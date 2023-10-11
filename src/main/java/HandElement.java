public enum HandElement {
    STRAIGHT_FLUSH (10),
    QUAD (9),
    FULL_HOUSE(8),
    FLUSH(7),
    STRAIGHT(6),
    TRIP(5),
    TWO_PAIR(4),
    PAIR(3),
    HIGH_CARD(2)
    ;

    public final int rank;

    HandElement(int rank) {
        this.rank = rank;
    }
}
