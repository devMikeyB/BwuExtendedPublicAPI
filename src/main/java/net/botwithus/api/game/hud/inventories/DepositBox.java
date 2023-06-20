package net.botwithus.api.game.hud.inventories;

import net.botwithus.rs3.interfaces.Component;
import net.botwithus.rs3.interfaces.Interface;
import net.botwithus.rs3.queries.builders.components.ComponentQuery;
import net.botwithus.rs3.queries.builders.inventories.InventoryQuery;
import net.botwithus.rs3.queries.builders.objects.SceneObjectQuery;
import net.botwithus.rs3.util.Regex;

import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static net.botwithus.api.game.hud.inventories.Backpack.BACKPACK;


public final class DepositBox {
    private DepositBox() {}

    /**
     *
     *   Opens the nearest deposit box and deposits all items.
     *
     *   @return true if the deposit box was opened and all items were deposited, false otherwise
     */
    public static boolean open() {
        var obj = SceneObjectQuery.newQuery().option("Deposit").option("Deposit-All", "Deposit all").results().nearest();
        return obj.map(sceneObject -> sceneObject.doAction("Deposit")).orElse(false);
    }

    /**
     *
     *   Checks if the interface is open
     *   @return true if the interface is open, false otherwise
     */
    public static boolean isOpen() {
        return Interface.isInterfaceOpen(11);
    }

    /**
     *
     *   Closes the interface with the given query.
     *
     *   @return true if the interface was closed, false otherwise
     */
    public static boolean close() {
        return Interface.find(ComponentQuery.newQuery(11)
                .option("Close"))
                .first()
                .map(Component::doAction)
                .orElse(false);
    }

    /**
     *
     *   Attempts to deposit all carried items.
     *
     *   @return true if the action was successful, false otherwise
     */
    public static boolean depositAll() {
        return Interface.find(ComponentQuery.newQuery(11)
                .option("Deposit Carried Items"))
                .first()
                .map(Component::doAction)
                .orElse(false);
    }


    public static boolean depositAllExcept(String... itemNames) {
        var items = ComponentQuery.newQuery(11).itemName(Regex.getPatternForNotContainingAnyString(itemNames)).option("Deposit").results();
        return !items.stream().map(i -> BACKPACK.doAction(i.getComponentIndex(), 1)).toList().contains(false);
    }

    public static boolean depositAllExcept(int... ids) {
        var idSet = Arrays.stream(ids).boxed().collect(Collectors.toSet());
        var items = InventoryQuery.newQuery(93).option("Deposit").results().stream().filter(i -> !idSet.contains(i.getItemId()));
        return !items.map(i -> BACKPACK.doAction(i.getSlot(), 1)).toList().contains(false);
    }

    public static boolean depositAllExcept(Pattern... patterns) {
        var items = InventoryQuery.newQuery(93).option("Deposit").results().stream().filter(i ->
                !Arrays.stream(patterns).map(p ->
                        p.matcher(i.getName()).matches()
                ).toList().contains(true)
        );
        return !items.map(i -> BACKPACK.doAction(i.getSlot(), 1)).toList().contains(false);
    }

    /**
     *
     *   Attempts to deposit all worn items.
     *
     *   @return {@code true} if the action was successful, {@code false} otherwise
     */
    public static boolean depositWornItems() {
        return Interface.find(ComponentQuery.newQuery(11)
                .option("Deposit Worn Items"))
                .first()
                .map(Component::doAction)
                .orElse(false);
    }

    /**
     *
     *   Attempts to deposit the familiar's items.
     *
     *   @return true if the action was successful, false otherwise
     */
    public static boolean depositFamiliarItems() {
        return Interface.find(ComponentQuery.newQuery(11)
                .option("Deposit Familiar's Items"))
                .first()
                .map(Component::doAction)
                .orElse(false);
    }

    /**
     *
     *   Attempts to deposit the money pouch.
     *
     *   @return true if the money pouch was successfully deposited, false otherwise
     */
    public static boolean depositMoneyPouch() {
        return Interface.find(ComponentQuery.newQuery(11)
                .option("Deposit Money Pouch"))
                .first()
                .map(Component::doAction)
                .orElse(false);
    }
}
