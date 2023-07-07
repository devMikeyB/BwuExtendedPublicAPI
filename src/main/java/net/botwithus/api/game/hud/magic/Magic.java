package net.botwithus.api.game.hud.magic;

import net.botwithus.rs3.types.configs.ConfigManager;
import net.botwithus.rs3.types.StructType;
import net.botwithus.rs3.types.enums.EnumType;
import net.botwithus.rs3.types.enums.Value;

import java.util.Objects;

public final class Magic {
    private Magic() {}

    /**
     *
     *   Retrieves a Spell object from the ConfigManager based on the given spellId.
     *
     *   @param spellId The id of the spell to retrieve
     *   @return A Spell object with the given spellId
     *   @throws IllegalArgumentException if the given spellId is invalid
     */
    public static Spell getSpell(int spellId) {
        if(spellId < 1) {
            throw new IllegalArgumentException("Invalid Spell Id");
        }
        EnumType spells = ConfigManager.getEnumType(6740);
        Value spellStruct = spells.variants.get(spellId);
        StructType spellInfo = ConfigManager.getStructType(spellStruct.getIntegerValue());
        return new Spell(spellInfo);
    }

    /**
     *
     *   Retrieves a {@link Spell} object from the game's configuration files by its name.
     *
     *   @param name The name of the spell to retrieve
     *   @return The {@link Spell} object associated with the given name
     *   @throws IllegalArgumentException if the given name does not match any spell
     */
    public static Spell getSpellByName(String name) {
        Objects.requireNonNull(name);
        EnumType spells = ConfigManager.getEnumType(6740);
        for (Value value : spells.variants.values()) {
            int structId = value.getIntegerValue();
            StructType spellInfo = ConfigManager.getStructType(structId);
            if(spellInfo.getString(2974).equalsIgnoreCase(name)) {
                return new Spell(spellInfo);
            }
        }
        throw new IllegalArgumentException("Unknown spell " + name);
    }

}
