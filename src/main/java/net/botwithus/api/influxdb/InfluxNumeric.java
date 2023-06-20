package net.botwithus.api.influxdb;

public class InfluxNumeric  {
    private Number previousNum = 0f, currentNum = 0f;

    public InfluxNumeric(Number previousNum, Number currentNum) {
        this.previousNum = previousNum;
        this.currentNum = currentNum;
    }

    public Number getPreviousNum() {
        return previousNum;
    }

    public void setPreviousNum(Number previousNum) {
        this.previousNum = previousNum;
    }

    public Number getCurrentNum() {
        return currentNum;
    }

    public void setCurrentNum(Number currentNum) {
        this.currentNum = currentNum;
    }

    public Number getLatest() {
        var value = currentNum.floatValue() - previousNum.floatValue();
        previousNum = currentNum;
        return value;
    }
}
