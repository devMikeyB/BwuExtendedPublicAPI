package net.botwithus.api.game.hud.traversal;

import net.botwithus.rs3.Client;
import net.botwithus.rs3.entities.pathing.player.Player;
import net.botwithus.rs3.interfaces.Interfaces;
import net.botwithus.rs3.minimenu.MiniMenu;
import net.botwithus.rs3.minimenu.types.ComponentAction;
import net.botwithus.rs3.minimenu.types.ObjectAction;
import net.botwithus.rs3.queries.builders.objects.SceneObjectQuery;
import net.botwithus.rs3.script.Execution;
import net.botwithus.rs3.util.RandomGenerator;

public class AnachroniaTeleMap {
    public static boolean isOpen() {
        return Interfaces.isOpen(71);
    }

    public static void open() {
        var mapObj = SceneObjectQuery.newQuery().name("Orthen teleportation device").results().nearest();
        Player player = Client.getLocalPlayer();
        if (mapObj != null && player != null && player.getPosition() != null && mapObj.getPosition() != null &&
                player.getPosition().getZ() == mapObj.getPosition().getZ()) {
            mapObj.interact(ObjectAction.OBJECT1);
        }
    }

    public static void chooseDigsite(int index) {
         MiniMenu.interact(ComponentAction.COMPONENT.getType(), 1, index, 4653065);
        Execution.delay(RandomGenerator.nextInt(2000, 4000));
        MiniMenu.interact(ComponentAction.COMPONENT.getType(), 1, -1, 4653084);
    }
}
