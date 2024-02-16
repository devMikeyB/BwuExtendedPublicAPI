package net.botwithus.api.game.hud;

import net.botwithus.rs3.game.hud.interfaces.Component;
import net.botwithus.rs3.game.hud.interfaces.Interfaces;
import net.botwithus.rs3.game.minimenu.MiniMenu;
import net.botwithus.rs3.game.minimenu.actions.ComponentAction;
import net.botwithus.rs3.game.queries.builders.components.ComponentQuery;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Dialog {
    public static boolean isOpen() {
        return Interfaces.areAnyOpen(1184, 1186,  1188, 1189, 1191);
    }

public static boolean select() {
    int[][] interfaceData = {
        {1184, 77594639},
        {1189, 77922323},
        {1191, 78053391}
    };

    for (int[] data : interfaceData) {
        if (Interfaces.isOpen(data[0])) {
            return MiniMenu.interact(ComponentAction.DIALOGUE.getType(), 0, -1, data[1]);
        }
    }

    return false;
}

    @NotNull
    public static List<String> getOptions() {
        if (Interfaces.isOpen(1188)) {
            List<String> options = new ArrayList<>(ComponentQuery.newQuery(1188).type(4).results().stream().map(Component::getText).toList());
            options.removeIf(result -> result.getBytes(StandardCharsets.UTF_8).length < 3);
            return options;
        }
        return Collections.emptyList();
    }
    public static boolean hasOption(String string) {
        return getOptions().stream().anyMatch(i -> i.contentEquals(string));
    }

    public static boolean interact(String optionText) {
        if (Interfaces.isOpen(1188)) {
            var result = ComponentQuery.newQuery(1188).type(4).text(optionText, String::contentEquals).results().first();
            if (result != null) {
                int slot = -1;
                int size = getOptions().size();
                for (int i = 0; i < size; i++) {
                    if (getOptions().get(i).contains(optionText)) {
                        slot = i;
                    }
                }
                if (slot != -1) {
                    int[] opcode = new int[]{77856776, 77856781, 77856786, 77856791, 77856796};
                    return MiniMenu.interact(16, 0, -1, opcode[slot]);
                }
            }
            return result != null && result.interact();
        }
        return false;
    }

    /**
     * Retrieves the text from a component if the interface is open.
     *
     * @return The text from the component, or an empty string if the interface is not open.
     */
    @Nullable
    public static String getText() {
        if (isOpen()) {
            var result = ComponentQuery.newQuery(1184).componentIndex(10).results().first();
            if (result != null && result.getText() != null) {
                return result.getText();
            } else {
                result = ComponentQuery.newQuery(1189).componentIndex(3).results().first();
                if (result != null && result.getText() != null) {
                    return result.getText();
                }
            }
        }
        return null;
    }

@Nullable
public static String getTitle() {
    int[] interfaceIds = {1184, 1188, 1189, 1191};
    Component result = null;

    for (int id : interfaceIds) {
        if (Interfaces.isOpen(id)) {
            result = ComponentQuery.newQuery(id).type(4).results().first();
            break;
        }
    }

    return result != null ? result.getText() : null;
}
}
