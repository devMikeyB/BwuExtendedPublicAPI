package net.botwithus.api.game.hud.traversal;


import net.botwithus.rs3.interfaces.Interface;
import net.botwithus.rs3.menu.MiniMenu;
import net.botwithus.rs3.menu.types.ComponentAction;
import net.botwithus.rs3.queries.builders.components.ComponentQuery;

public class ChooseDestination {
    private static final int[] INDEX_IDS = { 47185921, 47185940, 47185943, 47185946, 47185949, 47185952, 47185955, 47185958 },
        OPTIONS_INDICES = { 14, 21,  24, 27, 30, 33, 36, 39, 42, 45 };
    private static final int CAN_SELECT_TEXTURE_ID = 184741, CANNOT_SELECT_TEXTURE_ID = 18508;


    public static boolean isOpen() {
        return Interface.isInterfaceOpen(720);
    }

    public static void selectOption(int index) {
        MiniMenu.doAction(ComponentAction.DIALOGUE.getType(), 0, -1, INDEX_IDS[index]);
    }

    public static boolean canSelectOption(int index) {
        var component = ComponentQuery.newQuery(720).spriteId(CAN_SELECT_TEXTURE_ID).results().first();
        return component.isPresent();
    }
}