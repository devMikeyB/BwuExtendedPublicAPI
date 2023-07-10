package net.botwithus.api.game.script.treescript;


import net.botwithus.api.game.script.treescript.interfaces.ITreeTask;

public abstract class TreeTask implements ITreeTask {
    @Override
    public abstract void execute();

    @Override
    public abstract TreeTask successTask();

    @Override
    public abstract boolean validate();

    @Override
    public abstract TreeTask failureTask();

    @Override
    public abstract boolean isLeaf();

    public static void traverse(TreeTask treeTask) {
        var validate = treeTask.validate();
        System.out.println("[TreeTaskBranch] " + treeTask.getClass().getSimpleName() + ": " + validate);
        if (!treeTask.isLeaf()) {
            traverse(validate ? treeTask.successTask() : treeTask.failureTask());
        } else {
            treeTask.execute();
        }
    }
}
