package net.botwithus.api.game;

import net.botwithus.rs3.game.Client;

public class RuneScape {
    public static boolean isLoggedIn() {
        return Client.getGameState() == Client.GameState.LOGGED_IN && Client.getLocalPlayer() != null;
    }
}
