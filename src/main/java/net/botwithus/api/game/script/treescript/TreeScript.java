package net.botwithus.api.game.script.treescript;

import net.botwithus.internal.plugins.ScriptDefinition;
import net.botwithus.rs3.script.Script;

public abstract class TreeScript extends Script {
    private BranchTask rootTask;

    public TreeScript(String name, ScriptDefinition scriptDefinition) {
        super(name, scriptDefinition);
    }

    public abstract BranchTask getRootTask();

    @Override
    public void onLoop() {
        if (rootTask != null) {
            TreeTask.traverse(rootTask);
        } else {
            rootTask = getRootTask();
        }
    }
}
