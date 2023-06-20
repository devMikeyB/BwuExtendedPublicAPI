package net.botwithus.api.game.world;

import net.botwithus.rs3.util.Random;
import net.botwithus.rs3.queries.Queries;
import net.botwithus.rs3.world.Area;
import net.botwithus.rs3.world.Position;
import net.botwithus.rs3.world.World;

public class Traverse {
    public static boolean to(Position position) {
        var player = Queries.self();
        if (player == null)
            return false;
        if (player.distance(position) > 60) {
            return World.bresenhamWalkTo(position, false, Random.nextInt(38, 68));
        } else {
            return World.walkTo(position);
        }
    }

    public static boolean to(Area area) {
        return to(area.getRandomPosition());
    }
}
