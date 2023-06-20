package net.botwithus.api.game.hud.magic;

import net.botwithus.rs3.menu.Interactable;
import net.botwithus.rs3.menu.types.ComponentAction;

public interface MagicAbility extends Interactable<ComponentAction> {

    boolean isActive();
    boolean isAutoCasting();

}
