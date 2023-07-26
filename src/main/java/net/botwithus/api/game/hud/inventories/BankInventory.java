package net.botwithus.api.game.hud.inventories;

import net.botwithus.rs3.game.hud.interfaces.Component;
import net.botwithus.rs3.game.Item;
import net.botwithus.rs3.queries.ResultSet;
import net.botwithus.rs3.queries.builders.components.ComponentQuery;
import net.botwithus.rs3.queries.builders.items.ItemQuery;

import java.util.Optional;
import java.util.function.Function;

public class BankInventory extends Inventory {
    private static final Function<Integer, Integer> OPTION_MAPPER = i -> switch (i) {
        case 2 -> 3;
        case 3 -> 4;
        case 4 -> 5;
        case 5 -> 6;
        case 6 -> 7;
        case 7 -> 8;
        default -> 1;
    };

    public BankInventory() {
        super(95, 517, 195, OPTION_MAPPER);
    }

    @Override
    public boolean interact(int slot, int option) {
        ResultSet<Item> results = ItemQuery.newQuery(getId()).slots(slot).results();
        Item item = results.first();
        if (item != null) {
            System.out.println("[Inventory#interact(slot, option)]: " + item.getId());
            ResultSet<Component> queryResults = ComponentQuery.newQuery(interfaceIndex).item(
                    item.getId()).componentIndex(componentIndex).results();
            System.out.println("[Inventory#interact(slot, option)]: QueryResults: " + queryResults.size());
            var result = queryResults.first();
            return result != null && result.interact(option);
        }
        return false;
    }
}
