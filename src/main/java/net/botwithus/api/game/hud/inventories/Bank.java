package net.botwithus.api.game.hud.inventories;

import net.botwithus.rs3.game.Distance;
import net.botwithus.rs3.game.hud.interfaces.Interfaces;
import net.botwithus.rs3.game.Item;
import net.botwithus.rs3.game.minimenu.MiniMenu;
import net.botwithus.rs3.game.minimenu.actions.ComponentAction;
import net.botwithus.rs3.game.queries.builders.characters.NpcQuery;
import net.botwithus.rs3.game.queries.builders.components.ComponentQuery;
import net.botwithus.rs3.game.queries.builders.items.InventoryItemQuery;
import net.botwithus.rs3.game.queries.builders.objects.SceneObjectQuery;
import net.botwithus.rs3.script.Execution;
import net.botwithus.rs3.util.RandomGenerator;
import net.botwithus.rs3.game.vars.VarManager;


import java.util.Arrays;
import java.util.HashSet;
import java.util.function.BiFunction;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static net.botwithus.api.game.hud.inventories.Backpack.BACKPACK;


public class Bank {

    private static final Pattern bankPattern = Pattern.compile("^(?!.*deposit).*(bank).*$", Pattern.CASE_INSENSITIVE);

    private static final int PRESET_BROWSING_VARBIT_ID = 49662, SELECTED_OPTIONS_TAB_VARBIT_ID = 45191, WITHDRAW_TYPE_VARBIT_ID = 45189, WITHDRAW_X_VARP_ID = 111;

    private static final Inventory BANK = new BankInventory();
//    private static final Inventory BACKPACK = new Inventory(93, 517, i -> i + 1);

    private Bank() {

    }

    /**
     * Opens the nearest bank.
     *
     * @return {@code true} if the bank was successfully opened, {@code false} otherwise.
     */
    public static boolean open() {
        var obj = SceneObjectQuery.newQuery().name(bankPattern).results().nearest();
        var npc = NpcQuery.newQuery().option("Bank").results().nearest();
        var useObj = true;

        if (obj != null && npc != null) {
            useObj = Distance.to(obj) < Distance.to(npc);
        }
        if (obj != null && useObj) {
            var actions = obj.getOptions();
            if (!actions.isEmpty()) {
                var action = actions.stream().filter(i -> i != null && !i.isEmpty()).findFirst();
                return action.isPresent() && obj.interact(action.get());
            } else {
                System.out.println("[Bank] No options on object");
                return false;
            }
        } else if (npc != null) {
            return npc.interact("Bank");
        }

        return false;
    }

    /**
     * Checks if the bank is open
     *
     * @return true if the bank is open, false otherwise
     */
    public static boolean isOpen() {
        return Interfaces.isOpen(517);
    }

    /**
     * Closes the bank interface.
     *
     * @return true if the interface was closed, false otherwise
     */
    public static boolean close() {
//        return Interface.find(ComponentQuery.newQuery(517)
//                .option("Close"))
//                .first()
//                .map(Component::doAction)
//                .orElse(false);
        // TODO: Update to no longer use MiniMenu.doAction
        return MiniMenu.interact(ComponentAction.COMPONENT.getType(), 1, -1, 33882418);
    }

    /**
     * Gets all the items in the players bank
     *
     * @return returns an array containing all items in the bank.
     */
    public static Item[] getItems() {
        return InventoryItemQuery.newQuery(95).results().stream().filter(i -> i.getId() != -1).toArray(Item[]::new);
    }


    /**
     * Gets the count of a specific item in the bank.
     *
     * @param query the predicate specifying the item to count.
     * @return returns an integer representing the count of the item
     */
    public static int count(InventoryItemQuery query) {
        return query.results().stream().mapToInt(Item::getStackSize).sum();
    }

    /**
     * Gets the first item matching the predicate.
     *
     * @param query the predicate specifying the item to count.
     * @return returns the item, or null if not found.
     */
    public static Item first(InventoryItemQuery query) {
        return query.results().first();
    }

