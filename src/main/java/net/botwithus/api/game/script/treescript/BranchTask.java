package net.botwithus.api.game.script.treescript;

import net.botwithus.rs3.script.Script;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Represents a branch task in a tree. This task is not a leaf node and
 * must provide success and failure tasks based on validation.
 */
public class BranchTask extends TreeTask {
    private Permissive[] permissives = new Permissive[0];
    private TreeTask successTask, failureTask;

    public BranchTask(Script script, String desc) {
        super(script, desc);
    }

    public BranchTask(Script script, String desc, TreeTask successTask, TreeTask failureTask, Permissive[] permissives) {
        super(script, desc);
        this.permissives = permissives;
        this.successTask = successTask;
        this.failureTask = failureTask;
    }

    /** {@inheritDoc}
     * Not Executed in Branch Tasks*/
    @Override
    public void execute() {
    }

    /** {@inheritDoc} */
    @Override
    public TreeTask successTask(){
        return successTask;
    }

    /** {@inheritDoc} */
    @Override
    public boolean validate() {
        var val = !Arrays.stream(permissives).map(Permissive::isMet).collect(Collectors.toSet()).contains(false);
        getScript().println("[Branch] " + getDesc() + ": " + val);
        return val;
    }

    /** {@inheritDoc} */
    @Override
    public TreeTask failureTask(){
        return failureTask;
    }

    /** {@inheritDoc}
     * Will always be False for BranchTasks */
    @Override
    public final boolean isLeaf() {
        return false;
    }

    public Permissive[] getPermissives() {
        return permissives;
    }

    public void setPermissives(Permissive[] permissives) {
        this.permissives = permissives;
    }

}
