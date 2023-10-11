import java.util.*;
import java.util.stream.Collectors;

public class Poker {
    public static void main(String args[]) {
        Set<Card> deck = new HashSet<>();
        for (Suit suit: Suit.values()) {
            for (Value value: Value.values()) {
                Card card = new Card(value, suit);
                deck.add(card);
            }
        }
        List<Hand> hands = new ArrayList<>();

        for (int j=0; j<10; j++) {
            Card[] hand = new Card[5];

            for (int i = 0; i < 5; i++) {
                Card drawn = deck.stream().skip(new Random().nextInt(deck.size())).findFirst().orElse(null);
                deck.remove(drawn);
                hand[i] = drawn;
            }
            Arrays.sort(hand);
            hands.add(new Hand(hand, evaluateHand(hand)));

        }

        hands.sort(Comparator.naturalOrder());
        System.out.println(hands);
    }

    private static List<HandElementValue> evaluateHand(Card[] hand) {
        int[] valueCount = new int[13];
        for (int i=0; i<5; i++) {
            valueCount[hand[i].getValue().getStrength()]++;
        }
        List<HandElementValue> p = new ArrayList<>();

        // flush
        for (int i=0; i<4; i++) {
            if (hand[i].getSuit() != hand[i+1].getSuit()) {
                break;
            }
            if (i==3) {
                p.add(new HandElementValue(HandElement.FLUSH, -1));
            }
        }

        // straight
        int[][] straightPattern = new int[10][13];
        for (int i=0; i<9; i++) {
            Arrays.fill(straightPattern[i], i, i+5,1);
        }
        straightPattern[9] = new int[]{1,1,1,1,0,0,0,0,0,0,0,0,1};
        for (int i=0; i<10; i++) {
            if (Arrays.equals(valueCount, straightPattern[i])) {
                p.add(new HandElementValue(HandElement.STRAIGHT, -2));
            }
        }

        // quad, trip, pair, kicker
        for (int i=0; i< valueCount.length; i++) {
            switch (valueCount[i]) {
                case 0:
                    break;
                case 1:
                    p.add(new HandElementValue(HandElement.HIGH_CARD, i));
                    break;
                case 2:
                    p.add(new HandElementValue(HandElement.PAIR, i));
                    break;
                case 3:
                    p.add(new HandElementValue(HandElement.TRIP, i));
                    break;
                case 4:
                    p.add(new HandElementValue(HandElement.QUAD, i));
                    break;
            }
        }

        Map<HandElement, Long> count =
                p.stream().collect(Collectors.groupingBy(e -> e.getHandElement(), Collectors.counting()));

        System.out.println(count.entrySet());

        if (count.getOrDefault(HandElement.FLUSH, 0L) == 1L && count.getOrDefault(HandElement.STRAIGHT, 0L) == 1L) {
            p.add(new HandElementValue(HandElement.STRAIGHT_FLUSH, -1));
        }

        if (count.getOrDefault(HandElement.TRIP, 0L) == 1 && count.getOrDefault(HandElement.PAIR, 0L) == 1) {
            p.add(new HandElementValue(HandElement.FULL_HOUSE, -1));
        }

        if (count.getOrDefault(HandElement.PAIR, 0L) == 2L) {
            p.add(new HandElementValue(HandElement.TWO_PAIR, -1));
        }

        Collections.sort(p);

        System.out.println(p);
        return p;
    }

}
