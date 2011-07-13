
package abstraction;

import abstraction.card.CardInterface;
import abstraction.hand.Hand;

// TODO avoir la pire meilleure main possible
// TODO avoir la meilleure main possible
// TODO avoir la meilleure meilleure main possible

public final class Probabilities {

    private Probabilities() {}

    public static PlayerHandProbabilities from(final Board board, final CardInterface[] twoCards) {

        final Deck deck = new Deck();

        deck.draw(twoCards[0]);
        deck.draw(twoCards[1]);

        final CardInterface[] boardCards = board.toArray();

        for (final CardInterface seenCard : boardCards)
            deck.draw(seenCard);

        final PlayerHandProbabilities phb = new PlayerHandProbabilities();

        final StringBuilder sb = new StringBuilder();
        for (final CardInterface card : twoCards)
            sb.append(card + "   ");
        sb.append("\n");
        sb.append(board.toString());

        System.out.println(sb.toString());

        final long t0 = System.currentTimeMillis();

        switch (board.getTimeLine()) {
            case RIVER:
                final Hand bh = ProbabilitiesWithABoardOfFiveCards.from(
                        phb,
                        deck,
                        twoCards[0],
                        twoCards[1],
                        boardCards[0],
                        boardCards[1],
                        boardCards[2],
                        boardCards[3],
                        boardCards[4]
                        );
                System.out.println(phb);
                System.out.println(bh);
                break;
            case TURN:
                ProbabilitiesWithABoardOfFourCards.from(
                        phb,
                        deck,
                        twoCards[0],
                        twoCards[1],
                        boardCards[0],
                        boardCards[1],
                        boardCards[2],
                        boardCards[3]
                        );
                System.out.println(phb);
                break;
            case FLOP:
                ProbabilitiesWithABoardOfThreeCards.from(
                        phb,
                        deck,
                        twoCards[0],
                        twoCards[1],
                        boardCards[0],
                        boardCards[1],
                        boardCards[2]
                        );
                System.out.println(phb);
                break;
            default:
                ProbabilitiesWithAnEmptyBoard2.from(
                        phb,
                        deck,
                        twoCards[0],
                        twoCards[1]
                        );
        }

        final long t1 = System.currentTimeMillis();
        System.out.println(t1 - t0 + " ms" + "\n\n");
        return phb;
    }

}
