package Toy;

import Child.AgeGroup;

public class MiddleChildToy extends Toy{
    public MiddleChildToy(int id,String name, int price,ToySize toySize) {
        super(id,name, price, AgeGroup.MIDDLECHILD, toySize);
    }
}
