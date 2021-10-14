public class Card {
    private Values value;
    private Colors color;

    public Card(Values value, Colors color) {
        this.value = value;
        this.color = color;
    }

    public Values getValue() {
        return this.value;
    }

    public Colors getColor() {
        return this.color;
    }

    public boolean equals(Card other) {
        return (other.color.equals(this.color)
                && other.value.equals(this.value));
    }

    public String toString() {
        return this.color.toString() + " " + this.value.toString();
    }
}
