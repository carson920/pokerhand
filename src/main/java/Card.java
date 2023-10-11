public class Card implements Comparable<Card>{
    private Value value;
    private Suit suit;

    public Card(Value value, Suit suit) {
        this.value = value;
        this.suit = suit;
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }

    public Suit getSuit() {
        return suit;
    }

    public void setSuit(Suit suit) {
        this.suit = suit;
    }

    @Override
    public String toString() {
        return value.getName() +
                suit.getName();
    }

    @Override
    public int compareTo(Card o) {
        int result = Integer.compare(o.getValue().getStrength(), getValue().getStrength());
        if (result != 0) return result;
        return Integer.compare(o.getSuit().getIndex(), getSuit().getIndex());
    }
}
