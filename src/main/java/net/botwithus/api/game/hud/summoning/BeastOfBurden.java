package net.botwithus.api.game.hud.summoning;

import net.botwithus.rs3.interfaces.item.Item;
import net.botwithus.rs3.queries.ResultSet;
import net.botwithus.rs3.queries.builders.inventories.InventoryQuery;

import java.util.List;

public class BeastOfBurden {
    public static ResultSet<Item> getItems() {
        return InventoryQuery.newQuery(530).results();
    }

    public static List<Item> getPopulatedItems() {
        return getItems().stream().filter(i -> i.getId() > 0).toList();
    }
}
