import java.util.ArrayList;

public class Hand {

    int numCards;
    ArrayList<Card> cardsCantPlayOn;

    public Hand(int startingNum) {
        numCards = startingNum;
        cardsCantPlayOn = new ArrayList<Card>();
    }

    public void draw(int numToDraw) {
        numCards += numToDraw;
    }

    public void discard() {
        numCards--;
    }

    public boolean hasUno() {
        return numCards == 1;
    }

    public boolean hasWon() {
        return numCards == 0;
    }

    public void addCannotPlayCard(Card c) {
        if(c != null)
            cardsCantPlayOn.add(c);
    }

    public void refreshCantPlayOn() {
        cardsCantPlayOn.clear();
    }
    
    public int handSize() {
    	return numCards;
    }

    /*
    public void printCantPlayCards() {
        if(cardsCantPlayOn.size() > 0) {
            Card card;
            int value;
            System.out.print("(Can't play on ");
            for(int i = 0; i < cardsCantPlayOn.size(); i++) {
                card = cardsCantPlayOn.get(i);
                value = card.getValue().ordinal();
                if(i == cardsCantPlayOn.size() - 1)
                    if(value < 10)
                        System.out.print(value + "-" + card.getColor() + ")\n");
                    else
                        System.out.print(card.getValue() + "-" + card.getColor() + ")\n");
                else {
                    if(value < 10)
                        System.out.print(value + "-" + card.getColor() + ", ");
                    else
                        System.out.print(card.getValue() + "-" + card.getColor() + ", ");
                }
            }
        }
    }
	*/
}
