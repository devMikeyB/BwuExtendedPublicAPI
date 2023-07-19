package net.botwithus.api.game.world;

import net.botwithus.rs3.Client;
import net.botwithus.rs3.util.RandomGenerator;
import net.botwithus.rs3.world.Area;
import net.botwithus.rs3.world.Coordinate;
import net.botwithus.rs3.world.Travel;

public class Traverse {
    public static boolean to(Coordinate coordinate) {
        var player = Client.getLocalPlayer();
        if (player == null) {
            return false;
        }
        return Travel.walkTo(coordinate);
    }

    public static boolean to(Area area) {
        return to(area.getCoordinates().get(RandomGenerator.nextInt(area.getCoordinates().size())));
    }
}
