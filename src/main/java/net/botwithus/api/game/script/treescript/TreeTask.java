package net.botwithus.api.game.script.treescript;

import com.google.common.flogger.FluentLogger;
import net.botwithus.api.game.script.treescript.interfaces.ITreeTask;

/**
 * Represents a task in a tree structure. This is an abstract base class for
 * all types of tasks.
 */
public abstract class TreeTask implements ITreeTask {
    private static final FluentLogger log = FluentLogger.forEnclosingClass();

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
        if (!treeTask.isLeaf()) {
            log.atInfo().log("[TreeTaskBranch] " + treeTask.getClass().getSimpleName() + ": " + validate);
            traverse(validate ? treeTask.successTask() : treeTask.failureTask());
        } else {
            treeTask.execute();
        }
    }
}
