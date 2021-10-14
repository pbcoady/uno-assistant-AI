import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import java.util.ArrayList;

public class Game {

    public static final int HAND_SIZE = 7;
    public static final int NUM_OPPONENTS = 4;

    Deck deck;
    ArrayList<Hand> players;
    PlayerHand userHand;
    int turnCounter;
    boolean gameFinished;
    boolean isReversed;
    int winningPlayer;

    public Game(int startingPlayer) {

        this.deck = new Deck();
        this.players = new ArrayList<Hand>();
        this.userHand = new PlayerHand();
        this.turnCounter = startingPlayer;
        this.gameFinished = false;


        for (int i = 0; i < NUM_OPPONENTS; i++) {
            players.add(new Hand(HAND_SIZE));
        }
    }

    public void advanceTurn() {
        if (!isReversed) {
            turnCounter++;
            turnCounter = turnCounter % NUM_OPPONENTS;
        } else {
            turnCounter--;
            if (turnCounter < 0)
                turnCounter = NUM_OPPONENTS - 1;
        }
    }

    public void opponentDrawCards(int playerNum, int cardsToDraw) {
        players.get(playerNum).draw(cardsToDraw);
        players.get(playerNum).refreshCantPlayOn();
        if (cardsToDraw > 1)
            advanceTurn();
    }

    public boolean userDrawCard(Card c) {
        boolean cardDrawn = deck.drawCard(c);
        if (cardDrawn) {
            userHand.addCard(c);
        }
        return cardDrawn;
    }

    public void opponentPass() {
        players.get(turnCounter).addCannotPlayCard(deck.discardPile.getTopCard());
        advanceTurn();
    }

    public void userPass() {
        advanceTurn();
    }

    public boolean opponentPlayCard(Card c) {
        boolean cardPlayed = deck.playCard(c);
        if (cardPlayed) {
            players.get(turnCounter).discard();
            if (c.getValue().equals(Values.Reverse)) {
                isReversed = !isReversed;
            }
            else if (c.getValue().equals(Values.Skip)) {
                advanceTurn();
            }
            if (players.get(turnCounter).hasUno()) {
                System.out.println("Player " + turnCounter + " has UNO!");
            }
            if (players.get(turnCounter).hasWon()) {
                System.out.println("Player " + turnCounter + " has won!");
                gameFinished = true;
                winningPlayer = turnCounter;
            }
            advanceTurn();
        }
        return cardPlayed;
    }

    public boolean userPlayCard(Card c) {
        boolean validCard = userHand.contains(c);
        if (validCard) {
            userHand.removeCard(c);
            deck.discardPile.cards.add(c);
            if (userHand.hasUno()) {
                System.out.println("You have UNO!");
            }
            if (userHand.hasWon()) {
                System.out.println("You have won!");
                gameFinished = true;
                winningPlayer = turnCounter;
            }
            if (c.getValue().equals(Values.Reverse)) {
                isReversed = !isReversed;
            } else if (c.getValue().equals(Values.Skip)) {
                advanceTurn();
            }
            advanceTurn();
        }
        return validCard;
    }

    public int hasWon() {
        if (gameFinished) {
            return winningPlayer;
        }
        return -1;
    }

    public boolean isUserTurn() {
        return turnCounter == 3;
    }

