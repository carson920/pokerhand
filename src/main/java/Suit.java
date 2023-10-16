public enum Suit {
    DIAMOND (0,"♦"),
    CLUB(1,"♣"),
    HEART(2,"♥"),
    SPADE(3,"♠");

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