    /**
     * Determines if the bank is empty
     *
     * @return returns true if empty, false if not.
     */
    public static boolean isEmpty() {
        return getItems().length == 0;
    }

    /**
     * Determines if the bank contains an item.
     *
     * @param query the predicate specifying the item to count.
     * @return returns the item, or null if not found.
     */
    public static boolean contains(InventoryItemQuery query) {
        return count(query) > 0;
    }

    public static boolean contains(String... itemNames) {
        return !InventoryItemQuery.newQuery(95).name(itemNames).results().isEmpty();
    }

    public static boolean contains(Pattern itemNamePattern) {
        return !InventoryItemQuery.newQuery(95).name(itemNamePattern).results().isEmpty();
    }

    /**
     * Withdraws an item from the bank
     *
     * @param query  the query specifying the item to withdraw.
     * @param option the doAction option to execute on the item.
     */
    public static boolean withdraw(InventoryItemQuery query, int option) {
        query.ids(95);
        Item item = query.results().first();
        return item != null && BANK.interact(item.getSlot(), option);
    }

    /**
     * Withdraws an item from the inventory.
     *
     * @param itemName The name of the item to withdraw.
     * @param option   The option to withdraw.
     * @return True if the item was successfully withdrawn, false otherwise.
     */
    public static boolean withdraw(String itemName, int option) {
        if (itemName != null && !itemName.isEmpty()) {
            return withdraw(InventoryItemQuery.newQuery().name(itemName), option);
        }
        return false;
    }

    /**
     * Withdraws an item from the inventory.
     *
     * @param itemId The ID of the item to withdraw.
     * @param option The option of the item to withdraw.
     * @return True if the item was successfully withdrawn, false otherwise.
     */
    public static boolean withdraw(int itemId, int option) {
        if (itemId >= 0) {
            return withdraw(InventoryItemQuery.newQuery().ids(itemId), option);
        }
        return false;
    }

    /**
     * Withdraws an item from the inventory.
     *
     * @param pattern The pattern of the item to withdraw.
     * @param option  The option of the item to withdraw.
     * @return true if the item was successfully withdrawn, false otherwise.
     */
    public static boolean withdraw(Pattern pattern, int option) {
        if (pattern != null) {
            return withdraw(InventoryItemQuery.newQuery().name(pattern), option);
        }
        return false;
    }

    /**
     * Withdraws all of a given item from the inventory.
     *
     * @param name The name of the item to withdraw.
     * @return true if the item was successfully withdrawn, false otherwise.
     */
    public static boolean withdrawAll(String name) {
        return withdraw(InventoryItemQuery.newQuery().name(name), 6);
    }

    public static boolean withdrawAll(int id) {
        return withdraw(InventoryItemQuery.newQuery().ids(id), 6);
    }

    public static boolean withdrawAll(Pattern pattern) {
        return withdraw(InventoryItemQuery.newQuery().name(pattern), 6);
    }

    /**
     * Deposits all items in the player's bank.
     *
     * @return true if the items were successfully deposited, false otherwise
     */
    public static boolean depositAll() {
        var comp = ComponentQuery.newQuery(517).option("Deposit carried items").results().first();
        return comp != null && comp.interact(1);
    }

    /**
     * Attempts to deposit an item from the given {@link InventoryItemQuery}.
     *
     * @param query  The query to use for finding the item to deposit.
     * @param option The option to use when depositing the item.
     * @return {@code true} if the item was successfully deposited, {@code false} otherwise.
     */
    public static boolean deposit(InventoryItemQuery query, int option) {
        query.ids(93);
        Item item = query.results().first();
        return deposit(item, option);
    }

    public static boolean deposit(Item item, int option) {
        return item != null && BACKPACK.interact(item.getSlot(), option);
    }

    public static boolean depositAll(String... itemNames) {
        return InventoryItemQuery.newQuery(93).name(itemNames).results().stream().map(
                i -> deposit(i, 7)).toList().contains(false);
    }

