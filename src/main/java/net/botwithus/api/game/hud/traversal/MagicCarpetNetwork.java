package net.botwithus.api.game.hud.traversal;

import net.botwithus.rs3.interfaces.Interface;
import net.botwithus.rs3.queries.builders.npc.NpcQuery;

public class MagicCarpetNetwork {
    /**
     *
     *   Checks if the interface with the given ID is open.
     *
     *   @return true if the interface is open, false otherwise.
     */
    public static boolean isOpen() {
        return Interface.isInterfaceOpen(1928);
    }

    /**
     *
     *   Opens the rug merchant's shop.
     *
     *   @return {@code true} if the shop was successfully opened, {@code false} otherwise.
     */
    public static boolean open() {
        var npc = NpcQuery.newQuery().name("Rug merchant").option("Travel").results().nearest();
        return npc.isPresent() && npc.get().doAction("Travel");
    }
}
