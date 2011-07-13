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

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import abstraction.card.CardInterface;
import abstraction.card.CardRank;
import abstraction.card.CardType;
import abstraction.card.Cards;

final class Deck implements Iterator<CardInterface>, Iterable<CardInterface> {

    public final static int NUMBER_OF_CARDS = 52;

    private final Map<CardInterface, Boolean> cards = new TreeMap<CardInterface, Boolean>();
    private int numberOfCardsOut;

    private int numberOfIterationsLeft;
    private CardInterface[] arrayOfCards;

    public Deck() {
        this.reset();
    }

    public void reset() {
        this.numberOfCardsOut = 0;
        for (final CardType type : CardType.values())
            for (final CardRank rank : CardRank.values())
                this.cards.put(Cards.get(type, rank), true);
    }

    public boolean contains(final CardInterface card) {
        return this.cards.get(card) == true;
    }

    public CardInterface draw(final CardInterface card) {
        if (this.cards.get(card)) {
            this.cards.put(card, false);
            ++this.numberOfCardsOut;
            return card;
        }
        return null;
    }

    public Deck putBack(final CardInterface card) {
        if (this.cards.get(card) == false) {
            this.cards.put(card, true);
            --this.numberOfCardsOut;
        }
        return this;
    }

    public CardInterface[] toArray() {
        final CardInterface[] cards = new CardInterface[NUMBER_OF_CARDS - this.numberOfCardsOut];
        int n = -1;
        for (final Entry<CardInterface, Boolean> data : this.cards.entrySet())
            if (data.getValue())
                cards[++n] = data.getKey();
        return cards;
    }

    @Override
    public Iterator<CardInterface> iterator() {
        this.arrayOfCards = this.toArray();
        this.numberOfIterationsLeft = this.arrayOfCards.length;
        return this;
    }

    @Override
    public boolean hasNext() {
        return this.numberOfIterationsLeft > 0;
    }

    @Override
    public CardInterface next() {
        return this.arrayOfCards[NUMBER_OF_CARDS - this.numberOfCardsOut - this.numberOfIterationsLeft--];
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        for (final CardInterface card : this) {
            sb.append(card.toString());
            sb.append("\n");
        }
        return sb.toString();
    }

    public static void main(final String[] args) { // TODO unit tests
        final Deck deck = new Deck();
        System.out.println(deck);
        System.out.println();
        for (final CardInterface card : deck.toArray())
            System.out.println(card);
        System.out.println();
        deck.draw(Cards.get(CardType.HEARTS, CardRank.DEUCE));
        deck.draw(Cards.get(CardType.CLUBS, CardRank.ACE));
        for (final CardInterface card : deck)
            System.out.println(card);
    }

}
