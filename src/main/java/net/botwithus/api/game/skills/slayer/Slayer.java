package net.botwithus.api.game.skills.slayer;

import net.botwithus.rs3.types.configs.ConfigManager;
import net.botwithus.rs3.types.enums.EnumType;
import net.botwithus.rs3.types.enums.Value;
import net.botwithus.rs3.variables.VariableManager;

public final class Slayer {

    private Slayer() {}

    /**
     *
     *   Gets the current reaper assignment.
     *   @return The current reaper assignment, or "none" if none is active.
     */
    public String getReaperAssignment() {
        int taskId = VariableManager.getVarbitValue(22901);
        EnumType tasks = ConfigManager.getEnumType(9197);
        Value value = tasks.variants.get(taskId);
        if(value != null) {
            return value.getStringValue();
        }
        return "none";
    }

    /**
     *
     *   Gets the remaining reaper kills for the current task.
     *
     *   @return The remaining reaper kills for the current task.
     */
    public static int getRemainingReaperKills() {
        return VariableManager.getVarbitValue(22902);
    }

}
