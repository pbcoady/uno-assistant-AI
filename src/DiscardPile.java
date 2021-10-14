import java.util.ArrayList;

public class DiscardPile {

    ArrayList<Card> cards;

    public DiscardPile() {
        cards = new ArrayList<Card>();
    }

    public Card getTopCard(){
        if (cards.size() == 0) {
            return null;
        }
        return cards.get(cards.size() - 1);
    }
}
