package net.botwithus.api.game;

import net.botwithus.rs3.item.Item;
import net.botwithus.rs3.menu.MiniMenu;
import net.botwithus.rs3.menu.types.ComponentAction;
import net.botwithus.rs3.queries.ResultSet;
import net.botwithus.rs3.queries.builders.inventories.InventoryQuery;
import net.botwithus.rs3.variables.VariableManager;

import java.util.List;

public class Familiar {
    private static final int MINUTES_REMAINING_VARBIT_ID = 6055, CURRENT_HEALTH_VARBIT_ID = 19034, TOTAL_HEALTH_VARBIT_ID = 27403;

    private static final int MINUTE_REMAINING_VARP_ID = 1786, HEALTH_VARP_ID = 5194;

    public static int getMinutesRemaining() {
        var val = VariableManager.getVarbitValue(6055);
        //System.out.println("[Familiar#getMinutesRemaining: " + val);
        return val;
    }

    public static ResultSet<Item> getBobItems() {
        return InventoryQuery.newQuery(530).results();
    }

    public static List<Item> getBobPopulatedItems() {
        return getBobItems().stream().filter(i -> i.getItemId() > 0).toList();
    }

    public static void triggerSpecial() {
        MiniMenu.doAction(ComponentAction.COMPONENT.getType(), 1, -1, 43384870);
    }
}
