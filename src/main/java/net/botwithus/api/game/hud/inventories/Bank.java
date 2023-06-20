package net.botwithus.api.game.hud.inventories;

import net.botwithus.rs3.interfaces.Interface;
import net.botwithus.rs3.item.Item;
import net.botwithus.rs3.menu.MiniMenu;
import net.botwithus.rs3.menu.types.ComponentAction;
import net.botwithus.rs3.queries.builders.components.ComponentQuery;
import net.botwithus.rs3.queries.builders.inventories.InventoryQuery;
import net.botwithus.rs3.queries.builders.npc.NpcQuery;
import net.botwithus.rs3.queries.builders.objects.SceneObjectQuery;
import net.botwithus.rs3.script.Delay;
import net.botwithus.rs3.types.ItemOption;
import net.botwithus.rs3.util.Random;
import net.botwithus.rs3.variables.VariableManager;
import net.botwithus.rs3.world.navigation.computations.Distance;

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
     *
     *   Opens the nearest bank.
     *
     *   @return {@code true} if the bank was successfully opened, {@code false} otherwise.
     */
    public static boolean open() {
        var obj = SceneObjectQuery.newQuery().name(bankPattern).results().nearest();
        var npc = NpcQuery.newQuery().option("Bank").results().nearest();
        var useObj = true;

        if (obj.isPresent() && npc.isPresent()) {
            useObj = Distance.to(obj.get()) < Distance.to(npc.get());
        }
        if (obj.isPresent() && useObj) {
            String[] actions = obj.get().getOptions();
            if (actions.length > 0) {
                var action = Arrays.stream(actions).filter(i -> i != null && i.length() > 0).findFirst();
                return action.isPresent() && obj.get().doAction(action.get());
            } else {
                System.out.println("[Bank] No options on object");
                return false;
            }
        } else if (npc.isPresent()) {
            return npc.get().doAction("Bank");
        }

        return false;
    }

    /**
     * Checks if the bank is open
     *
     * @return true if the bank is open, false otherwise
     */
    public static boolean isOpen() {
        return Interface.isInterfaceOpen(517);
    }

    /**
     *
     *   Closes the bank interface.
     *
     *   @return true if the interface was closed, false otherwise
     */
    public static boolean close() {
//        return Interface.find(ComponentQuery.newQuery(517)
//                .option("Close"))
//                .first()
//                .map(Component::doAction)
//                .orElse(false);
        return MiniMenu.doAction(ComponentAction.COMPONENT.getType(), 1, -1, 33882418);
    }

    /**
     * Gets all the items in the players bank
     *
     * @return returns an array containing all items in the bank.
     */
    public static Item[] getItems() {
        return InventoryQuery.newQuery(95).results().stream().filter(i -> i.getItemId() != -1).toArray(Item[]::new);
    }


    /**
     * Gets the count of a specific item in the bank.
     *
     * @param query the predicate specifying the item to count.
     * @return returns an integer representing the count of the item
     */
    public static int count(InventoryQuery query) {
        return query.results().stream().mapToInt(Item::getAmount).sum();
    }

    /**
     * Gets the first item matching the predicate.
     *
     * @param query the predicate specifying the item to count.
     * @return returns the item, or null if not found.
     */
    public static Item first(InventoryQuery query) {
        return query.results().first().orElse(Item.EMPTY);
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
    public static boolean contains(InventoryQuery query) {
        return count(query) > 0;
    }

    public static boolean contains(String... itemNames) {
        return !InventoryQuery.newQuery(95).itemName(itemNames).results().isEmpty();
    }

    public static boolean contains(Pattern itemNamePattern) {
        return !InventoryQuery.newQuery(95).itemName(itemNamePattern).results().isEmpty();
    }

    /**
     * Withdraws an item from the bank
     *
     * @param query  the query specifying the item to withdraw.
     * @param option the doAction option to execute on the item.
     */
    public static boolean withdraw(InventoryQuery query, int option) {
        query.setIds(95);
        Item item = query.results().first().orElse(Item.EMPTY);
        if (item == Item.EMPTY)
            return false;
        return BANK.doAction(item.getSlot(), option);
    }

    /**
     *
     *   Withdraws an item from the inventory.
     *
     *   @param itemName The name of the item to withdraw.
     *   @param option The option to withdraw.
     *   @return True if the item was successfully withdrawn, false otherwise.
     */
    public static boolean withdraw(String itemName, int option) {
        if (itemName != null && !itemName.isEmpty()) {
            return withdraw(InventoryQuery.newQuery().itemName(itemName), option);
        }
        return false;
    }

    /**
     *
     *   Withdraws an item from the inventory.
     *
     *   @param itemId The ID of the item to withdraw.
     *   @param option The option of the item to withdraw.
     *   @return True if the item was successfully withdrawn, false otherwise.
     */
    public static boolean withdraw(int itemId, int option) {
        if (itemId >= 0) {
            return withdraw(InventoryQuery.newQuery().itemId(itemId), option);
        }
        return false;
    }

    /**
     *
     *   Withdraws an item from the inventory.
     *
     *   @param pattern The pattern of the item to withdraw.
     *   @param option The option of the item to withdraw.
     *   @return true if the item was successfully withdrawn, false otherwise.
     */
    public static boolean withdraw(Pattern pattern, int option) {
        if (pattern != null) {
            return withdraw(InventoryQuery.newQuery().itemName(pattern), option);
        }
        return false;
    }

    /**
     *
     *   Withdraws all of a given item from the inventory.
     *   @param name The name of the item to withdraw.
     *   @return true if the item was successfully withdrawn, false otherwise.
     */
    public static boolean withdrawAll(String name) {
        return withdraw(InventoryQuery.newQuery().itemName(name), 6);
    }
    public static boolean withdrawAll(int id) {
        return withdraw(InventoryQuery.newQuery().itemId(id), 6);
    }
    public static boolean withdrawAll(Pattern pattern) {
        return withdraw(InventoryQuery.newQuery().itemName(pattern), 6);
    }

    /**
     * Deposits all items in the player's bank.
     *
     * @return true if the items were successfully deposited, false otherwise
     */
    public static boolean depositAll() {
        return Interface.find(ComponentQuery
                        .newQuery(517)
                        .option("Deposit carried items"))
                .first()
                .map(c -> c.doAction(1))
                .orElse(false);
    }

    /**
     * Attempts to deposit an item from the given {@link InventoryQuery}.
     *
     * @param query  The query to use for finding the item to deposit.
     * @param option The option to use when depositing the item.
     * @return {@code true} if the item was successfully deposited, {@code false} otherwise.
     */
    public static boolean deposit(InventoryQuery query, int option) {
        query.setIds(93);
        Item item = query.results().first().orElse(Item.EMPTY);
        return deposit(item, option);
    }

    public static boolean deposit(Item item, int option) {
        if (item == Item.EMPTY)
            return false;
        for (ItemOption bankOption : item.getType().bankOptions) {
            if(bankOption.getOption() == option) {
                return BANK.doAction(item.getSlot(), option);
            }
        }
        return BACKPACK.doAction(item.getSlot(), option);
    }

    public static boolean depositAll(String... itemNames) {
        return InventoryQuery.newQuery(93).itemName(itemNames).results().stream().map(i -> deposit(i, 7)).toList().contains(false);
    }

    public static boolean depositAll(int... itemIds) {
        return InventoryQuery.newQuery(93).itemId(itemIds).results().stream().map(i -> deposit(i, 7)).toList().contains(false);
    }

    public static boolean depositAll(Pattern... patterns) {
        return InventoryQuery.newQuery(93).itemName(patterns).results().stream().map(i -> deposit(i, 7)).toList().contains(false);
    }

    public static boolean depositAllExcept(String... itemNames) {
        var nameSet = new HashSet<>(Arrays.asList(itemNames));
        var items = InventoryQuery.newQuery(93).option("Deposit-All").results().stream().filter(i -> !nameSet.contains(i.getName()));
        return !items.map(i -> deposit(i, 7)).toList().contains(false);
    }

    public static boolean depositAllExcept(int... ids) {
        var idSet = Arrays.stream(ids).boxed().collect(Collectors.toSet());
        var items = InventoryQuery.newQuery(93).option("Deposit-All").results().stream().filter(i -> !idSet.contains(i.getItemId()));
        return !items.map(i -> deposit(i, 7)).toList().contains(false);
    }

    public static boolean depositAllExcept(Pattern... patterns) {
        var items = InventoryQuery.newQuery(93).option("Deposit-All").results().stream().filter(i ->
                !Arrays.stream(patterns).map(p ->
                        p.matcher(i.getName()).matches()
                ).toList().contains(true)
        );
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
        return deposit(InventoryQuery.newQuery(93).itemId(itemId), option);
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
        return deposit(InventoryQuery.newQuery(93).itemName(name, spred), option);
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
     *
     *   Loads the given preset number.
     *
     *   @param presetNumber the preset number to load
     *   @return true if the preset was successfully loaded, false otherwise
     *   @throws InterruptedException if the thread is interrupted while sleeping
     */
    public static boolean loadPreset(int presetNumber) {
        int presetBrowsingValue = VariableManager.getVarbitValue(PRESET_BROWSING_VARBIT_ID);
        if ((presetNumber >= 10 && presetBrowsingValue < 1) || (presetNumber < 10 && presetBrowsingValue > 0)) {
            Delay.delay(Random.nextInt(300, 700));
            MiniMenu.doAction(ComponentAction.COMPONENT.getType(), 1, 100, 33882231);
        }
        return MiniMenu.doAction(ComponentAction.COMPONENT.getType(), 1, presetNumber % 9, 33882231);//presetComp != null && presetComp.doAction("Load");
    }

    /**
     *
     *   Gets the value of a varbit in the inventory.
     *
     *   @param slot The inventory slot to check.
     *   @param varbitId The varbit id to check.
     *   @return The value of the varbit.
     */
    public static int getVarbitValue(int slot, int varbitId) {
        return VariableManager.getInventoryVarbit(95, slot, varbitId);
    }
}
