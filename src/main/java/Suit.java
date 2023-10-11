public enum Suit {
    DIAMOND (0,"\u2666"),
    CLUB(1,"\u2663"),
    HEART(2,"\u2665"),
    SPADE(3,"\u2660");

    private final int index;
    private final String name;

    public String getName() {
        return name;
    }

    public int getIndex() {
        return index;
    }

    Suit(int index, String name) {
        this.index = index;
        this.name = name;
    }
}
