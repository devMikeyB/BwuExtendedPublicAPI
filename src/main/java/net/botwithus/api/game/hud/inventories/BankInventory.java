package net.botwithus.api.game.hud.inventories;

import net.botwithus.rs3.interfaces.Component;
import net.botwithus.rs3.item.Item;
import net.botwithus.rs3.queries.ResultSet;
import net.botwithus.rs3.queries.builders.components.ComponentQuery;
import net.botwithus.rs3.queries.builders.inventories.InventoryQuery;

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
    public boolean doAction(int slot, int option) {
        ResultSet<Item> results = InventoryQuery.newQuery(getId()).slot(slot).results();
        Optional<Item> item = results.first();
        if(item.isPresent()) {
            Item i = item.get();
            System.out.println("[Inventory#doAction(slot, option)]: " + i.getItemId());
            ResultSet<Component> queryResults = ComponentQuery.newQuery(interfaceIndex)
                    .item(i.getItemId())
                    .componentIndex(componentIndex)
                    .results();
            System.out.println("[Inventory#doAction(slot, option)]: QueryResults: " + queryResults.size());
            return queryResults.first().map(c -> c.doAction(option)).orElse(false);
        }
        return false;
    }
}
