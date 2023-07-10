package net.botwithus.api.game.hud;

import net.botwithus.rs3.minimenu.MiniMenu;
import net.botwithus.rs3.minimenu.types.ComponentAction;
import net.botwithus.rs3.queries.builders.components.ComponentQuery;
import net.botwithus.rs3.script.Delay;

public final class Hud {
    private Hud() {
    }

    /**
     * Logs out the player.
     *
     * @return true if the logout was successful, false otherwise
     */
    public static boolean logout() {
        Component logout = ComponentQuery.newQuery(1477).option("Logout").results().first();
        if (logout != null) {
            logout.doAction("Logout");
            if (Delay.delayUntil(5000, self -> Interface.isInterfaceOpen(1433))) {
                return MiniMenu.doAction(ComponentAction.COMPONENT.getType(), 1, -1, (1433 << 16) | 71);
            }
        }
        return false;
    }

    /**
     * Opens the player's backpack.
     *
     * @return true if the backpack was opened, false otherwise
     */
    public static boolean openBackpack() {
        Component component = Interface.find(ComponentQuery.newQuery(1431).option("Open Backpack")).first();
        return component != null && component.doAction("Open Backpack");
    }

    /**
     * Opens the Skills tab in the game interface.
     *
     * @return true if the Skills tab was successfully opened, false otherwise
     */
    public static boolean openSkills() {
        Component component = Interface.find(ComponentQuery.newQuery(1431).option("Open Skills")).first();
        return component != null && component.doAction("Open Skills");
    }

    /**
     * Opens the worn equipment interface.
     *
     * @return true if the interface was opened, false otherwise
     */
    public static boolean openWornEquipment() {
        Component component = Interface.find(ComponentQuery.newQuery(1431).option("Open Worn Equipment")).first();
        return component != null && component.doAction("Open Worn Equipment");
    }
}
