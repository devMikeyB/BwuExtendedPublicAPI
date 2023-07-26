//package net.botwithus.api.game.hud.prayer;
//
//import net.botwithus.rs3.menu.types.ComponentAction;
//import net.botwithus.rs3.queries.builders.components.ComponentQuery;
//import net.botwithus.rs3.types.StructType;
//import net.botwithus.rs3.game.js5.types.configs.ConfigManager;
//import net.botwithus.rs3.variables.VarManager;
//
//public enum NormalBook implements PrayerAbility {
//    THICK_SKIN(14541, 16739), ROCK_SKIN(14542, 16739), STEEL_SKIN(14543, 16739), BURST_OF_STRENGTH(14545,
//                                                                                                   16740), SUPERHUMAN_STRENGTH(
//            14546, 16740), ULTIMATE_STRENGTH(14547, 16740), CLARITY_OF_THOUGHT(14549, 16741), IMPROVED_REFLEXES(14550,
//                                                                                                                16741), INCREDIBLE_REFLEXES(
//            14551, 16741), SHARP_EYE(14553, 16751), HAWK_EYE(14554, 16751), EAGLE_EYE(14555, 16751), UNSTOPPABLE_FORCE(
//            14557, 16753), UNRELENTING_FORCE(14558, 16753), OVERPOWERING_FORCE(14559, 16753), MYSTIC_WILL(14561,
//                                                                                                          16752), MYSTIC_LORE(
//            14562, 16752), MYSTIC_MIGHT(14563, 16752), CHARGE(14565, 16754), SUPER_CHARGE(14566, 16754), OVERCHARGE(
//            14567, 16754), CHIVALRY(14568, 16756), PIETY(14569, 16757), AUGURY(14570, 16759), RIGOUR(14571,
//                                                                                                     16760), RAPID_RESTORE(
//            14572, 16742), RAPID_HEAL(14573, 16743), RAPID_RENEWAL(14574, 16758), PROTECT_ITEM(14575,
//                                                                                               16744), PROTECT_FROM_MAGIC(
//            14576, 16745), PROTECT_FROM_MISSILES(14577, 16746), PROTECT_FROM_MELEE(14578,
//                                                                                   16747), PROTECT_FROM_SUMMONING(14579,
//                                                                                                                  16755), RETRIBUTION(
//            14580, 16748), REDEMPTION(14581, 16749), SMITE(14582, 16750);
//    private final int structId;
//    private final int varbitId;
//
//    NormalBook(int structId, int varbitId) {
//        this.structId = structId;
//        this.varbitId = varbitId;
//    }
//
//    public StructType getStruct() {
//        return ConfigManager.getStructType(structId);
//    }
//
//    public int getVarbitValue() {
//        return VarManager.getVarbitValue(varbitId);
//    }
//
//    public int getLevel() {
//        return getStruct().getInt(2807);
//    }
//
//    /**
//     * Checks if the object is active.
//     *
//     * @return true if the object is active, false otherwise
//     */
//    @Override
//    public boolean isActive() {
//        return getVarbitValue() == 1;
//    }
//
//    @Override
//    public boolean interact(ComponentAction type) {
//        int spriteId = getStruct().getInt(735);
//        return ComponentQuery.newQuery(1458).spriteId(spriteId).results().first().map(c -> c.interact(1)).orElse(false);
//    }
//
//    @Override
//    public boolean interact(int option) {
//        int spriteId = getStruct().getInt(735);
//        return ComponentQuery.newQuery(1458).spriteId(spriteId).results().first().map(c -> c.interact(option)).orElse(
//                false);
//    }
//}