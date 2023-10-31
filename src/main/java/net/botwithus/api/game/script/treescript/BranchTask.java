package net.botwithus.api.game.script.treescript;

/**
 * Represents a branch task in a tree. This task is not a leaf node and
 * must provide success and failure tasks based on validation.
 */
public abstract class BranchTask extends TreeTask {

    /** {@inheritDoc}
     * Not Executed in Branch Tasks*/
    @Override
    public void execute() {
    }

    /** {@inheritDoc} */
    @Override
    public abstract TreeTask successTask();

    /** {@inheritDoc} */
    @Override
    public abstract boolean validate();

    /** {@inheritDoc} */
    @Override
    public abstract TreeTask failureTask();

    /** {@inheritDoc}
     * Will always be False for BranchTasks */
    @Override
    public final boolean isLeaf() {
        return false;
    }
}
