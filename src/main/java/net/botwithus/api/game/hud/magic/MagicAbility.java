package net.botwithus.api.game.hud.magic;


import net.botwithus.rs3.minimenu.Interactable;
import net.botwithus.rs3.minimenu.types.ComponentAction;

public interface MagicAbility extends Interactable<ComponentAction> {

    boolean isActive();

    boolean isAutoCasting();

}