    public static boolean depositAll(int... itemIds) {
        return InventoryItemQuery.newQuery(93).ids(itemIds).results().stream().map(i -> deposit(i, 7)).toList().contains(
                false);
    }

    public static boolean depositAll(Pattern... patterns) {
        return InventoryItemQuery.newQuery(93).name(patterns).results().stream().map(
                i -> deposit(i, 7)).toList().contains(false);
    }

    public static boolean depositAllExcept(String... itemNames) {
        var nameSet = new HashSet<>(Arrays.asList(itemNames));
        var items = InventoryItemQuery.newQuery(93).option("Deposit-All").results().stream().filter(
                i -> !nameSet.contains(i.getName()));
        return !items.map(i -> deposit(i, 7)).toList().contains(false);
    }

    public static boolean depositAllExcept(int... ids) {
        var idSet = Arrays.stream(ids).boxed().collect(Collectors.toSet());
        var items = InventoryItemQuery.newQuery(93).option("Deposit-All").results().stream().filter(
                i -> !idSet.contains(i.getId()));
        return !items.map(i -> deposit(i, 7)).toList().contains(false);
    }

    public static boolean depositAllExcept(Pattern... patterns) {
        var items = InventoryItemQuery.newQuery(93).option("Deposit-All").results().stream().filter(
                i -> !Arrays.stream(patterns).map(p -> p.matcher(i.getName()).matches()).toList().contains(true));
        return !items.map(i -> deposit(i, 7)).toList().contains(false);
    }

    /**
     * Deposits an item into the inventory.
     *
     * @param itemId The ID of the item to deposit.
     * @param option The option to use when depositing the item.
     * @return True if the item was successfully deposited, false otherwise.
     */
    public static boolean deposit(int itemId, int option) {
        return deposit(InventoryItemQuery.newQuery(93).ids(itemId), option);
    }

    /**
     * Deposits an item into the inventory.
     *
     * @param name   The name of the item to deposit.
     * @param spred  The spread function to use when searching for the item.
     * @param option The option to use when depositing the item.
     * @return True if the item was successfully deposited, false otherwise.
     */
    public static boolean deposit(String name, BiFunction<String, CharSequence, Boolean> spred, int option) {
        return deposit(InventoryItemQuery.newQuery(93).name(name, spred), option);
    }

    /**
     * Deposits an amount of money into an account.
     *
     * @param name   The name of the account to deposit into.
     * @param option The amount of money to deposit.
     * @return True if the deposit was successful, false otherwise.
     */
    public static boolean deposit(String name, int option) {
        return deposit(name, String::contentEquals, option);
    }

    /**
     * Loads the given preset number.
     *
     * @param presetNumber the preset number to load
     * @return true if the preset was successfully loaded, false otherwise
     * @throws InterruptedException if the thread is interrupted while sleeping
     */
    // TODO: Update to no longer use MiniMenu.doAction
    public static boolean loadPreset(int presetNumber) {
        int presetBrowsingValue = VarManager.getVarbitValue(PRESET_BROWSING_VARBIT_ID);
        if ((presetNumber >= 10 && presetBrowsingValue < 1) || (presetNumber < 10 && presetBrowsingValue > 0)) {
            Execution.delay(RandomGenerator.nextInt(300, 700));
            MiniMenu.interact(ComponentAction.COMPONENT.getType(), 1, 100, 33882231);
        }
        return MiniMenu.interact(ComponentAction.COMPONENT.getType(), 1, presetNumber % 9,
                                 33882231);//presetComp != null && presetComp.interact("Load");
    }

    /**
     * Gets the value of a varbit in the inventory.
     *
     * @param slot     The inventory slot to check.
     * @param varbitId The varbit id to check.
     * @return The value of the varbit.
     */
    public static int getVarbitValue(int slot, int varbitId) {
        var item = InventoryItemQuery.newQuery(95).slots(slot).results().first();
        return item != null ? item.getVarbitValue(varbitId) : Integer.MIN_VALUE;
    }
}
