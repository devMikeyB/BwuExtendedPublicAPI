package net.botwithus.api.game.script.treescript;

import com.google.common.flogger.FluentLogger;
import net.botwithus.api.game.script.treescript.permissive.Permissive;
import net.botwithus.api.game.script.treescript.permissive.Result;
import net.botwithus.rs3.script.Script;

/**
 * Represents a branch task in a tree. This task is not a leaf node and
 * must provide success and failure tasks based on validation.
 */
public class BranchTask extends TreeTask {
    private static final FluentLogger log = FluentLogger.forEnclosingClass();
    private Permissive[][] permissives = new Permissive[][]{new Permissive[0]};
    private TreeTask successTask, failureTask;

    public BranchTask(Script script, String desc) {
        super(script, desc);
    }

    public BranchTask(Script script, String desc, TreeTask successTask, TreeTask failureTask, Permissive[]... permissives) {
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
        var groupIsValid = false;
        Permissive curPerm = null;

        try {
            for (var group : permissives) {
                groupIsValid = true; // Assume the group is valid initially
                for (var perm : group) {
                    curPerm = perm;
                    if (!perm.isMet()) {
                        groupIsValid = false; // Set to false if any permissive in the group is not met
                        break; // No need to check further in this group
                    }
                }
                if (groupIsValid) {
                    break; // A valid group is found, no need to check further groups
                }
            }
        } catch (Exception e) {
            groupIsValid = false; // Ensure validation fails in case of an exception
            log.atSevere().withCause(e).log("Could not process permissive: " + (curPerm != null ? curPerm.getName() : "null"));
        }

        latestValidate = new Result(groupIsValid);
        return groupIsValid;
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

    public Permissive[][] getPermissives() {
        return permissives;
    }

    public void setPermissives(Permissive[]... permissives) {
        this.permissives = permissives;
    }

}
