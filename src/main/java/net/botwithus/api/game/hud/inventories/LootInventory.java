package net.botwithus.api.game.hud.inventories;

import net.botwithus.rs3.interfaces.Component;
import net.botwithus.rs3.interfaces.Interface;
import net.botwithus.rs3.item.Item;
import net.botwithus.rs3.queries.builders.components.ComponentQuery;
import net.botwithus.rs3.script.Delay;
import net.botwithus.rs3.util.Random;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public final class LootInventory {
    private static final int LOOT_INTERFACE = 1622;
    private static final int LOOT_VARP = 5413;
    private static final int INVENTORY_ID = 773;
    static final Inventory LOOT_INVENTORY = new Inventory(INVENTORY_ID, LOOT_INTERFACE, 5, i -> i + 1);

    private LootInventory() {
    }

    /**
     * Checks if any items match the given id
     *
     * @param id the id to check the items against
     * @return true if an item matches the id
     */
    public static boolean contains(int id) {
        return LOOT_INVENTORY.contains(id);
    }

    /**
     * Checks if any items match the given name
     *
     * @param name the name to check the items against
     * @return true if an item matches the name
     */
    public static boolean contains(String name) {
        return LOOT_INVENTORY.contains(name);
    }

    /**
     * Checks if any items match the given name
     *
     * @param name the name to check the items against
     * @return true if an item matches the name
     */
    public static boolean contains(Pattern name) {
        return LOOT_INVENTORY.contains(name);
    }

    /**
     * Checks if all of the supplied ids match at least one item each
     *
     * @param ids the ids to check the items against
     * @return true if all of the ids have a match
     */
    public static boolean containsAllOf(final int... ids) {
        return LOOT_INVENTORY.containsAllOf(ids);
    }

    /**
     * Checks if all of the supplied names match at least one item each
     *
     * @param names the names to check the items against
     * @return true if all of the names have a match
     */
    public static boolean containsAllOf(final String... names) {
        return LOOT_INVENTORY.containsAllOf(names);
    }

    /**
     * Checks if all of the supplied names match at least one item each
     *
     * @param names the names to check the items against
     * @return true if all of the names have a match
     */
    public static boolean containsAllOf(final Pattern... names) {
        return LOOT_INVENTORY.containsAllOf(names);
    }

    /**
     * Checks if any items don't match the given ids
     *
     * @param ids the ids to check the items against
     * @return true if at least one item doesn't match the ids
     */
//    public static boolean containsAnyExcept(final int... ids) {
//        return LOOT_INVENTORY.containsAnyExcept(ids);
//    }

    /**
     * Checks if any items don't match the given names
     *
     * @param names the names to check the items against
     * @return true if at least one item doesn't match the names
     */
    public static boolean containsAnyExcept(final String... names) {
        return LOOT_INVENTORY.containsAnyExcept(names);
    }

    /**
     * Checks if any items don't match the given names
     *
     * @param names the names to check the items against
     * @return true if at least one item doesn't match the names
     */
    public static boolean containsAnyExcept(final Pattern... names) {
        return LOOT_INVENTORY.containsAnyExcept(names);
    }

    /**
     * Checks if any item matches the given ids
     *
     * @param ids the ids to check the items against
     * @return true if at least one item matches an id
     */
//    public static boolean containsAnyOf(final int... ids) {
//        return LOOT_INVENTORY.containsAnyOf(ids);
//    }

    /**
     * Checks if any item matches the given names
     *
     * @param names the names to check the items against
     * @return true if at least one item matches a name
     */
//    public static boolean containsAnyOf(final String... names) {
//        return LOOT_INVENTORY.containsAnyOf(names);
//    }

    /**
     * Checks if any item matches the given names
     *
     * @param names the names to check the items against
     * @return true if at least one item matches a name
     */
//    public static boolean containsAnyOf(final Pattern... names) {
//        return LOOT_INVENTORY.containsAnyOf(names);
//    }

    /**
     * Checks if all of the items match the given {@link Predicate filter}
     *
     * @param filter the filter to check the items against
     * @return true if all items match the filter
     */
//    public static boolean containsOnly(final Predicate<Item> filter) {
//        return LOOT_INVENTORY.containsOnly(filter);
//    }

    /**
     * Checks if all of the items match the given {@link Predicate filter}s
     *
     * @param filters the filters to check the items against
     * @return true if all items match at least one filter each
     */
//    @SafeVarargs
//    public static boolean containsOnly(final Predicate<Item>... filters) {
//        return LOOT_INVENTORY.containsOnly(filters);
//    }

    /**
     * Checks if all of the items match the given names
     *
     * @param names the filters to check the items against
     * @return true if all items match at least one name each
     */
//    public static boolean containsOnly(final String... names) {
//        return LOOT_INVENTORY.containsOnly(names);
//    }

    /**
     * Checks if all of the items match the given names
     *
     * @param names the filters to check the items against
     * @return true if all items match at least one name each
     */
//    public static boolean containsOnly(final Pattern... names) {
//        return LOOT_INVENTORY.containsOnly(names);
//    }

    /**
     * Gets the total quantity of items
     *
     * @return the total quantity of items
     */
    public static int getQuantity() {
        return LOOT_INVENTORY.getQuantity(Pattern.compile("^(.*)$"));
    }

    /**
     * Gets the total quantity of items matching the ids
     *
     * @param ids the ids to check the items against
     * @return the total quantity of items matching the ids
     */
    public static int getQuantity(final int... ids) {
        return LOOT_INVENTORY.getQuantity(ids);
    }

    /**
     * Gets the total quantity of items matching the names
     *
     * @param names the ids to check the items against
     * @return the total quantity of items matching the names
     */
    public static int getQuantity(final String... names) {
        return LOOT_INVENTORY.getQuantity(names);
    }

//    /**
//     * Gets the total quantity of items matching the names
//     *
//     * @param names the ids to check the items against
//     * @return the total quantity of items matching the names
//     */
//    public static int getQuantity(final Pattern... names) {
//        return LOOT_INVENTORY.getQuantity(names);
//    }

    public static boolean close(boolean hotkey) {
        if (!isOpen()) {
            return true;
        }
        final var button = ComponentQuery.newQuery(LOOT_INTERFACE).option("Close").results().first();
        return button.isPresent() && button.get().doAction("Close") && Delay.delayUntil(Random.nextInt(1500, 3000),
                                                                                        (p) -> !LootInventory.isOpen());
    }

    public static Item getItemIn(final int index) {
        return LOOT_INVENTORY.getItems().get(index);
    }

    public static List<Item> getItems() {
        return LOOT_INVENTORY.getItems();
    }

    public static List<Item> getItems(String... names) {
        var set = new HashSet<>(Arrays.stream(names).toList());
        return getItems().stream().filter(i -> set.contains(names)).toList();
    }

    //TODO: need #getValueOfBit
//    public static boolean isAreaLootEnabled() {
//        return VariableManager.getVarPlayerValue(LOOT_VARP).getValueOfBit(1) == 1;
//    }
//
//    public static boolean isEnabled() {
//        return VariableManager.getVarPlayerValue(LOOT_VARP).getValueOfBit(0) == 1;
//    }

    public static boolean isOpen() {
        return Interface.isInterfaceOpen(LOOT_INTERFACE);
    }

    public static boolean take(String... names) {
        Item result = getItems(names).get(0);
        return take(result);
    }

    //TODO: Cannot perform doAction with Item object directly
    public static boolean take(Item item) {
        if (item == null) {
            return false;
        }
        int quantity = getQuantity(item.getItemId());
        return LOOT_INVENTORY.doAction(item.getName()) && Delay.delayUntil(Random.nextInt(1500, 2500),
                                                                           (p) -> quantity == getQuantity(
                                                                                   item.getItemId()));
    }

    public static boolean lootAll(boolean hotkeys) {
        var component = ComponentQuery.newQuery(LOOT_INTERFACE).type(Component.Type.LABEL.getId()).text("Loot All",
                                                                                                        String::contentEquals).results().first();
        return component.isPresent() && component.get().doAction("Select") && Delay.delayUntil(
                Random.nextInt(1500, 3000), (p) -> !LootInventory.isOpen());
    }

    public static boolean lootCustom() {
        var component = ComponentQuery.newQuery(LOOT_INTERFACE).type(Component.Type.LABEL.getId()).text("Loot Custom",
                                                                                                        String::contentEquals).results().first();
        if (component.isPresent()) {
            int quantity = getQuantity();
            return component.get().doAction("Select") && Delay.delayUntil(Random.nextInt(1500, 3000),
                                                                          (p) -> getQuantity() == quantity);
        }
        return false;
    }
}
