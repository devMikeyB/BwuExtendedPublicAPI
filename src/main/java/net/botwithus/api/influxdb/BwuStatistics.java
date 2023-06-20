package net.botwithus.api.influxdb;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.botwithus.rs3.time.Stopwatch;
import net.botwithus.rs3.util.Json;
import net.botwithus.rs3.skills.Skill;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.*;

public class BwuStatistics {
    private Map<String, InfluxItem> influxItemMap = new HashMap<>();
    private Map<Skill, XPInfo> xpInfoMap = new HashMap<>();

    private Stopwatch stopWatch = new Stopwatch();
    private String botTitle, playerName;
    public Influx influx = null;
    private final UUID sessionId = UUID.randomUUID();
    private MessageDigest digest;
    private byte[] hash;

    public BwuStatistics(String botTitle, String playerName) {
        this.botTitle = botTitle;
        this.playerName = playerName;
        influx = new Influx();
        stopWatch.start();
    }

    public void updateInfluxData() {
        updateInfluxData(false);
    }

    public void updateInfluxData(boolean force) {
        if (force || stopWatch.elapsed() > 30000) {
            System.out.println("Updating Influx Data");
            try {
                var input = "53B3D8AEC4FE75F1D8C83EFBBDC56" + botTitle + playerName + sessionId.toString();
                digest = MessageDigest.getInstance("SHA-256");
                hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));

                var obj = new JsonObject();
                var tags = Json.serialize(new HashMap<>() {{
                    //put("abyssName", new String(Abyss.getKrakenUsernameBinary()));
                    put("botTitle", botTitle);
                    put("sessionId", sessionId.toString());
                    put("platform", "Abyss");
                    put("digest", Base64.getEncoder().encodeToString(hash));
                    put("playerName", playerName);
                }});

                var events = new JsonArray();
                for (var skill : xpInfoMap.keySet()) {
                    var model = xpInfoMap.get(skill);
                    var influxXP = model.deriveInfluxXP();

                    if (influxXP.floatValue() > 0 && influxXP.floatValue() < 500000) {
                        var event = new JsonObject();
                        event.addProperty("eventType", skill.toString().toLowerCase(Locale.ROOT) + "XP");
                        event.addProperty("xp", influxXP);
                        events.add(event);
                    }
                }

                for (var item : influxItemMap.keySet()) {
                    if (!item.equals("N/A")) {
                        var info = influxItemMap.get(item);
                        var influxCount = info.deriveInfluxCount();

                        if (influxCount.floatValue() > 0 && influxCount.floatValue() < 500000) {
                            var event = new JsonObject();
                            event.addProperty("eventType", "itemAcquired");
                            event.addProperty("itemName", item);
                            event.addProperty("quantity", influxCount);
                            events.add(event);
                        }
                    }
                }

                obj.add("tags", tags);
                obj.add("events", events);
                System.out.println(obj.toString());
                influx.post(obj.toString());
            } catch (Exception e) {
                System.out.println(e.getMessage() + " | " + Arrays.toString(e.getStackTrace()));
            }
            stopWatch = Stopwatch.startNew();
        }
    }

    /**
     * Will add item with a starting count of zero
     * @param itemName
     */
    public void addItem(String itemName) {
        addItem(itemName, 0);
    }

    public void addItem(String itemName, int startingCount) {
        if (!influxItemMap.containsKey(itemName))
            influxItemMap.put(itemName, new InfluxItem(itemName, startingCount));
    }

    /**
     * Will add items with a starting count of zero
     * @param itemNames
     */
    public void addItems(String... itemNames) {
        for (var itemName : itemNames) {
            addItem(itemName, 0);
        }
    }

    /**
     * Allows user to specify the starting counts of items
     * @param itemNames
     * @param startingCounts
     * @throws UnsupportedOperationException
     */
    public void addItems(String[] itemNames, int[] startingCounts) throws UnsupportedOperationException {
        if (itemNames == null || startingCounts == null || itemNames.length != startingCounts.length)
            throw new UnsupportedOperationException("itemNames and startingCounts parameters must be equal in length");

        for (int i = 0; i < itemNames.length; i++) {
            addItem(itemNames[i], startingCounts[i]);
        }
    }

    public void addSkill(Skill skill) {
        xpInfoMap.put(skill, new XPInfo(skill));
    }

    public void addSkills(Skill... skills) {
        for (var skill : skills) {
            addSkill(skill);
        }
    }

    public boolean containsItem(String itemName) {
        return influxItemMap.containsKey(itemName);
    }

    public void addItemQuantity(String itemName, int quantityDifference) {
        influxItemMap.get(itemName).addCount(quantityDifference);
    }
}
