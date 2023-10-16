import java.util.*;
import java.util.stream.Collectors;

public class Util {

    public static <T> List<List<T>> generateCombinations(List<T> elements, int k) {
        List<List<T>> result = new ArrayList<>();
        generateCombinationsHelper(elements, k, 0, new ArrayList<T>(), result);
        return result;
    }

    private static <T> void generateCombinationsHelper(List<T> elements, int k, int start, List<T> current, List<List<T>> result) {
        if (k == 0) {
            result.add(new ArrayList<>(current));
            return;
        }

        for (int i = start; i < elements.size(); i++) {
            current.add(elements.get(i));
            generateCombinationsHelper(elements, k - 1, i + 1, current, result);
            current.remove(current.size() - 1);
        }
    }

    public static <T> List<T> combineLists(List<T> list1, List<T> list2) {
        List<T> combinedList = new ArrayList<>(list1);
        combinedList.addAll(list2);
        return combinedList;
    }

    public static List<HandElementValue> evaluateHand(List<Card> hand) {
        return evaluateHand(hand.toArray(new Card[0]));
    }
    public static List<HandElementValue> evaluateHand(Card[] hand) {
        // count the no. of appearance each face value (2 to Ace) disregarding suit
        int[] valueCount = new int[13];
        for (int i = 0; i < 5; i++) {
            valueCount[hand[i].getValue().getStrength()]++;
        }
        List<HandElementValue> p = new ArrayList<>();

        // flush condition
        for (int i = 0; i < 4; i++) {
            if (hand[i].getSuit() != hand[i + 1].getSuit()) {
                break;
            }
            if (i == 3) {
                p.add(new HandElementValue(HandElement.FLUSH, -1));
            }
        }

        // straight condition
        int[][] straightPattern = new int[9][13];
        // straight pattern for 2, 3, 4, 5, 6 to 10, J, Q, K, A
        for (int i = 0; i < 9; i++) {
            Arrays.fill(straightPattern[i], i, i + 5, 1);
        }

        // compare if the value count matches one of the straight pattern
        for (int i = 0; i < 9; i++) {
            if (Arrays.equals(valueCount, straightPattern[i])) {
                p.add(new HandElementValue(HandElement.STRAIGHT, 2));
            }
        }

        // special case for A, 2, 3, 4, 5
        int[] straightA2345 = new int[]{1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1};

        if (Arrays.equals(valueCount, straightA2345)) {
            p.add(new HandElementValue(HandElement.STRAIGHT, 1));
        }

        // quad, trip, pair, kicker
        for (int i = 0; i < valueCount.length; i++) {
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

        //System.out.println("Sorted Hand Elements of a hand" + p);
        return p;
    }


}
