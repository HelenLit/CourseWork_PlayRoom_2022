package Toy;

import Child.AgeGroup;

public class ToddlerToy extends Toy{
    public ToddlerToy(int id,String name, int price,ToySize toySize) {
        super(id,name, price, AgeGroup.TODDLER,toySize);
    }
}
