package net.botwithus.api.game.hud.traversal;

import net.botwithus.rs3.script.Delay;
import net.botwithus.rs3.util.Random;
import net.botwithus.rs3.menu.MiniMenu;
import net.botwithus.rs3.menu.types.ComponentAction;

public enum MagicCarpet {
    AL_KHARID(1928, 28),
    NORTH_POLLNIVNEACH(1928, 60),
    SOUTH_POLLNIVNEACH(1928, 68),
    NARDAH(1928, 68),
    BEDABIN_CAMP(1928, 44),
    UZER(1928, 52),
    MENAPHOS(1928, 84),
    SOPHANEM(1928, 92);
    private final int interfaceIndex;
    private final int componentIndex;

    MagicCarpet(int interfaceIndex, int componentIndex) {
        this.interfaceIndex = interfaceIndex;
        this.componentIndex = componentIndex;
    }

    public int getId() {
        return interfaceIndex << 16 | componentIndex;
    }

    //TODO: Update to no longer use MiniMenu.doAction
    public boolean teleport() {
        if (!MagicCarpetNetwork.isOpen()) {
            MagicCarpetNetwork.open();
            Delay.delay(Random.nextInt(600, 900));
//            Time.waitUntil(MagicCarpetNetwork::isOpen, Random.nextInt(600, 1200), Random.nextInt(300, 600));
        }
        if (MagicCarpetNetwork.isOpen()) {
            return MiniMenu.doAction(ComponentAction.COMPONENT.getType(), 1, -1, getId());
        }
        return false;
    }
}

