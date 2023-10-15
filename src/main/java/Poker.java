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

        if (args.length == 0 || "Stud".equalsIgnoreCase(args[0])) {
            System.out.println("Default to play stud");
            playStud(deck);
        } else if ("Texas".equalsIgnoreCase(args[0])) {
            playTexas(deck);
        } else if ("Omaha".equalsIgnoreCase(args[0])) {
            playOmaha(deck);
        } else {
            System.out.println("Please specify 'Stud', 'Texas' or 'Omaha' as parameter");
        }

        // Deal 10 players 5 cards each and rank the hands.
    }

    private static void playOmaha(Set<Card> deck) {
    }

    private static void playTexas(Set<Card> deck) {
    }

    private static void playStud(Set<Card> deck) {
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

    // the main logic for evaluating hand strength
    private static List<HandElementValue> evaluateHand(Card[] hand) {
        // count the no. of appearance each face value (2 to Ace) disregarding suit
        int[] valueCount = new int[13];
        for (int i=0; i<5; i++) {
            valueCount[hand[i].getValue().getStrength()]++;
        }
        List<HandElementValue> p = new ArrayList<>();

        // flush condition
        for (int i=0; i<4; i++) {
            if (hand[i].getSuit() != hand[i+1].getSuit()) {
                break;
            }
            if (i==3) {
                p.add(new HandElementValue(HandElement.FLUSH, -1));
            }
        }

        // straight condition
        int[][] straightPattern = new int[10][13];
        // straight pattern for A, 2, 3, 4, 5 to 9, 10, J, Q, K
        for (int i=0; i<9; i++) {
            Arrays.fill(straightPattern[i], i, i+5,1);
        }
        // special case for 10, J, Q, K, A
        straightPattern[9] = new int[]{1,1,1,1,0,0,0,0,0,0,0,0,1};

        // compare if the value count matches one of the straight pattern
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
                    // pair condition (need to check for possible two pairs)
                    p.add(new HandElementValue(HandElement.PAIR, i));
                    break;
                case 3:
                    // trip condition (need to check for possible full house)
                    p.add(new HandElementValue(HandElement.TRIP, i));
                    break;
                case 4:
                    // quad condition
                    p.add(new HandElementValue(HandElement.QUAD, i));
                    break;
            }
        }

        Map<HandElement, Long> count =
                p.stream().collect(Collectors.groupingBy(HandElementValue::getHandElement, Collectors.counting()));

        // straight flush if both straight and flush conditions are met
        if (count.getOrDefault(HandElement.FLUSH, 0L) == 1L && count.getOrDefault(HandElement.STRAIGHT, 0L) == 1L) {
            p.add(new HandElementValue(HandElement.STRAIGHT_FLUSH, -1));
        }

        // full house condition
        if (count.getOrDefault(HandElement.TRIP, 0L) == 1 && count.getOrDefault(HandElement.PAIR, 0L) == 1) {
            p.add(new HandElementValue(HandElement.FULL_HOUSE, -1));
        }

        // two pairs condition
        if (count.getOrDefault(HandElement.PAIR, 0L) == 2L) {
            p.add(new HandElementValue(HandElement.TWO_PAIR, -1));
        }

        Collections.sort(p);

        System.out.println("Sorted Hand Elements of a hand" + p);
        return p;
    }

}
