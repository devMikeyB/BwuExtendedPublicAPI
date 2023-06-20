package net.botwithus.api.game.hud.prayer;

import net.botwithus.rs3.menu.Interactable;
import net.botwithus.rs3.menu.types.ComponentAction;

public sealed interface PrayerAbility extends Interactable<ComponentAction> permits AncientBook, NormalBook {

    boolean isActive();

}
