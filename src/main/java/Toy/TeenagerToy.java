package Toy;

import Child.AgeGroup;

public class TeenagerToy extends Toy{
    public TeenagerToy(int id,String name, int price,ToySize toySize) {
        super(id,name, price, AgeGroup.TEENAGER, toySize);
    }
}
