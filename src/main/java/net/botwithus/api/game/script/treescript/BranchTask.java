package net.botwithus.api.game.script.treescript;

import com.google.common.flogger.FluentLogger;
import net.botwithus.api.game.script.treescript.permissive.Permissive;
import net.botwithus.rs3.script.Script;

/**
 * Represents a branch task in a tree. This task is not a leaf node and
 * must provide success and failure tasks based on validation.
 */
public class BranchTask extends TreeTask {
    private static final FluentLogger log = FluentLogger.forEnclosingClass();
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
        var val = true;
        Permissive curPerm = null;
        try {
            for (var perm : permissives) {
                curPerm = perm;
                if (!perm.isMet()) {
                    val = false;
                    break;
                }
            }
        } catch (Exception e) {
            log.atSevere().withCause(e).log("Could not process permissive: " + (curPerm != null ? curPerm.getName() : "null"));
        }
        latestValidate = val;
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
