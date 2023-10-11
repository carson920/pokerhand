public class HandElementValue implements Comparable<HandElementValue>{

    private HandElement handElement;
    private int strength;

    public HandElementValue(HandElement handElement, int strength) {
        this.handElement = handElement;
        this.strength = strength;
    }

    public HandElement getHandElement() {
        return handElement;
    }

    public void setHandElement(HandElement handElement) {
        this.handElement = handElement;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    @Override
    public String toString() {
        return
                handElement + " " + strength;
    }

    @Override
    public int compareTo(HandElementValue o) {
        int c = Integer.compare(o.getHandElement().rank, getHandElement().rank);
        if (c !=0) return c;
        return Integer.compare(o.getStrength(), getStrength());
    }
}
