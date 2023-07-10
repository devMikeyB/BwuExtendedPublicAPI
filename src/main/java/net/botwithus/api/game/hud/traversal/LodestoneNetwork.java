package net.botwithus.api.game.hud.traversal;

import net.botwithus.rs3.interfaces.Interface;
import net.botwithus.rs3.queries.builders.components.ComponentQuery;

public class LodestoneNetwork {
    public static boolean isOpen() {
        return Interface.isInterfaceOpen(1092);
    }

    /**
     * Opens the Lodestone Network interface.
     *
     * @return {@code true} if the interface was opened, {@code false} otherwise.
     */
    public static boolean open() {
        return Interface.find(ComponentQuery.newQuery(1465).option("Lodestone network")).first().map(
                c -> c.doAction("Lodestone network")).orElse(false);
    }

    /**
     * Teleports the player to their previous destination.
     *
     * @return true if the player was successfully teleported, false otherwise
     */
    public static boolean teleportToPreviousDestination() {
        return Interface.find(ComponentQuery.newQuery(1465).option("Previous Destination")).first().map(
                c -> c.doAction("Previous Destination")).orElse(false);
    }
}
