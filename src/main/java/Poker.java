import java.util.*;
import java.util.stream.Collectors;

public class Poker {
    public static void main(String[] args) {
        Set<Card> deck = new HashSet<>();
        for (Suit suit: Suit.values()) {
            for (Value value: Value.values()) {
                Card card = new Card(value, suit);
                deck.add(card);
            }
        }

        if (args.length == 0 || "Stud".equalsIgnoreCase(args[0])) {
            System.out.println("Play stud");
            playStud(deck);
        } else if ("Texas".equalsIgnoreCase(args[0])) {
            System.out.println("Play Texas Hold'em");
            playTexas(deck);
        } else if ("Omaha".equalsIgnoreCase(args[0])) {
            System.out.println("Play Texas Omaha");
            playOmaha(deck);
        } else {
            System.out.println("Please specify 'Stud', 'Texas' or 'Omaha' as parameter");
        }

        // Deal 10 players 5 cards each and rank the hands.
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
            hands.add(new Hand(hand, Util.evaluateHand(hand)));

        }
        hands.sort(Comparator.naturalOrder());
        System.out.println(hands);
    }

    private static void playTexas(Set<Card> deck) {
        List<Card> communityCards = new ArrayList<>();
        for (int i=0; i<5; i++) {
            Card drawn = deck.stream().skip(new Random().nextInt(deck.size())).findFirst().orElse(null);
            deck.remove(drawn);
            communityCards.add(drawn);
        }
        communityCards.sort(Comparator.naturalOrder());

        List<TexasOmahaHand> hands = new ArrayList<>();
        for (int j=0; j<10; j++) {
            List<TexasOmahaHand> evaluatedCombinations = new ArrayList<>();
            List<Card> holeCards = new ArrayList<>();
            for (int i = 0; i < 2; i++) {
                Card drawn = deck.stream().skip(new Random().nextInt(deck.size())).findFirst().orElse(null);
                deck.remove(drawn);
                holeCards.add(drawn);
                holeCards.sort(Comparator.naturalOrder());
            }
            List<Card> combinedCards = Util.combineLists(holeCards, communityCards);
            List<List<Card>> possibleFiveCards = Util.generateCombinations(combinedCards, 5);
            for (List<Card> p : possibleFiveCards) {
                p.sort(Comparator.naturalOrder());
                evaluatedCombinations.add(new TexasOmahaHand(holeCards.toArray(new Card[0]),
                        p.toArray(new Card[0]),
                        Util.evaluateHand(p)));
            }
            //evaluatedCombinations.sort(Comparator.naturalOrder());
            TexasOmahaHand individualBestHand = Collections.min(evaluatedCombinations);
            System.out.println("Hole Cards: " + holeCards + " Largest combination: " + individualBestHand);
            hands.add(individualBestHand);
        }
        System.out.println("Community Cards: " + communityCards);
        hands.sort(Comparator.naturalOrder());
        System.out.println(hands);
    }

    private static void playOmaha(Set<Card> deck) {
        List<Card> communityCards = new ArrayList<>();
        for (int i=0; i<5; i++) {
            Card drawn = deck.stream().skip(new Random().nextInt(deck.size())).findFirst().orElse(null);
            deck.remove(drawn);
            communityCards.add(drawn);
        }
        communityCards.sort(Comparator.naturalOrder());

        List<TexasOmahaHand> hands = new ArrayList<>();
        for (int j=0; j<10; j++) {
            List<TexasOmahaHand> evaluatedCombinations = new ArrayList<>();
            List<Card> holeCards = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                Card drawn = deck.stream().skip(new Random().nextInt(deck.size())).findFirst().orElse(null);
                deck.remove(drawn);
                holeCards.add(drawn);
                holeCards.sort(Comparator.naturalOrder());
            }
            List<List<Card>> possibleTwoCards = Util.generateCombinations(holeCards, 2);
            List<List<Card>> possibleThreeCards = Util.generateCombinations(communityCards, 3);
            List<List<Card>> possibleFiveCards = new ArrayList<>();

            for (List<Card> two : possibleTwoCards) {
                for (List<Card> three : possibleThreeCards) {
                    List<Card> combinedCards = Util.combineLists(two, three);
                    possibleFiveCards.add(combinedCards);
                }
            }
            for (List<Card> p : possibleFiveCards) {
                p.sort(Comparator.naturalOrder());
                evaluatedCombinations.add(new TexasOmahaHand(holeCards.toArray(new Card[0]),
                        p.toArray(new Card[0]),
                        Util.evaluateHand(p)));
            }
            //evaluatedCombinations.sort(Comparator.naturalOrder());
            TexasOmahaHand individualBestHand = Collections.min(evaluatedCombinations);
            System.out.println("Hole Cards: " + holeCards + " Largest combination: " + individualBestHand);
            hands.add(individualBestHand);
        }
        System.out.println("Community Cards: " + communityCards);
        hands.sort(Comparator.naturalOrder());
        System.out.println(hands);
    }

}
