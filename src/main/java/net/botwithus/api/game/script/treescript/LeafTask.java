package net.botwithus.api.game.script.treescript;

/**
 * Represents a leaf task in a tree. This task does not have any child tasks.
 */
public abstract class LeafTask extends TreeTask {

    /** {@inheritDoc} */
    @Override
    public abstract void execute();

    /** {@inheritDoc}
     * Does not apply in LeafTasks */
    @Override
    public TreeTask successTask() {
        return null;
    }

    /** {@inheritDoc}
     * Does not apply in LeafTasks*/
    @Override
    public boolean validate() {
        return false;
    }

    /** {@inheritDoc}
     * Does not apply in LeafTasks*/
    @Override
    public TreeTask failureTask() {
        return null;
    }

    /** {@inheritDoc}
     * Will return True for LeafTasks*/
    @Override
    public final boolean isLeaf() {
        return true;
    }
}
