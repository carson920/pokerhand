import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Hand implements Comparable<Hand>{

    private Card[] cards;
    private List<HandElementValue> handElementValues;

    public Hand(Card[] cards, List<HandElementValue> handElementValues) {
        this.cards = cards;
        this.handElementValues = handElementValues;
    }

    public Card[] getCards() {
        return cards;
    }

    public void setCards(Card[] cards) {
        this.cards = cards;
    }

    public List<HandElementValue> getHandElementValues() {
        return handElementValues;
    }

    public void setHandElementValues(List<HandElementValue> handElementValues) {
        this.handElementValues = handElementValues;
    }

    @Override
    public int compareTo(Hand o) {
        List<HandElementValue> c1 = getHandElementValues();
        List<HandElementValue> c2 = o.getHandElementValues();
        for (int i=0; i< Math.min(c1.size(),c2.size()); i++) {
            if (c1.get(i).compareTo(c2.get(i)) != 0) return  c1.get(i).compareTo(c2.get(i));
        };
        return 0;
    }

    @Override
    public String toString() {
        return Arrays.toString(cards) + " " + handElementValues.get(0).getHandElement() + "\r\n";
    }
}
