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

package abstraction.hand;

import abstraction.card.CardInterface;
import abstraction.card.CardRank;
import abstraction.card.CardType;
import abstraction.card.Cards;

// TODO implémenter Iterable / Iterator ?
// TODO tester une factory avec cache des mains
// TODO avoir la possibilité de considérer l'as comme le début de la suite
// 1,2,3,4,5
// TODO à documenter
public final class Hand implements Comparable<Hand> {

    public final static int NUMBER_OF_CARDS_IN_HAND = 5;

    private final static int SAME_COLOR_SUM = 5;
    private final static int[] SUITES = new int[9];
    static {
        final CardRank[] cardRankValues = CardRank.values();
        for (int i = 0, max = cardRankValues.length - 5; i <= max; ++i) {
            int product = 1;
            for (int j = 0; j < 5; ++j)
                product *= cardRankValues[i + j].getValue();
            SUITES[i] = product;
        }
    }

    private final CardInterface[] cards = new CardInterface[NUMBER_OF_CARDS_IN_HAND];
    private final boolean isMadeOfSameType;
    private final boolean isSequence;
    private final int rankProduct;

    private Rankable[] data = null;

    private static boolean isSequence(final int rankProduct) {
        for (final int p : SUITES)
            if (p == rankProduct)
                return true;
        return false;
    }

    private static boolean isMadeOfSameType(final int colorSum, final int typeProduct) {
        if (Math.abs(colorSum) == SAME_COLOR_SUM)
            return HandFactors.get(typeProduct)[0] == 1;
        return false;
    }

    public Hand(final CardInterface... cards) {
        if (cards.length != NUMBER_OF_CARDS_IN_HAND)
            throw new IllegalArgumentException("Number of cards in a hand must be equals to " + NUMBER_OF_CARDS_IN_HAND);

        int rankProduct = 1;
        int typeProduct = 1;
        int colorSum = 0;
        for (int i = 0; i < NUMBER_OF_CARDS_IN_HAND; ++i) {
            this.cards[i] = cards[i]; // defensive copy
            rankProduct *= cards[i].getRank().getValue();
            typeProduct *= cards[i].getType().getId();
            colorSum += cards[i].getColor().getId();
        }

        this.rankProduct = rankProduct;
        this.isSequence = isSequence(rankProduct);
        this.isMadeOfSameType = isMadeOfSameType(colorSum, typeProduct);
    }

    public int getRankProduct() {
        return this.rankProduct;
    }

    public boolean isMadeOfSameType() {
        return this.isMadeOfSameType;
    }

    public boolean isSequence() {
        return this.isSequence;
    }

    private Rankable[] getData() {
        if (this.data == null)
            this.data = HandParser.parse(this);
        return this.data;
    }

    private Rankable getData(final int index) {
        return this.getData()[index];
    }

    public HandType getType() {
        return (HandType) this.getData(0);
    }

    @Override
    public final int compareTo(final Hand that) {
        for (int i = 0; i < this.getData().length; ++i) {
            if (this.getData(i).getRankValue() < that.getData(i).getRankValue())
                return -1;
            if (this.getData(i).getRankValue() > that.getData(i).getRankValue())
                return 1;
        }
        return 0;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        for (final CardInterface card : this.cards)
            sb.append(card + "    ");
        sb.append("\n" + this.getType() + " :\n");
        for (int i = 1, max = this.getData().length; i < max; ++i)
            sb.append(" - " + this.getData(i) + "\n");
        return sb.toString();
    }

    public static void main(final String[] args) {
        // TODO écrire tests unitaires (avant)
        Hand hand1;
        Hand hand2;

        hand1 = new Hand(
                Cards.get(CardType.HEARTS, CardRank.ACE),
                Cards.get(CardType.HEARTS, CardRank.ACE),
                Cards.get(CardType.HEARTS, CardRank.QUEEN),
                Cards.get(CardType.HEARTS, CardRank.QUEEN),
                Cards.get(CardType.DIAMONDS, CardRank.NINE));

        hand2 = new Hand(
                Cards.get(CardType.HEARTS, CardRank.ACE),
                Cards.get(CardType.HEARTS, CardRank.ACE),
                Cards.get(CardType.HEARTS, CardRank.KING),
                Cards.get(CardType.HEARTS, CardRank.KING),
                Cards.get(CardType.DIAMONDS, CardRank.QUEEN));

        System.out.println(hand1.compareTo(hand2));

        hand1 = new Hand(
                Cards.get(CardType.HEARTS, CardRank.ACE),
                Cards.get(CardType.HEARTS, CardRank.ACE),
                Cards.get(CardType.HEARTS, CardRank.ACE),
                Cards.get(CardType.HEARTS, CardRank.QUEEN),
                Cards.get(CardType.DIAMONDS, CardRank.NINE));

        hand2 = new Hand(
                Cards.get(CardType.SPADES, CardRank.NINE),
                Cards.get(CardType.HEARTS, CardRank.QUEEN),
                Cards.get(CardType.HEARTS, CardRank.ACE),
                Cards.get(CardType.HEARTS, CardRank.ACE),
                Cards.get(CardType.HEARTS, CardRank.ACE));

        System.out.println(hand1.compareTo(hand2));

        hand1 = new Hand(
                Cards.get(CardType.HEARTS, CardRank.ACE),
                Cards.get(CardType.HEARTS, CardRank.KING),
                Cards.get(CardType.HEARTS, CardRank.QUEEN),
                Cards.get(CardType.HEARTS, CardRank.JACK),
                Cards.get(CardType.DIAMONDS, CardRank.NINE));

        hand2 = new Hand(
                Cards.get(CardType.HEARTS, CardRank.ACE),
                Cards.get(CardType.HEARTS, CardRank.KING),
                Cards.get(CardType.HEARTS, CardRank.QUEEN),
                Cards.get(CardType.HEARTS, CardRank.JACK),
                Cards.get(CardType.DIAMONDS, CardRank.EIGHT));

        System.out.println(hand1.compareTo(hand2));
    }

}
