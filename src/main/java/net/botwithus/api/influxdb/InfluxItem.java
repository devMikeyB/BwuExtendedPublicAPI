package net.botwithus.api.influxdb;

public class InfluxItem {
    private String itemName = "";
    private InfluxNumeric itemCount;

    public InfluxItem(String itemName, int startingCount) {
        this.itemName = itemName;
        itemCount = new InfluxNumeric(startingCount, startingCount);
    }

    public void addCount(float count) {
        itemCount.setCurrentNum(itemCount.getCurrentNum().floatValue() + count);
    }

    public Number deriveInfluxCount() {
        return itemCount.getLatest();
    }
}
