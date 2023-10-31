package net.botwithus.api.util.time;

public class Stopwatch {
    private long startMs;

    public Stopwatch() {
    }

    public static Stopwatch startNew() {
        Stopwatch s = new Stopwatch();
        s.start();
        return s;
    }

    public void start() {
        this.startMs = System.currentTimeMillis();
    }

    public long elapsed() {
        return System.currentTimeMillis() - this.startMs;
    }

    public boolean startIfElapsed(long ms) {
        if (this.elapsed() >= ms) {
            this.start();
            return true;
        } else {
            return false;
        }
    }
}