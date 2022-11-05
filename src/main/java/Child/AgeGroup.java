package Child;

public enum AgeGroup {
    TODDLER(1),
    MIDDLECHILD(2),
    TEENAGER(3);
    static public AgeGroup getGroup(int age){
        return switch (age){
            case 2, 3, 4, 5 -> AgeGroup.TODDLER;
            case 6, 7,8,9,10 -> AgeGroup.MIDDLECHILD;
            case 11, 12, 13, 14, 15,16,17 ->AgeGroup.TEENAGER;
            default -> null;
        };
    }
    private final int ord;
    AgeGroup(int ord) {
        this.ord = ord;
    }

    public int getOrd() {
        return this.ord;
    }
    public static AgeGroup getAgeGroupByOrd(int ord){
        return switch (ord){
            case 1 -> TODDLER;
            case 2 -> MIDDLECHILD;
            case 3 -> TEENAGER;
            default -> null;
        };
    }
}
