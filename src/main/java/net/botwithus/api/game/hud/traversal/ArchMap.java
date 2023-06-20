package net.botwithus.api.game.hud.traversal;

import net.botwithus.rs3.interfaces.Interface;
import net.botwithus.rs3.menu.MiniMenu;
import net.botwithus.rs3.menu.types.ComponentAction;
import net.botwithus.rs3.menu.types.ObjectAction;
import net.botwithus.rs3.queries.Queries;
import net.botwithus.rs3.queries.builders.objects.SceneObjectQuery;
import net.botwithus.rs3.script.Delay;

import java.util.Locale;

public class ArchMap {
    public static boolean isOpen() {
        return Interface.isInterfaceOpen(667);
    }

    public static void open() {
        var mapObj = SceneObjectQuery.newQuery().name("Dig sites map").results().nearest();
        var player = Queries.self();
        if (mapObj.isPresent() && player != null && player.getPosition().getZ() == mapObj.get().getPosition().getZ()) {
            mapObj.get().doAction(ObjectAction.OBJECT1);
        }
    }

    public static void chooseDigsite(Digsite digsite) {
        chooseDigsite(digsite.getMapId());
    }

    public static void chooseDigsite(int mapId) {
        MiniMenu.doAction(ComponentAction.COMPONENT.getType(), 1, mapId, 43712523);
        Delay.delay(800);
        MiniMenu.doAction(ComponentAction.COMPONENT.getType(), 1, -1, 43712535);
    }
}

enum Digsite {
    KHARID_ET(0),
    EVERLIGHT(1),
    INFERNAL_SOURCE(2),
    STORMGUARD(3),
    WARFORGE(4),
    ORTHEN(5),
    SENNTISTEN(6),
    ARCH_GUILD(-1);

    private final int mapId;

    Digsite(int mapId) {
        this.mapId = mapId;
    }

    public int getMapId() {
        return mapId;
    }

    @Override
    public String toString() {
        return this.name().substring(0, 1).toUpperCase(Locale.ROOT) + this.name().substring(1).toLowerCase(Locale.ROOT).replace('_', ' ').replace('$', '-');
    }
}

