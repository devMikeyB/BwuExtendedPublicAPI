package net.botwithus.api.game.script.treescript.interfaces;


import net.botwithus.api.game.script.treescript.TreeTask;

public interface ITreeTask {
    void execute();

    TreeTask successTask();

    boolean validate();

    TreeTask failureTask();

    boolean isLeaf();
}