    public Card convertToCard(String c) {
        String[] attributes = c.split("-");
        if (attributes.length != 2)
            return new Card(null, null);

        String valueString;
        String colorString;
        if (attributes[0].equals("0") || attributes[0].equals("1") || attributes[0].equals("2") || attributes[0].equals("3") || attributes[0].equals("4") || attributes[0].equals("5") || attributes[0].equals("6") || attributes[0].equals("7") || attributes[0].equals("8") || attributes[0].equals("9") ||
                attributes[0].equals("skip") || attributes[0].equals("Skip") || attributes[0].equals("SKIP") || attributes[0].equals("drawtwo") || attributes[0].equals("DrawTwo") || attributes[0].equals("DWARTWO") || attributes[0].equals("reverse") || attributes[0].equals("Reverse") || attributes[0].equals("REVERSE") ||
                attributes[0].equals("changecolor") || attributes[0].equals("ChangeColor") || attributes[0].equals("CHANGECOLOR") || attributes[0].equals("drawfour") || attributes[0].equals("DrawFour") || attributes[0].equals("DRAWFOUR")) {
            valueString = attributes[0];
            colorString = attributes[1];
        } else {
            valueString = attributes[1];
            colorString = attributes[0];
        }

        Values value;
        switch (valueString) {
            case "0":
                value = Values.Zero;
                break;
            case "1":
                value = Values.One;
                break;
            case "2":
                value = Values.Two;
                break;
            case "3":
                value = Values.Three;
                break;
            case "4":
                value = Values.Four;
                break;
            case "5":
                value = Values.Five;
                break;
            case "6":
                value = Values.Six;
                break;
            case "7":
                value = Values.Seven;
                break;
            case "8":
                value = Values.Eight;
                break;
            case "9":
                value = Values.Nine;
                break;
            case "Skip":
            case "skip":
            case "SKIP":
                value = Values.Skip;
                break;
            case "DrawTwo":
            case "drawtwo":
            case "DRAWTWO":
                value = Values.DrawTwo;
                break;
            case "Reverse":
            case "reverse":
            case "REVERSE":
                value = Values.Reverse;
                break;
            case "ChangeColor":
            case "changecolor":
            case "CHANGECOLOR":
                value = Values.ChangeColor;
                break;
            case "DrawFour":
            case "drawfour":
            case "DRAWFOUR":
                value = Values.DrawFour;
                break;
            default:
                value = null;
        }
        Colors color;
        switch (colorString) {
            case "Red":
            case "red":
            case "RED":
                color = Colors.Red;
                break;
            case "Yellow":
            case "yellow":
            case "YELLOW":
                color = Colors.Yellow;
                break;
            case "Green":
            case "green":
            case "GREEN":
                color = Colors.Green;
                break;
            case "Blue":
            case "blue":
            case "BLUE":
                color = Colors.Blue;
                break;
            case "Wild":
            case "wild":
            case "WILD":
                color = Colors.Wild;
                break;
            default:
                color = null;
        }

        if ((value == Values.ChangeColor && color != Colors.Wild) || (value == Values.DrawFour && color != Colors.Wild) || (value != Values.ChangeColor && value != Values.DrawFour && color == Colors.Wild)) {
            value = null;
            color = null;
        }

        return new Card(value, color);
    }

