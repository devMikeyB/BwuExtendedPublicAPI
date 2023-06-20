package net.botwithus.api.game.hud;

import net.botwithus.rs3.interfaces.Component;
import net.botwithus.rs3.interfaces.Interface;
import net.botwithus.rs3.menu.MiniMenu;
import net.botwithus.rs3.menu.types.ComponentAction;
import net.botwithus.rs3.queries.builders.components.ComponentQuery;

import java.util.ArrayList;
import java.util.List;

public class Dialog {

    public static boolean isOpen() {
        return  Interface.isInterfaceOpen(1184) || Interface.isInterfaceOpen(1188) || Interface.isInterfaceOpen(1189) || Interface.isInterfaceOpen(1191);
    }

    public static boolean doContinue() {
        if (Interface.isInterfaceOpen(1184)) {
            return MiniMenu.doAction(ComponentAction.DIALOGUE.getType(), 0, -1, 77594639);
        } else if (Interface.isInterfaceOpen(1189)) {
            return MiniMenu.doAction(ComponentAction.DIALOGUE.getType(), 0, -1, 77922323);
        } else if (Interface.isInterfaceOpen(1191)) {
            return MiniMenu.doAction(ComponentAction.DIALOGUE.getType(), 0, -1, 78053391);
        }
        return false;
    }

    public static List<String> getOptions() {
        if (Interface.isInterfaceOpen(1188)) {
            return ComponentQuery.newQuery(1188).componentIndex(8, 13, 18, 23, 28).type(4).results().stream().map(Component::getText).toList();
        }
        return new ArrayList<>();
    }

    public static boolean doAction(String optionText) {
        if (Interface.isInterfaceOpen(1188)) {
            return ComponentQuery.newQuery(1188).type(4).text(optionText, String::contentEquals).results().first().map(Component::doAction).orElse(false);
        }
        return false;
    }

    /**
     *
     *   Retrieves the text from a component if the interface is open.
     *
     *   @return The text from the component, or an empty string if the interface is not open.
     */
    public static String getText() {
        if (isOpen()) {
            var comp = Interface.find(ComponentQuery.newQuery(1184).componentIndex(10)).first();
            return comp.isPresent() ? comp.get().getText() : "";
        }
        return "";
    }

    public static String getTitle() {
        if (Interface.isInterfaceOpen(1184)) {
            return ComponentQuery.newQuery(1184).type(4).results().first().map(Component::getText).orElse("");
        } else if (Interface.isInterfaceOpen(1188)) {
            return ComponentQuery.newQuery(1188).type(4).results().first().map(Component::getText).orElse("");
        } else if (Interface.isInterfaceOpen(1189)) {
            return ComponentQuery.newQuery(1189).type(4).results().first().map(Component::getText).orElse("");
        } else if (Interface.isInterfaceOpen(1191)) {
            return ComponentQuery.newQuery(1191).type(4).results().first().map(Component::getText).orElse("");
        }
        return "";
    }
}
