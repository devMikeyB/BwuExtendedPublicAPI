package net.botwithus.api.game.script.treescript;

import net.botwithus.api.game.script.treescript.interfaces.ITreeTask;

/**
 * Represents a task in a tree structure. This is an abstract base class for
 * all types of tasks.
 */
public abstract class TreeTask implements ITreeTask {

    /** {@inheritDoc} */
    @Override
    public abstract void execute();

    /** {@inheritDoc} */
    @Override
    public abstract TreeTask successTask();

    /** {@inheritDoc} */
    @Override
    public abstract boolean validate();

    /** {@inheritDoc} */
    @Override
    public abstract TreeTask failureTask();

    /** {@inheritDoc} */

    @Override
    public abstract boolean isLeaf();

    /**
     * Traverses the given tree of tasks, executing the appropriate task
     * based on the result of validation.
     * @param treeTask The root task of the tree to traverse.
     */
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
