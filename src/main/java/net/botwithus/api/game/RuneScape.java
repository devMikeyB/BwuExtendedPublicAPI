package net.botwithus.api.game;

import net.botwithus.rs3.Client;
import net.botwithus.rs3.queries.Queries;

public class RuneScape {
    public static boolean isLoggedIn() {
        return Client.getGameState() == Client.GameState.LOGGED_IN && Queries.self() != null;
    }
}
