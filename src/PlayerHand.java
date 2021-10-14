import java.util.ArrayList;

public class PlayerHand {

	private ArrayList<Card> cards;

	public PlayerHand() {
		cards = new ArrayList<Card>();
	}

	public void addCard(Card c) {
		cards.add(c);
	}

	public boolean removeCard(Card c) {
		for (int i = 0; i < cards.size(); i++) {
			if (cards.get(i).equals(c)) {
				cards.remove(i);
				return true;
			}
		}
		return false;
	}

	public boolean hasUno() {
		return cards.size() == 1;
	}

	public boolean hasWon() {
		return cards.size() == 0;
	}

	public boolean contains(Card c) {
		for (int i = 0; i < cards.size(); i++) {
			if (cards.get(i).equals(c)) {
				return true;
			}
		}
		return false;
	}

	public ArrayList<Card> getCards() {
		return cards;
	}

	public int handSize() {
		return cards.size();
	}

	public Card getCard(int index) {
		return cards.get(index);
	}

	//find the index of a playable card with a given value in the user's hand
	public int playableCardIndex(Values cardValue, Card topCard) {
		if (cardValue.equals(Values.None)) {
			return -1;
		}
		//search for cards of the given value with the same color as the top card
		for (int i = 0; i < cards.size(); i++) {
			if (cards.get(i).getValue().equals(cardValue)) {
				//if the card you're looking for is a wild, it can be played on any color
				if (cardValue.equals(Values.DrawFour) || cardValue.equals(Values.ChangeColor)) return i;
				if (cards.get(i).getColor().equals(topCard.getColor())) return i;
			}
		}
		//search for cards of the given value with the same value as the top card
		for (int i = 0; i < cards.size(); i++) {
			if (cards.get(i).getValue().equals(cardValue)) {
				if (cards.get(i).getValue().equals(topCard.getValue())) return i;
			}
		}
		//otherwise return -1
		return -1;
	}

	public int numCardsOfColor(Colors color) {
		int counter = 0;
		for (Card c : cards) {
			if (c.getColor().equals(color)) return counter++;
		}
		return counter;
	}

	public int numCardsOfValue(Values value) {
		int counter = 0;
		for (Card c : cards) {
			if (c.getValue().equals(value)) return counter++;
		}
		return counter;
	}

	public Values highestOfColor(Colors color) {
		for (Card c : cards) {
			if (c.equals(new Card(Values.Nine, color))) return Values.Nine;
		}
		for (Card c : cards) {
			if (c.equals(new Card(Values.Eight, color))) return Values.Eight;
		}
		for (Card c : cards) {
			if (c.equals(new Card(Values.Seven, color))) return Values.Seven;
		}
		for (Card c : cards) {
			if (c.equals(new Card(Values.Six, color))) return Values.Six;
		}
		for (Card c : cards) {
			if (c.equals(new Card(Values.Five, color))) return Values.Five;
		}
		for (Card c : cards) {
			if (c.equals(new Card(Values.Four, color))) return Values.Four;
		}
		for (Card c : cards) {
			if (c.equals(new Card(Values.Three, color))) return Values.Three;
		}
		for (Card c : cards) {
			if (c.equals(new Card(Values.Two, color))) return Values.Two;
		}
		for (Card c : cards) {
			if (c.equals(new Card(Values.One, color))) return Values.One;
		}
		for (Card c : cards) {
			if (c.equals(new Card(Values.Zero, color))) return Values.Zero;
		}
		return Values.None;
	}

		public void printCards() {
			for (int i = 0; i < cards.size(); i++) {
				int value = cards.get(i).getValue().ordinal();
				if (value < 10) {
					if (i == cards.size() - 1)
						System.out.print(value + "-" + cards.get(i).getColor() + "\n");
					else
						System.out.print(value + "-" + cards.get(i).getColor() + "\t");
				} else {
					if (i == cards.size() - 1)
						System.out.print(cards.get(i).getValue() + "-" + cards.get(i).getColor() + "\n");
					else
						System.out.print(cards.get(i).getValue() + "-" + cards.get(i).getColor() + "\t");
				}
			}
		}
	}
