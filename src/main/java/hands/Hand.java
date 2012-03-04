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

package hands;

import cards.CardInterface;
import cards.CardRank;

// TODO ? avoir la possibilité de considérer l'as comme le début de la suite
// 1,2,3,4,5
public final class Hand implements Comparable<Hand> {

    public final static int NUMBER_OF_CARDS_IN_HAND = 5;
    private final static int SAME_COLOR_SUM = 5;
    private final static int[] SUITES = new int[9];
    static {
        final CardRank[] cardRankValues = CardRank.values();
        for (int i = 0, max = cardRankValues.length - 5; i <= max; ++i) {
            int product = 1;
            for (int j = 0; j < 5; ++j) {
                product *= cardRankValues[i + j].getValue();
            }
            SUITES[i] = product;
        }
    }

    private final CardInterface[] cards = new CardInterface[NUMBER_OF_CARDS_IN_HAND];

    private int rankProduct = 1;
    private int[] factors = null;
    private HandType type;
    private Rankable[] data = null;
    private int typeProduct = 1;
    private int colorSum = 0;

    private static boolean isSequence(final int rankProduct) {
        for (final int p : SUITES) {
            if (p == rankProduct) { return true; }
        }
        return false;
    }

    private static boolean isMadeOfSameType(final int colorSum, final int typeProduct) {
        if (Math.abs(colorSum) == SAME_COLOR_SUM) { return HandFactors.get(typeProduct)[0] == 1; }
        return false;
    }

    private static HandType computeType(final int[] factors, final boolean isSequence, final boolean isMadeOfSameType) {
        if (!isMadeOfSameType && !isSequence) {
            if (factors[0] == 5) { return HandType.HighCard; }
            if (factors[0] == 4) { return HandType.OnePair; }
            if (factors[0] == 3) {
                if (factors[2] == 2 || factors[4] == 2) { return HandType.TwoPair; }
                return HandType.ThreeOfAKind;
            }
            if (factors[2] == 2 || factors[2] == 3) { return HandType.FullHouse; }
            return HandType.FourOfAKind;
        }
        if (!isMadeOfSameType && isSequence) { return HandType.Straight; }
        if (isMadeOfSameType && !isSequence) { return HandType.Flush; }
        return HandType.StraightFlush;
    }

    // TODO tester avec une map dont les clés sont triés
    public Hand(final CardInterface... cards) {
        /*
        if (cards.length != NUMBER_OF_CARDS_IN_HAND)
            throw new IllegalArgumentException("Number of cards in a hand must be equals to " + NUMBER_OF_CARDS_IN_HAND);
        */
        for (int i = 0; i < NUMBER_OF_CARDS_IN_HAND; ++i) {
            this.cards[i] = cards[i]; // defensive copy
            this.rankProduct *= cards[i].getRank().getValue();
            this.typeProduct *= cards[i].getType().getId();
            this.colorSum += cards[i].getColor().getId();
        }
    }

    public int[] getFactors() {
        if (this.factors == null) {
            this.factors = HandFactors.get(this.rankProduct);
        }
        return this.factors;
    }

    private Rankable[] getData() {
        if (this.data == null) {
            this.data = HandParser.parse(this);
        }
        return this.data;
    }

    private Rankable getData(final int index) {
        return this.getData()[index];
    }

    public HandType getType() {
        if (this.type == null) {
            this.type = computeType(this.getFactors(), isSequence(this.rankProduct), isMadeOfSameType(this.colorSum, this.typeProduct));
        }
        return this.type;
    }

    public int computeScore() {
        int sum = 0;
        for (final CardInterface card : this.cards) {
            sum += card.getRank().getValue();
        }
        return this.getType().getDelta() + sum;
    }

    @Override
    public final int compareTo(final Hand that) {
        if (this.getType().getRankValue() < that.getType().getRankValue()) { return -1; }
        if (this.getType().getRankValue() > that.getType().getRankValue()) { return 1; }
        for (int i = 0, max = this.getData().length; i < max; ++i) {
            if (this.getData(i).getRankValue() < that.getData(i).getRankValue()) { return -1; }
            if (this.getData(i).getRankValue() > that.getData(i).getRankValue()) { return 1; }
        }
        return 0;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(" - ");
        for (final CardInterface card : this.cards) {
            sb.append(card + " - ");
        }
        sb.append("\n\n" + this.getType() + " :\n");
        for (final Rankable handComponent : this.getData()) {
            sb.append(" - " + handComponent + "\n");
        }
        return sb.toString();
    }

}