    //use the bot AI to recommend a card
    //adapted from https://boardgames.stackexchange.com/questions/13162/what-specific-strategies-have-the-highest-winloss-ratio-in-uno)
    //currentColor: the current color (can't retrieve this from the top card, because it might be wild)
    public String recommendCard(Colors currentColor) {
        String result = "Suggested Play: ";
        Card topCard = new Card(deck.discardPile.getTopCard().getValue(), currentColor);

        int drawFourIndex = userHand.playableCardIndex(Values.DrawFour, topCard);
        int changeColorIndex = userHand.playableCardIndex(Values.ChangeColor, topCard);
        int drawTwoIndex = userHand.playableCardIndex(Values.DrawTwo, topCard);
        int reverseIndex = userHand.playableCardIndex(Values.Reverse, topCard);
        int skipIndex = userHand.playableCardIndex(Values.Skip, topCard);
        int highestNumIndex = userHand.playableCardIndex(userHand.highestOfColor(currentColor), topCard);
        //if the user doesn't have a number card of the top color, they might have a card that matches the top number
        if (highestNumIndex == -1) {
            highestNumIndex = userHand.playableCardIndex(topCard.getValue(), topCard);
        }

        Hand nextOpponent;
        Hand previousOpponent;
        Hand oppositeOpponent = players.get(1);
        if (isReversed) {
            nextOpponent = players.get(0);
            previousOpponent = players.get(2);
        } else {
            nextOpponent = players.get(2);
            previousOpponent = players.get(0);
        }


        int leadingPlayerHandSize = Math.min(userHand.handSize(),
                Math.min(nextOpponent.handSize(),
                        Math.min(previousOpponent.handSize(), oppositeOpponent.handSize())));
        //Going to lose; get rid of high points
        if (leadingPlayerHandSize < userHand.handSize() / 2
                && leadingPlayerHandSize < userHand.handSize() - 3) {
            if (drawFourIndex != -1) return result + "DrawFour, call " + getColorToCall();
            if (changeColorIndex != -1) return result + "ChangeColor, call " + getColorToCall();
            if (drawTwoIndex != -1) return result + userHand.getCard(drawTwoIndex).toString();
            if (previousOpponent.handSize() > oppositeOpponent.handSize()) {
                if (reverseIndex != -1) return result + userHand.getCard(reverseIndex).toString();
                if (skipIndex != -1) return result + userHand.getCard(skipIndex).toString();
                if (highestNumIndex != -1) return result + userHand.getCard(highestNumIndex).toString();
            } else {
                if (skipIndex != -1) return result + userHand.getCard(skipIndex).toString();
                if (reverseIndex != -1) return result + userHand.getCard(reverseIndex).toString();
                if (highestNumIndex != -1) return result + userHand.getCard(highestNumIndex).toString();
            }
        }

        //Try to save one wild card for the end, otherwise get rid of extras
        else if (userHand.numCardsOfColor(Colors.Wild) > 1) {
            if (drawFourIndex != -1) return result + "DrawFour, call " + getColorToCall();
            if (changeColorIndex != -1) return result + "ChangeColor, call " + getColorToCall();
        }

        //Use a draw 2 if necessary, but you should save one
        else if (userHand.numCardsOfValue(Values.DrawTwo) > 1 || nextOpponent.handSize() < 3) {
            if (drawTwoIndex != -1) return result + userHand.getCard(drawTwoIndex).toString();
        }

        //Play normally
        if (skipIndex != -1) return result + userHand.getCard(skipIndex).toString();
        if (reverseIndex != -1) return result + userHand.getCard(reverseIndex).toString();
        if (drawTwoIndex != -1) return result + userHand.getCard(drawTwoIndex).toString();
        if (highestNumIndex != -1) return result + userHand.getCard(highestNumIndex).toString();
        if (drawFourIndex != -1) return result + "DrawFour, call " + getColorToCall();

        //if the game isn't close to being lost, save your wilds
        if (leadingPlayerHandSize > 2 && leadingPlayerHandSize > userHand.handSize() - 1) {
            return result + "Pass";
        }
        if (changeColorIndex != -1) return result + "ChangeColor, call " + getColorToCall();

        //there are no cards the user can play
        return result + "Pass";
    }

    //return the most optimal color to call
    public String getColorToCall() {
        int redCounter = 0, blueCounter = 0,
                yellowCounter = 0, greenCounter = 0;
        boolean hasRed = false, hasBlue = false,
                hasYellow = false, hasGreen = false;
        ArrayList<Card> myCards = userHand.getCards();
        for (int i = 0; i < myCards.size(); i++) {
            switch (myCards.get(i).getColor()) {
                case Red:
                    redCounter++;
                    hasRed = true;
                    break;
                case Blue:
                    blueCounter++;
                    hasBlue = true;
                    break;
                case Yellow:
                    yellowCounter++;
                    hasYellow = true;
                    break;
                case Green:
                    greenCounter++;
                    hasGreen = true;
                    break;
                default:
            }
        }
        if (hasRed || hasBlue || hasYellow || hasGreen) {

            //Find the most seen cards in the deck
            ArrayList<Card> cardsSeen = deck.discardPile.cards;
            for (int i = 0; i < cardsSeen.size(); i++) {
                switch (cardsSeen.get(i).getColor()) {
                    case Red:
                        if (hasRed) redCounter++;
                        break;
                    case Blue:
                        if (hasBlue) blueCounter++;
                        break;
                    case Yellow:
                        if (hasYellow) yellowCounter++;
                        break;
                    case Green:
                        if (hasGreen) greenCounter++;
                        break;
                    default:
                }
            }
            //Name the color most often seen so far
            return mostCommonColor(redCounter, blueCounter, yellowCounter, greenCounter);
        } else {
            //The user's hand is all black cards
            return "Red";
        }
    }

    //Finds the maximum of four numbers
    //AKA the most common color when counting all four colors in a deck
    public String mostCommonColor(int numRed, int numBlue, int numYellow, int numGreen) {
        int max = numRed;
        String maxString = "Red";

        if (numBlue > max) {
            max = numBlue;
            maxString = "Blue";
        }

        if (numYellow > max) {
            max = numBlue;
            maxString = "Yellow";
        }

        if (numGreen > max) {
            maxString = "Green";
        }

        return maxString;
    }
}
