package net.botwithus.api.game.script.treescript;


public abstract class BranchTask extends TreeTask {
    public boolean validate;

    @Override
    public void execute() {};

    @Override
    public abstract TreeTask successTask();

    @Override
    public abstract boolean validate();

    @Override
    public abstract TreeTask failureTask();

    @Override
    public final boolean isLeaf() {
        return false;
    }
}
