package Toy;

public enum ToySize {
    TINY(1),
    SMALL(2),
    MEDIUM(3),
    BIG(4);
    private int ord;
    ToySize(int ord) {
        this.ord = ord;
    }
    public int getOrd() {
        return this.ord;
    }
    public static ToySize getSizeByOrd(int ord){
        return switch (ord){
            case 1 -> TINY;
            case 2 -> SMALL;
            case 3 -> MEDIUM;
            case 4 -> BIG;
            default -> null;
        };
    }
}
