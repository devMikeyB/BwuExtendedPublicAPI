package net.botwithus.api.game.hud.inventories;

import net.botwithus.internal.pooling.ItemPool;
import net.botwithus.rs3.interfaces.Component;
import net.botwithus.rs3.item.Item;
import net.botwithus.rs3.queries.ResultSet;
import net.botwithus.rs3.queries.builders.components.ComponentQuery;
import net.botwithus.rs3.queries.builders.inventories.InventoryQuery;
import net.botwithus.rs3.types.InventoryType;
import net.botwithus.rs3.types.configs.ConfigManager;
import net.botwithus.rs3.variables.VariableManager;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Inventory implements Iterable<Item> {

    private final InventoryType type;
    private final int id;

    protected final int interfaceIndex;

    protected final int componentIndex;

    private final Function<Integer, Integer> optionMapper;

    public Inventory(int id, int interfaceIndex, int componentIndex, Function<Integer, Integer> optionMapper) {
        this.id = id;
        this.interfaceIndex = interfaceIndex;
        this.componentIndex = componentIndex;
        this.type = ConfigManager.getInventoryType(id);
        this.optionMapper = optionMapper;
    }

    public Optional<Item> getSlot(int slot) {
        return InventoryQuery.newQuery(id).slot(slot).results().first();
    }

    public Optional<Item> getItem(String name) {
        return InventoryQuery.newQuery(id).itemName(name).results().first();
    }

    public Optional<Item> getItem(Pattern pattern) {
        return InventoryQuery.newQuery(id).itemName(pattern).results().first();
    }

    /**
     * Checks if the inventory is full.
     *
     * @return true if the inventory is full, false otherwise
     */
    public boolean isFull() {
        // Server does not send inventory items on login when empty, which is why this check is necessary.
        ResultSet<Item> results = InventoryQuery.newQuery(id).results();
        if (results.isEmpty()) {
            return false;
        }
        return InventoryQuery.newQuery(id).itemId(-1).results().isEmpty();
    }

    /**
     * Checks if the inventory is empty.
     *
     * @return true if the inventory is empty, false otherwise
     */
    public boolean isEmpty() {
        // Server does not send inventory items on login when empty, which is why this check is necessary.
        ResultSet<Item> results = InventoryQuery.newQuery(id).results();
        if (results.isEmpty()) {
            return true;
        }
        return results.stream().allMatch(i -> i.getItemId() == -1);
    }

    /**
     * Checks if the inventory contains the given query.
     *
     * @param query The query to check for
     * @return true if the inventory contains the query, false otherwise
     */
    public boolean contains(InventoryQuery query) {
        query.setIds(id);
        return !query.results().isEmpty();
    }

    public boolean contains(String... names) {
        return !InventoryQuery.newQuery(id).itemName(names).results().isEmpty();
    }

    public boolean contains(int... ids) {
        return !InventoryQuery.newQuery(id).itemId(ids).results().isEmpty();
    }

    public boolean contains(Pattern itemNamePattern) {
        return !InventoryQuery.newQuery(id).itemName(itemNamePattern).results().isEmpty();
    }

    public boolean containsAllOf(String... names) {
        var items = InventoryQuery.newQuery(id).itemName(names).results();
        var itemsSet = new HashSet<>(items.stream().map(Item::getName).distinct().toList());
        return !Arrays.stream(names).map(itemsSet::contains).toList().contains(false);
    }

    // TODO: Need to fix
    public boolean containsAllOf(int... ids) {
        var items = InventoryQuery.newQuery(id).itemId(ids).results();
        var itemsSet = items.stream().map(Item::getItemId).collect(Collectors.toUnmodifiableSet());
//        return !Arrays.stream(ids).map(i -> itemsSet.contains(i)).toList().contains(false);
        return false;
    }

    public boolean containsAllOf(Pattern... patterns) {
        var items = InventoryQuery.newQuery(id).itemName(patterns).results();
        var itemsList = items.stream().map(Item::getName).distinct().toList();
        return !itemsList.stream().map(
                i -> !Arrays.stream(patterns).map(j -> j.matcher(i)).toList().contains(false)).toList().contains(false);
    }

    public boolean containsAnyExcept(String... names) {
        var items = getItems();
        var nameSet = new HashSet<>(Arrays.asList(names));

        for (Item item : items) {
            if (!nameSet.contains(item.getName())) {
                return true;
            }
        }

        return false;
    }

    public boolean containsAnyExcept(Pattern... patterns) {
        return getItems().stream().map(Item::getName).distinct().filter(
                i -> !Arrays.stream(patterns).map(p -> p.matcher(i).matches()).toList().contains(
                        true)).toList().isEmpty();
    }

    public int getCount() {
        return InventoryQuery.newQuery(id).results().size();
    }

    public int getCount(String... names) {
        return InventoryQuery.newQuery(id).itemName(names).results().size();
    }

    public int getCount(int... ids) {
        return InventoryQuery.newQuery(id).itemId(ids).results().size();
    }

    public int getCount(Pattern pattern) {
        return InventoryQuery.newQuery(id).itemName(pattern).results().size();
    }

    public int getQuantity(String... names) {
        var item = InventoryQuery.newQuery(id).itemName(names).results().first();
        return item.map(Item::getAmount).orElse(-1);
    }

    public int getQuantity(int... ids) {
        var item = InventoryQuery.newQuery(id).itemId(ids).results().first();
        return item.map(Item::getAmount).orElse(-1);
    }

    public int getQuantity(Pattern itemNamePattern) {
        var item = InventoryQuery.newQuery(id).itemName(itemNamePattern).results().first();
        return item.map(Item::getAmount).orElse(-1);
    }

    /**
     * Executes an action on the specified slot with the given option.
     *
     * @param slot   The slot to execute the action on.
     * @param option The option to execute the action with.
     * @return True if the action was successful, false otherwise.
     */
    public boolean doAction(int slot, String option) {
        return doAction(slot, option, String::contentEquals);
    }

    /**
     * Performs an action on an item in the inventory.
     *
     * @param slot       The slot of the item in the inventory.
     * @param option     The option to perform on the item.
     * @param optionpred A predicate to test if the option is valid.
     * @return True if the action was successful, false otherwise.
     */
    public boolean doAction(int slot, String option, BiFunction<String, CharSequence, Boolean> optionpred) {
        Optional<Item> first = InventoryQuery.newQuery(id).slot(slot).results().first();
        if (first.isEmpty()) {
            return false;
        }
        Item item = first.get();
        String[] options;
        if (id == 94) {
            options = item.getEquipmentOptions();
        } else {
            options = item.getComponentOptions();
        }
        for (int i = 0; i < options.length; i++) {
            String s = options[i];
            if (optionpred.apply(s, option)) {
                return doAction(slot, i);
            }
        }
        return false;
    }

    /**
     * Executes an action on the item in the specified slot.
     *
     * @param slot   The slot to execute the action on.
     * @param option The option to execute.
     * @return True if the action was successful, false otherwise.
     */
    public boolean doAction(int slot, int option) {
        ResultSet<Item> results = InventoryQuery.newQuery(id).slot(slot).results();
        Optional<Item> item = results.first();
        if (item.isPresent()) {
            Item i = item.get();
            System.out.println("[Inventory#doAction(slot, option)]: " + i.getItemId());
            ResultSet<Component> queryResults = ComponentQuery.newQuery(interfaceIndex).item(
                    i.getItemId()).componentIndex(componentIndex).withOptionMapper(optionMapper).results();
            System.out.println("[Inventory#doAction(slot, option)]: QueryResults: " + queryResults.size());
            return queryResults.first().map(c -> c.doAction(option)).orElse(false);
        }
        return false;
    }

    /**
     * Executes an action with the given name.
     *
     * @param name The name of the action to execute.
     * @return true if the action was executed successfully, false otherwise.
     */
    public boolean doAction(String name) {
        return doAction(name, "", String::contentEquals, (s, c) -> true);
    }

    /**
     * Executes an action on an item in the inventory.
     *
     * @param name   The name of the item to perform the action on.
     * @param option The option to perform the action with.
     * @return True if the action was successful, false otherwise.
     */
    public boolean doAction(String name, int option) {
        Optional<Item> first = InventoryQuery.newQuery(id).itemName(name).results().first();
        if (first.isPresent()) {
            Item item = first.get();

            return doAction(item.getSlot(), option);
        }
        return false;
    }

    /**
     * Performs an action based on the given name and option.
     *
     * @param name   The name of the action to perform.
     * @param option The option to use for the action.
     * @return True if the action was successful, false otherwise.
     */
    public boolean doAction(String name, String option) {
        return doAction(name, option, String::contentEquals, String::contentEquals);
    }

    public boolean doAction(Pattern namePattern, String option) {
        Optional<Item> first = InventoryQuery.newQuery(id).itemName(namePattern).results().first();
        if (first.isPresent()) {
            Item item = first.get();
            return doAction(item.getSlot(), option);
        }
        return false;
    }

    public boolean doAction(Pattern namePattern, int option) {
        Optional<Item> first = InventoryQuery.newQuery(id).itemName(namePattern).results().first();
        if (first.isPresent()) {
            Item item = first.get();
            return doAction(item.getSlot(), option);
        }
        return false;
    }

    public List<Item> getItems() {
        List<Item> items = new ArrayList<>();
        for (Item result : InventoryQuery.newQuery(id).results()) {
            if (result.getItemId() > -1) {
                items.add(ItemPool.createItem(this.id, result.getSlot(), result.getItemId(), result.getAmount()));
            }
        }
        return items;
    }

    /**
     * Executes an action on an item.
     *
     * @param name       The name of the item to perform the action on.
     * @param option     The option of the item to perform the action on.
     * @param namepred   The predicate to use to compare the item name.
     * @param optionpred The predicate to use to compare the item option.
     * @return true if the action was successful, false otherwise.
     */
    public boolean doAction(String name, String option, BiFunction<String, CharSequence, Boolean> namepred, BiFunction<String, CharSequence, Boolean> optionpred) {
        Optional<Item> item = InventoryQuery.newQuery(id).itemName(name, namepred).results().first();
        if (item.isPresent()) {
            Item i = item.get();
            String[] options;
            if (id == 94) {
                options = i.getEquipmentOptions();
            } else {
                options = i.getComponentOptions();
            }
            for (int j = 0; j < options.length; j++) {
                String s = options[j];
                if (optionpred.apply(s, option)) {
                    int optionIndex = j;
                    return ComponentQuery.newQuery(interfaceIndex).item(i.getItemId()).componentIndex(
                            componentIndex).withOptionMapper(optionMapper).results().first().map(
                            c -> c.doAction(optionIndex)).orElse(false);
                }
            }
        }
        return false;
    }

    /**
     * Returns the number of slots in the type.
     *
     * @return the number of slots in the type
     */
    public int getSlotCount() {
        return type.count;
    }

    /**
     * Returns the id of the inventory.
     *
     * @return the id of the inventory
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the value of a varbit in the inventory.
     *
     * @param slot     The slot of the inventory.
     * @param varbitId The varbit id.
     * @return The value of the varbit.
     */
    public int getVarbitValue(int slot, int varbitId) {
        return VariableManager.getInventoryVarbit(id, slot, varbitId);
    }

    @Override
    public Iterator<Item> iterator() {
        return getItems().iterator();
    }

}
