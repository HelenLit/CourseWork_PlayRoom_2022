package Toy;

public class ToyEntityInfo {
    private int amount;
    private int totalPrice;

    public ToyEntityInfo addToAmount() {
        this.amount++;
        return this;
    }

    public ToyEntityInfo addTotalPrice(int price) {
        this.totalPrice += price;
        return this;
    }

    public int getAmount() {
        return amount;
    }

    public int getTotalPrice() {
        return totalPrice;
    }
}
