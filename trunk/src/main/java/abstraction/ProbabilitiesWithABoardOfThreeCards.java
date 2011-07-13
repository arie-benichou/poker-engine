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

import abstraction.card.CardInterface;

final class ProbabilitiesWithABoardOfThreeCards {

    private final static int PLAYER_CARD_1_INDEX = 0;
    private final static int PLAYER_CARD_2_INDEX = 1;
    private final static int FLOP_FIRST_CARD_INDEX = 2;
    private final static int FLOP_SECOND_CARD_INDEX = 3;
    private final static int FLOP_THIRD_CARD_INDEX = 4;

    private ProbabilitiesWithABoardOfThreeCards() {}

    private final static Combinations COMBINATIONS = new Combinations(52 - 2 - 3, 2);
    private final static int[][] COMBINATIONS_FOR_47_ELEMENTS_TAKEN_BY_2 = new int[COMBINATIONS.getNumberOfCombinations().intValue()][1];
    static {
        int i = -1;
        while (COMBINATIONS.hasNext())
            COMBINATIONS_FOR_47_ELEMENTS_TAKEN_BY_2[++i] = COMBINATIONS.next();
    }

    public static void from(final PlayerHandProbabilities phb, final Deck deck, final CardInterface... seenCards) {
        final CardInterface[] notSeenCards = deck.toArray();
        for (final int[] combination : COMBINATIONS_FOR_47_ELEMENTS_TAKEN_BY_2) {
            deck.draw(notSeenCards[combination[0]]);
            deck.draw(notSeenCards[combination[1]]);
            ProbabilitiesWithABoardOfFiveCards.from(
                    phb,
                    deck,
                    seenCards[PLAYER_CARD_1_INDEX],
                    seenCards[PLAYER_CARD_2_INDEX],
                    seenCards[FLOP_FIRST_CARD_INDEX],
                    seenCards[FLOP_SECOND_CARD_INDEX],
                    seenCards[FLOP_THIRD_CARD_INDEX],
                    notSeenCards[combination[0]],
                    notSeenCards[combination[1]]
                    );
            deck.putBack(notSeenCards[combination[0]]);
            deck.putBack(notSeenCards[combination[1]]);
        }
    }
}
