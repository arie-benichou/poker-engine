
package abstraction;

import java.util.Arrays;

import abstraction.card.CardInterface;

public class Board {

    private final static int NUMBER_OF_CARDS_AT_PREFLOP = 0;
    private final static int NUMBER_OF_CARDS_AT_FLOP = NUMBER_OF_CARDS_AT_PREFLOP + 3;
    private final static int NUMBER_OF_CARDS_AT_TURN = NUMBER_OF_CARDS_AT_FLOP + 1;
    private final static int NUMBER_OF_CARDS_AT_RIVER = NUMBER_OF_CARDS_AT_TURN + 1;

    private int numberOfCards = NUMBER_OF_CARDS_AT_PREFLOP;
    private final CardInterface[] data = { null, null, null, null, null };

    private BoardTimeLine getTimeLine(final int numberOfCards) {
        switch (numberOfCards) {
            case NUMBER_OF_CARDS_AT_RIVER:
                return BoardTimeLine.RIVER;
            case NUMBER_OF_CARDS_AT_TURN:
                return BoardTimeLine.TURN;
            case NUMBER_OF_CARDS_AT_FLOP:
                return BoardTimeLine.FLOP;
            case NUMBER_OF_CARDS_AT_PREFLOP:
                return BoardTimeLine.PREFLOP;
            default:
                return BoardTimeLine.UNKNOWN;
        }
    }

    public BoardTimeLine getTimeLine() {
        return this.getTimeLine(this.numberOfCards);
    }

    public BoardTimeLine add(final CardInterface... cards) {
        final BoardTimeLine newBoardTimeLine = this.getTimeLine(this.numberOfCards + cards.length);
        if (newBoardTimeLine.equals(BoardTimeLine.UNKNOWN))
            throw new RuntimeException("A board must contain " + NUMBER_OF_CARDS_AT_PREFLOP + ", " + NUMBER_OF_CARDS_AT_FLOP + ", "
                    + NUMBER_OF_CARDS_AT_TURN + " or " + NUMBER_OF_CARDS_AT_RIVER + " cards"); // TODO lancer une BoardException
        for (final CardInterface card : cards)
            this.data[this.numberOfCards++] = card;
        return newBoardTimeLine;
    }

    public CardInterface[] toArray() {
        return Arrays.copyOf(this.data, this.numberOfCards);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(this.getTimeLine().toString());
        sb.append(":");
        for (final CardInterface card : this.toArray()) {
            sb.append("   ");
            sb.append(card);
        }
        sb.append("\n");
        return sb.toString();
    }

}
