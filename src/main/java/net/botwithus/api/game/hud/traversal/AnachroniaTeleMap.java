package net.botwithus.api.game.hud.traversal;

import net.botwithus.rs3.entities.pathing.player.Player;
import net.botwithus.rs3.interfaces.Interface;
import net.botwithus.rs3.menu.MiniMenu;
import net.botwithus.rs3.menu.types.ComponentAction;
import net.botwithus.rs3.menu.types.ObjectAction;
import net.botwithus.rs3.queries.Queries;
import net.botwithus.rs3.queries.builders.objects.SceneObjectQuery;
import net.botwithus.rs3.script.Delay;
import net.botwithus.rs3.util.Random;

public class AnachroniaTeleMap {
    public static boolean isOpen() {
        return Interface.isInterfaceOpen(71);
    }

    public static void open() {
        var mapObj = SceneObjectQuery.newQuery().name("Orthen teleportation device").results().nearest();
        Player player = Queries.self();
        if (mapObj.isPresent() && player != null && player.getPosition().getZ() == mapObj.get().getPosition().getZ()) {
            mapObj.get().doAction(ObjectAction.OBJECT1);
        }
    }

    public static void chooseDigsite(int index) {
        MiniMenu.doAction(ComponentAction.COMPONENT.getType(), 1, index, 4653065);
        Delay.delay(Random.nextInt(2000, 4000));
        MiniMenu.doAction(ComponentAction.COMPONENT.getType(), 1, -1, 4653084);
    }
}
