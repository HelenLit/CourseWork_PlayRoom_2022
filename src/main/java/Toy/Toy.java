package Toy;

import Child.AgeGroup;

public abstract class Toy {
    private String name;
    private int price;
    private int id;
    private AgeGroup ageGroup;
    private ToySize toySize;

    protected Toy(int id,String name, int price, AgeGroup ageGroup,ToySize toySize) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.ageGroup = ageGroup;
        this.toySize = toySize;
    }

    public void setToySize(ToySize toySize) {
        this.toySize = toySize;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setAgeGroup(AgeGroup ageGroup) {
        this.ageGroup = ageGroup;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
    public int getId() {
        return id;
    }

    public AgeGroup getAgeGroup() {
        return ageGroup;
    }

    public ToySize getToySize() {
        return toySize;
    }
    static public Toy createToy(int id, String name, int price, AgeGroup ageGroup,ToySize toySize){
        return switch (ageGroup){
            case TODDLER -> new ToddlerToy(id,name, price,toySize);
            case MIDDLECHILD -> new MiddleChildToy(id,name, price,toySize);
            case TEENAGER -> new TeenagerToy(id,name, price,toySize);
            default -> null;
        };
    }
    static public Toy createToy(int id,String name, int price, int ageOrd,ToySize toySize){
        AgeGroup ageGr = AgeGroup.getAgeGroupByOrd(ageOrd);
        return Toy.createToy(id,name, price,ageGr,toySize);
    }

    @Override
    public String toString() {
        return "\nName: " + name +
                "\nprice: " + price+
                "\nID: " + id +
                "\nageGroup: " + ageGroup +
                "\ntoySize: " + toySize;
    }
}
