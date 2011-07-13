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

final class ProbabilitiesWithABoardOfFourCards {

    private final static int PLAYER_CARD_1_INDEX = 0;
    private final static int PLAYER_CARD_2_INDEX = 1;
    private final static int FLOP_FIRST_CARD_INDEX = 2;
    private final static int FLOP_SECOND_CARD_INDEX = 3;
    private final static int FLOP_THIRD_CARD_INDEX = 4;
    private final static int TURN_CARD_INDEX = 5;

    private ProbabilitiesWithABoardOfFourCards() {}

    public static void from(final PlayerHandProbabilities phb, final Deck deck, final CardInterface... seenCards) {
        for (final CardInterface notSeenCard : deck.toArray()) {
            deck.draw(notSeenCard);
            ProbabilitiesWithABoardOfFiveCards.from(
                    phb,
                    deck,
                    seenCards[PLAYER_CARD_1_INDEX],
                    seenCards[PLAYER_CARD_2_INDEX],
                    seenCards[FLOP_FIRST_CARD_INDEX],
                    seenCards[FLOP_SECOND_CARD_INDEX],
                    seenCards[FLOP_THIRD_CARD_INDEX],
                    seenCards[TURN_CARD_INDEX],
                    notSeenCard
                    );
            deck.putBack(notSeenCard);
        }
    }

}
