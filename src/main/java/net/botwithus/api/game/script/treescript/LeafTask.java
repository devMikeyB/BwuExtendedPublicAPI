package net.botwithus.api.game.script.treescript;

import org.jetbrains.annotations.Nullable;

public abstract class LeafTask extends TreeTask {
    @Override
    public abstract void execute();

    @Nullable
    @Override
    public TreeTask successTask() {
        return null;
    }

    @Override
    public boolean validate() {
        return false;
    }

    @Nullable
    @Override
    public TreeTask failureTask() {
        return null;
    }

    @Override
    public final boolean isLeaf() {
        return true;
    }
}
