package net.botwithus.api.game.script.treescript.permissive;

public class Result {
    private boolean result;
    private long resultTime;

    public Result(boolean result) {
        this.result = result;
        resultTime = System.currentTimeMillis();
    }

    public boolean isValidResult() {
        return System.currentTimeMillis() - resultTime <= 5000;
    }

    public ResultType getResultType() {
        return !isValidResult() ? ResultType.INVALID : ResultType.getResult(result);
    }
}

