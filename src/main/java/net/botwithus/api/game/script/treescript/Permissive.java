package net.botwithus.api.game.script.treescript;

import com.google.common.flogger.FluentLogger;
import lombok.SneakyThrows;

import java.util.concurrent.Callable;

public class Permissive {
    private String name;
    private Callable<Boolean> predicate;
    private static final FluentLogger log = FluentLogger.forEnclosingClass();

    public Permissive(String name, Callable<Boolean> predicate) {
        this.name = name;
        this.predicate = predicate;
    }

    @SneakyThrows
    public boolean isMet() {
        try {
            var result = predicate.call();
            log.atInfo().log("[" + Thread.currentThread().getName() + "]: " + "[Permissive] " + name + ": " + result);
            return result;
        } catch (Exception e) {
            log.atSevere().withCause(e).log("Exception thrown in permissive predicate: " + name);
            return false;
        }
    }

    public String getName() {
        return name;
    }
}
