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
import abstraction.hand.Hand;

final class ProbabilitiesWithABoardOfFiveCards {

    private final static int LOWER = -1;
    private final static int GREATER = 1;

    //private final static int PLAYER_CARD_1_INDEX = 0;
    //private final static int PLAYER_CARD_2_INDEX = 1;
    private final static int FLOP_FIRST_CARD_INDEX = 2;
    private final static int FLOP_SECOND_CARD_INDEX = 3;
    private final static int FLOP_THIRD_CARD_INDEX = 4;
    private final static int TURN_CARD_INDEX = 5;
    private final static int RIVER_CARD_INDEX = 6;

    private final static int NUMBER_OF_CARDS_NOT_SEEN = Deck.NUMBER_OF_CARDS
            - (BestHandFinder.NUMBER_OF_CARDS_BY_PLAYER + BestHandFinder.NUMBER_OF_CARDS_IN_FLOP);

    private final static Combinations COMBINATIONS = new Combinations(NUMBER_OF_CARDS_NOT_SEEN, BestHandFinder.NUMBER_OF_CARDS_BY_PLAYER);
    private final static int[][] COMBINATIONS_FOR_45_ELEMENTS_TAKEN_BY_2 = new int[COMBINATIONS.getNumberOfCombinations().intValue()][1];
    static {
        int i = -1;
        while (COMBINATIONS.hasNext())
            COMBINATIONS_FOR_45_ELEMENTS_TAKEN_BY_2[++i] = COMBINATIONS.next();
    }

    private ProbabilitiesWithABoardOfFiveCards() {}

    public static Hand from(final PlayerHandProbabilities phb, final Deck deck, final CardInterface... seenCards) {

        final Hand playerBestHand = BestHandFinder.from(seenCards);
        final CardInterface[] notSeenCards = deck.toArray();

        for (final int[] combination : COMBINATIONS_FOR_45_ELEMENTS_TAKEN_BY_2)
            switch (playerBestHand.compareTo(
                    BestHandFinder.from(
                            notSeenCards[combination[0]],
                            notSeenCards[combination[1]],
                            seenCards[FLOP_FIRST_CARD_INDEX],
                            seenCards[FLOP_SECOND_CARD_INDEX],
                            seenCards[FLOP_THIRD_CARD_INDEX],
                            seenCards[TURN_CARD_INDEX],
                            seenCards[RIVER_CARD_INDEX]
                            ))) {
                case LOWER:
                    phb.incrementDefeats();
                    break;
                case GREATER:
                    phb.incrementVictories();
                    break;
                default:
                    phb.incrementDraws();
            }

        return playerBestHand;
    }

}
