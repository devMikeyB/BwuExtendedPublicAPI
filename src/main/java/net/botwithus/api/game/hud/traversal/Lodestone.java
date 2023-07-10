package net.botwithus.api.game.hud.traversal;


import net.botwithus.rs3.menu.MiniMenu;
import net.botwithus.rs3.menu.types.ComponentAction;
import net.botwithus.rs3.queries.Queries;
import net.botwithus.rs3.script.Delay;
import net.botwithus.rs3.util.Random;
import net.botwithus.rs3.variables.VariableManager;

public enum Lodestone {
    AL_KHARID(71565323, 28),
    ANACHRONIA(71565337, 44270),
    ARDOUGNE(71565324, 29),
    ASHDALE(71565346, 35), // TODO Need Varbit to know if they're available or not
    BANDIT_CAMP(71565321, 9482),
    BURTHORPE(71565325, 30),
    CANIFIS(71565339, 18523),
    CATHERBY(71565326, 31),
    DRAYNOR_VILLAGE(71565327, 32),
    EDGEVILLE(71565328, 33),
    EAGLES_PEAK(71565340, 18524),
    FALADOR(71565329, 34),
    FORT_FORINTHRY(71565335, 35), // TODO Need Varbit to know if they're available or not
    FREMENNIK_PROVINCE(71565341, 18525),
    KARAMJA(71565342, 18526),
    LUMBRIDGE(71565330, 35),
    LUNAR_ISLE(71565322, 9482),
    MENAPHOS(71565336, 35), // TODO Need Varbit to know if they're available or not
    OOGLOG(71565343, 18527),
    PORT_SARIM(71565331, 36),
    PRIFDDINAS(71565347, 24967),
    SEERS_VILLAGE(71565332, 37),
    TAVERLY(71565333, 38),
    TIRANNWN(71565344, 18528),
    VARROCK(71565334, 39),
    WILDERNESS_CRATER(71565345, 18529),
    YANILLE(71565338, 40);

    private final int interactId;
    private final int varbitId;

    Lodestone(int interactId, int varbitId) {
        this.interactId = interactId;
        this.varbitId = varbitId;
    }

    //TODO: Update to no longer use MiniMenu.doAction
        public boolean teleport() {
        var player = Client.self();
        if (player == null) {
            return false;
        }
        var coordinate = player.getPosition();
        if (!LodestoneNetwork.isOpen()) {
            LodestoneNetwork.open();
            Delay.delay(Random.nextInt(600, 900));
        }
        if (MiniMenu.doAction(ComponentAction.COMPONENT.getType(), 1, -1, interactId)) {
            Delay.delay(Random.nextInt(12000, 14000));
            return true;
        } else {
            return false;
        }
//        Time.waitUntil(() -> {
//            var newCoordinate = ApexPlayer.getPosition();
//            return coordinate != null && newCoordinate != null && !coordinate.equals(newCoordinate);
//        }, Random.nextInt(12000, 14000), 1000);
    }

    public boolean isAvailable() {
        var result = VariableManager.getVarbitValue(varbitId);
        switch (this) {
            case LUNAR_ISLE -> {
                return result >= 100;
            }
            case BANDIT_CAMP -> {
                return result >= 15;
            }
        }
        return result == 1;
    }
}
