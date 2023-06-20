package net.botwithus.api.game.hud.inventories;

import net.botwithus.rs3.item.Item;
import net.botwithus.rs3.queries.builders.inventories.InventoryQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.regex.Pattern;

/**
 * A class that provides methods to interact with the player's backpack.
 *
 * @author David
 */
public final class Backpack {

    private static final Pattern SELECTED_ITEM_PATTERN = Pattern.compile("^Use\\s.*?(\\s->\\s).*$");

    static final Inventory BACKPACK = new Inventory(93, 1473, 5, i -> i + 1);

    private Backpack() {
    }

    /**
     *
     *   Checks if the backpack is full.
     *
     *   @return true if the backpack is full, false otherwise
     */
    public static boolean isFull() {
        return BACKPACK.isFull();
    }

    /**
     *
     *   Checks if the backpack is empty.
     *   @return true if the backpack is empty, false otherwise.
     */
    public static boolean isEmpty() {
        return BACKPACK.isEmpty();
    }

    /**
     *
     *   Checks if the given {@link InventoryQuery} is contained in the {@link #BACKPACK}.
     *
     *   @param query The {@link InventoryQuery} to check for.
     *   @return {@code true} if the {@link InventoryQuery} is contained in the {@link #BACKPACK}, {@code false} otherwise.
     */
    public static boolean contains(InventoryQuery query) {
        return BACKPACK.contains(query);
    }

    public static boolean contains(String... names) {
        return BACKPACK.contains(names);
    }

    public static boolean contains(int... ids) {
        return BACKPACK.contains(ids);
    }

    public static int getCount() {
        return BACKPACK.getCount();
    }

    public static int getCount(String... names) {
        return BACKPACK.getCount(names);
    }

    public static int getCount(int... ids) {
        return BACKPACK.getCount(ids);
    }

    public static int getCount(Pattern pattern) {
        return BACKPACK.getCount(pattern);
    }

    public static int getQuantity(String... names) {
        return BACKPACK.getQuantity(names);
    }

    public static int getQuantity(int... ids) {
        return BACKPACK.getQuantity(ids);
    }

    public static int getQuantity(Pattern itemNamePattern) {
        return BACKPACK.getQuantity(itemNamePattern);
    }

    public static boolean contains(Pattern itemNamePattern) {
        return BACKPACK.contains(itemNamePattern);
    }

    public static boolean containsAllOf(String... names) {
        return BACKPACK.containsAllOf(names);
    }

    public static boolean containsAllOf(Pattern pattern) { return BACKPACK.containsAllOf(pattern); }

    public static boolean containsAnyExcept(String... names) { return BACKPACK.containsAnyExcept(names); }

    public static boolean containsAnyExcept(Pattern... patterns) { return BACKPACK.containsAnyExcept(patterns); }

    public static boolean doAction(int slot, String option) {
        return BACKPACK.doAction(slot, option);
    }

    /**
     *
     *   Executes an action on the backpack.
     *
     *   @param slot The slot of the backpack to perform the action on.
     *   @param option The option to perform on the slot.
     *   @return Whether the action was successful.
     */
    public static boolean doAction(int slot, int option) {
        return BACKPACK.doAction(slot, option);
    }

    /**
     *
     *   Executes an action on the backpack.
     *
     *   @param name The name of the action to be executed.
     *   @return True if the action was successful, false otherwise.
     */
    public static boolean doAction(String name) {
        return BACKPACK.doAction(name);
    }

    /**
     *
     *   Executes an action on a given item in the backpack.
     *
     *   @param name The name of the item to perform the action on.
     *   @param option The action to perform on the item.
     *   @return True if the action was successful, false otherwise.
     */
    public static boolean doAction(String name, String option) {
        return BACKPACK.doAction(name, option);
    }

    /**
     *
     *   Executes an action on the backpack.
     *
     *   @param name The name of the action to be executed.
     *   @param option The option of the action to be executed.
     *   @param namepred The predicate to be used to validate the name.
     *   @param optionpred The predicate to be used to validate the option.
     *   @return True if the action was successful, false otherwise.
     */
    public static boolean doAction(String name, String option, BiFunction<String, CharSequence, Boolean> namepred, BiFunction<String, CharSequence, Boolean> optionpred) {
        return BACKPACK.doAction(name, option, namepred, optionpred);
    }

    /**
     *
     *   Executes an action on the backpack
     *
     *   @param name The name of the action to be executed
     *   @param option The option to be used for the action
     *   @return The result of the action
     */
    public static boolean doAction(String name, int option) {
        return BACKPACK.doAction(name, option);
    }

    public static boolean doAction(Pattern namePattern, String option) { return BACKPACK.doAction(namePattern, option); }

    public static boolean doAction(Pattern namePattern, int option) { return BACKPACK.doAction(namePattern, option); }

    /**
     *
     *   Returns the capacity of the backpack.
     *
     *   @return The capacity of the backpack.
     */
    public static int capacity() {
        return BACKPACK.getSlotCount();
    }

    /**
     *
     *   Gets the value of a varbit in a given slot.
     *
     *   @param slot The slot of the varbit.
     *   @param varbitId The ID of the varbit.
     *   @return The value of the varbit.
     */
    public static int getVarbitValue(int slot, int varbitId) {
        return BACKPACK.getVarbitValue(slot, varbitId);
    }

    /**
     *
     *   Retrieves an item from the backpack.
     *
     *   @param name The name of the item to retrieve.
     *   @return An {@link Optional} containing the item if it exists, or an empty {@link Optional} if it does not.
     */
    public static Optional<Item> getItem(String name) {
        return BACKPACK.getItem(name);
    }

    public static Optional<Item> getItem(Pattern pattern) {
        return BACKPACK.getItem(pattern);
    }

    public static List<Item> getItems() {
        return BACKPACK.getItems();
    }

    public static Optional<Item> getSelectedItem() {
        return InventoryQuery.newQuery(93).option(SELECTED_ITEM_PATTERN).results().first();
    }
}