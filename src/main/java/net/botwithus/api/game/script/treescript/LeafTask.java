package net.botwithus.api.game.script.treescript;

public abstract class LeafTask extends TreeTask {
    @Override
    public abstract void execute();

    @Override
    public TreeTask successTask() {
        return null;
    }

    @Override
    public boolean validate() {
        return false;
    }

    @Override
    public TreeTask failureTask() {
        return null;
    }

    @Override
    public final boolean isLeaf() {
        return true;
    }
}