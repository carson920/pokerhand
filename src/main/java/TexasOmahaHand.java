import java.util.Arrays;
import java.util.List;

public class TexasOmahaHand extends Hand {

    Card[] holeCards;

    public TexasOmahaHand(Card[] holeCards, Card[] usedCards, List<HandElementValue> handElementValues) {
        super(usedCards, handElementValues);
        this.holeCards = holeCards;
    }

    public Card[] getHoleCards() {
        return holeCards;
    }

    public void setHoleCards(Card[] holeCards) {
        this.holeCards = holeCards;
    }

    @Override
    public String toString() {
        return "Hole cards: " + Arrays.toString(holeCards) + " " +
                "Hand: " + Arrays.toString(super.usedCards) + " " +
                handElementValues.get(0).getHandElement() + "\r\n";
    }
}
