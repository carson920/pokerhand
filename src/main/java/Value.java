public enum Value {
    ACE(12, "A"),
    TWO(0, "2"),
    THREE(1,"3"),
    FOUR(2,"4"),
    FIVE(3,"5"),
    SIX(4,"6"),
    SEVEN(5,"7"),
    EIGHT(6,"8"),
    NINE(7,"9"),
    TEN(8, "10"),
    JACK(9,"J"),
    QUEEN(10,"Q"),
    KING(11,"K");

    private final int strength;
    private final String name;

    Value(int strength, String name) {
        this.strength = strength;
        this.name = name;
    }

    public int getStrength() {
        return strength;
    }

    public String getName() {
        return name;
    }
}
