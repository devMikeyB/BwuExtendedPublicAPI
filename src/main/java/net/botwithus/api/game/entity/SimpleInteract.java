package net.botwithus.api.game.entity;

import net.botwithus.api.game.world.Traverse;
import net.botwithus.rs3.queries.Queries;
import net.botwithus.rs3.queries.builders.npc.NpcQuery;
import net.botwithus.rs3.queries.builders.objects.SceneObjectQuery;
import net.botwithus.rs3.script.Delay;
import net.botwithus.rs3.util.Random;
import net.botwithus.rs3.world.Area;
import net.botwithus.rs3.world.navigation.computations.Distance;

public class SimpleInteract {

    public static boolean interactWithObj(String name, String action, Area area) {
        var obj = SceneObjectQuery.newQuery().name(name).option(action).results().nearestTo(area.getRandomPosition());
        var player = Queries.self();

        if (player != null && obj.isPresent()) {
            System.out.println("Interacting with " + name + " with action \"" + action + "\".");

            if (Distance.to(obj.get()) > 60) {
                Traverse.to(new Area.Circular(obj.get().getPosition(), 5));
                return true;
            } else if (obj.get().doAction(action)) {
                Delay.delay(Random.nextInt(100, 1400));
                return true;
            }
        }
        return false;
    }

    public static boolean interactWithNpc(String name, String action, Area area) {
        var npc = NpcQuery.newQuery().name(name).option(action).results().nearestTo(area.getRandomPosition());
        var player = Queries.self();

        if (player != null && npc.isPresent()) {

            System.out.println("Interacting with " + name + " with action \"" + action + "\".");

            if (Distance.to(npc.get()) > 60) {
                Traverse.to(new Area.Circular(npc.get().getPosition(), 5));
                return true;
            } else if (npc.get().doAction(action)) {
                Delay.delay(Random.nextInt(100, 1400));
                return true;
            }
        }
        return false;
    }
}
