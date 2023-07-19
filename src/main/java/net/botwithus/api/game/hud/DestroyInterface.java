package net.botwithus.api.game.hud;

import net.botwithus.api.game.hud.inventories.Backpack;
import net.botwithus.rs3.interfaces.Interfaces;
import net.botwithus.rs3.minimenu.MiniMenu;
import net.botwithus.rs3.minimenu.types.ComponentAction;
import net.botwithus.rs3.script.Execution;
import net.botwithus.rs3.util.RandomGenerator;

public class DestroyInterface {
    public static final int NO_ID = 77529094, YES_ID = 77529093;

    public static boolean isOpen() {
        return Interfaces.isOpen(1183);
    }

    /**
     * Destroys an item in the player's backpack.
     *
     * @param name The name of the item to be destroyed.
     */
    public static void destroyItem(String name) {
        if (Backpack.contains(name) && !isOpen()) {
            Backpack.interact(name, 8);
            Execution.delayUntil(RandomGenerator.nextInt(800, 1400), DestroyInterface::isOpen);
        }
        if (isOpen()) {
            MiniMenu.interact(ComponentAction.COMPONENT.getType(), 0, -1, YES_ID);
        }
    }
}
