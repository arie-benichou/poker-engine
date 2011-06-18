/*
 * Copyright 2011 Arie Benichou
 * 
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */

package abstraction;

import java.util.Arrays;

import abstraction.card.CardInterface;
import abstraction.card.CardRank;
import abstraction.card.CardType;
import abstraction.card.Cards;
import abstraction.hand.Hand;

// TODO à documenter
final class BestHandFinder {

    // TODO définir ces constantes dans une classe de plus haut niveau
    public final static int NUMBER_OF_CARDS_BY_PLAYER = 2;
    public final static int NUMBER_OF_CARDS_IN_FLOP = 5;

    private final static CardInterface[] WORST_CARDS = new CardInterface[5];
    static {
        WORST_CARDS[0] = Cards.get(CardType.HEARTS, CardRank.DEUCE);
        WORST_CARDS[1] = Cards.get(CardType.SPADES, CardRank.TREY);
        WORST_CARDS[2] = Cards.get(CardType.DIAMONDS, CardRank.FOUR);
        WORST_CARDS[3] = Cards.get(CardType.CLUBS, CardRank.FIVE);
        WORST_CARDS[4] = Cards.get(CardType.HEARTS, CardRank.SEVEN);
    }
    private final static Hand WORST_HAND = new Hand(WORST_CARDS);

    private final static Combinations COMBINATIONS = new Combinations(NUMBER_OF_CARDS_BY_PLAYER + NUMBER_OF_CARDS_IN_FLOP, Hand.NUMBER_OF_CARDS_IN_HAND);
    private final static int[][] NUMBER_OF_COMBINATIONS_FOR_7_ELEMENTS_TAKEN_BY_5 = new int[COMBINATIONS.getNumberOfCombinations().intValue()][1];
    static {
        int i = -1;
        while (COMBINATIONS.hasNext())
            NUMBER_OF_COMBINATIONS_FOR_7_ELEMENTS_TAKEN_BY_5[++i] = COMBINATIONS.next();
    }

    private BestHandFinder() {}

    private static Hand from(final CardInterface[] cards) {
        Hand bestHand = WORST_HAND; // il y a toujours une main meilleure que cette pire main théorique        
        for (final int[] combination : NUMBER_OF_COMBINATIONS_FOR_7_ELEMENTS_TAKEN_BY_5) {
            final Hand currentHand = new Hand(
                    cards[combination[0]],
                    cards[combination[1]],
                    cards[combination[2]],
                    cards[combination[3]],
                    cards[combination[4]]
                    );
            if (currentHand.compareTo(bestHand) == 1)
                bestHand = currentHand;
        }
        return bestHand;
    }

    public static Hand from(final CardInterface firstCard, final CardInterface secondCard, final CardInterface[] fiveCards) {
        final CardInterface[] cards = Arrays.copyOf(fiveCards, 7);
        cards[5] = firstCard;
        cards[6] = secondCard;
        return BestHandFinder.from(cards);
    }

    public static Hand from(final CardInterface[] twoCards, final CardInterface[] fiveCards) {
        final CardInterface[] cards = Arrays.copyOf(fiveCards, 7);
        cards[5] = twoCards[0];
        cards[6] = twoCards[1];
        return BestHandFinder.from(cards);
    }

    public static void main(final String[] args) {

        // TODO ! écrire tests unitaires (avant)

        for (final int[] combination : NUMBER_OF_COMBINATIONS_FOR_7_ELEMENTS_TAKEN_BY_5) {
            for (final int i : combination)
                System.out.print(i);
            System.out.println();
        }
    }

}