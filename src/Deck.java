import java.util.ArrayList;

public class Deck {
    public ArrayList<Card> cards;
    public DiscardPile discardPile;
    static final int DECK_SIZE = 108;

    public Deck() {
        cards = new ArrayList<Card>();
        discardPile = new DiscardPile();

        for(Values value : Values.values()) {
            for (Colors color : Colors.values()) {
                switch (value) {
                    case Zero:
                        if (color != Colors.Wild) {
                            cards.add(new Card(value, color));
                        }
                        break;
                    case One:
                    case Two:
                    case Three:
                    case Four:
                    case Five:
                    case Six:
                    case Seven:
                    case Eight:
                    case Nine:
                    case Skip:
                    case DrawTwo:
                    case Reverse:
                        if (color != Colors.Wild) {
                            for (int i = 0; i < 2; i++) {
                                cards.add(new Card(value, color));
                            }
                        }
                        break;
                    case DrawFour:
                    case ChangeColor:
                        if (color == Colors.Wild) {
                            for (int i = 0; i < 4; i++) {
                                cards.add(new Card(value, color));
                            }
                        }
                        break;
                }
            }
        }
    }


    public boolean playCard(Card c) {
        for (int i = 0; i < this.cards.size(); i++) {
            if (this.cards.get(i).equals(c)){
                cards.remove(i);
                discardPile.cards.add(c);
                return true;
            }
        }
        return false;
    }

    public boolean drawCard(Card c) {
        for (int i = 0; i < this.cards.size(); i++) {
            if (this.cards.get(i).equals(c)){
                cards.remove(i);
                return true;
            }
        }
        return false;
    }

    public void addCard(Card c) {
        cards.add(c);
    }

    public boolean hasCard(Card c) {
        for (int i = 0; i < this.cards.size(); i++) {
            if (this.cards.get(i).equals(c)){
                return true;
            }
        }
        return false;
    }

    public void reshuffle() {
        while (discardPile.cards.size() != 1) {
            this.cards.add(discardPile.cards.get(0));
            discardPile.cards.remove(0);
        }
    }
}
