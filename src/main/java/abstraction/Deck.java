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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import abstraction.card.CardInterface;
import abstraction.card.CardRank;
import abstraction.card.CardType;
import abstraction.card.Cards;
import abstraction.hand.Hand;

final class Deck implements Iterable<CardInterface> {

    public final static int NUMBER_OF_CARDS = 52;

    private final List<CardInterface> cardsIn = new ArrayList<CardInterface>(NUMBER_OF_CARDS);
    private final List<CardInterface> cardsOut = new ArrayList<CardInterface>(NUMBER_OF_CARDS);

    public Deck(final boolean shuffle) {
        for (final CardType type : CardType.values())
            for (final CardRank rank : CardRank.values()) {
                final CardInterface card = Cards.get(type, rank);
                this.cardsIn.add(card);
            }
        if (shuffle)
            this.shuffle();
    }

    public Deck() {
        this(true);
    }

    public void shuffle() {
        Collections.shuffle(this.cardsIn);
    }

    public void reset() {
        this.cardsIn.addAll(this.cardsOut);
        this.cardsOut.clear();
        this.shuffle();
    }

    public CardInterface draw() {
        if (this.cardsIn.isEmpty())
            return null; // TODO lancer une exception
        this.cardsOut.add(this.cardsIn.get(0));
        return this.cardsIn.remove(0);
    }

    public CardInterface draw(final CardInterface card) {
        if (this.cardsIn.isEmpty())
            return null; // TODO lancer une exception
        if (!this.contains(card))
            return null; // TODO lancer une exception
        final int index = this.cardsIn.indexOf(card);
        this.cardsOut.add(card);
        return this.cardsIn.remove(index);
    }

    public boolean contains(final CardInterface card) {
        if (this.cardsOut.size() < this.cardsIn.size())
            return !this.cardsOut.contains(card);
        return this.cardsIn.contains(card);
    }

    @Override
    public Iterator<CardInterface> iterator() {
        return this.cardsIn.iterator();
    }

    public CardInterface[] toArray() {
        return this.cardsIn.toArray(new CardInterface[this.cardsIn.size()]);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        int n = 0;
        for (final CardInterface card : this)
            sb.append(card.toString() + (++n % 13 == 0 ? "\n\n" : "\n"));
        return sb.toString();
    }

    public static void main(final String[] args) {

        // TODO ! écrire tests unitaires (avant)

        //test1();

        //test2();

        test3();

        test4();

        //test5();

        test6();

    }

    public static void test1() {
        final Deck deck = new Deck();
        System.out.println();
        System.out.println(deck);
        deck.reset();
        System.out.println();
        System.out.println();
        System.out.println(deck);
        System.out.println(deck.contains(Cards.get(CardType.HEARTS, CardRank.QUEEN)));
        //System.out.println(deck.contains(new Card(CardType.HEARTS, CardRank.QUEEN)));
        //System.out.println(deck.draw(new Card(CardType.HEARTS, CardRank.QUEEN)));
        System.out.println(deck.draw(Cards.get(CardType.HEARTS, CardRank.QUEEN)));
    }

    public static void test2() {
        final Deck deck = new Deck();
        final CardInterface[] cards = new CardInterface[5];
        final List<Hand> hands = new ArrayList<Hand>();
        for (int n = 0; n < 4; ++n) {
            for (int i = 0; i < 5; ++i)
                cards[i] = deck.draw();
            hands.add(new Hand(cards));
            deck.reset();
        }
        Collections.sort(hands);
        System.out.println(hands);
    }

    public static void test3() {
        final Deck deck = new Deck();
        final CardInterface[] cards = new CardInterface[5];
        final long t0 = System.currentTimeMillis();
        for (int n = 0; n < 100000; ++n) {
            for (int i = 0; i < 5; ++i)
                cards[i] = deck.draw();
            new Hand(cards);
            deck.reset();
        }
        final long t1 = System.currentTimeMillis();
        System.out.println(t1 - t0 + " ms");
    }

    public static void test4() {
        final Deck deck = new Deck();
        final CardInterface[] cards = new CardInterface[5];
        final long t0 = System.currentTimeMillis();
        for (int n = 0; n < 100000; ++n) {
            for (int i = 0; i < 5; ++i)
                cards[i] = deck.draw();
            new Hand(cards).getType();
            deck.reset();
        }
        final long t1 = System.currentTimeMillis();
        System.out.println(t1 - t0 + " ms");
    }

    public static void test5() {
        final Deck deck = new Deck();
        final CardInterface[] twoCards = new CardInterface[2];
        final CardInterface[] fiveCards = new CardInterface[5];
        for (int i = 0; i < 2; ++i)
            twoCards[i] = deck.draw();
        for (final CardInterface card : twoCards)
            System.out.print(card + "    ");
        for (int i = 0; i < 5; ++i)
            fiveCards[i] = deck.draw();
        System.out.println();
        for (final CardInterface card : fiveCards)
            System.out.print(card + "    ");
        System.out.println();
        System.out.println(BestHandFinder.from(twoCards, fiveCards));
    }

    public static void test6() {
        final Deck deck = new Deck();
        final CardInterface[] twoCards = new CardInterface[2];
        final CardInterface[] fiveCards = new CardInterface[5];
        final long t0 = System.currentTimeMillis();
        for (int n = 0; n < 100000; ++n) {
            for (int i = 0; i < 2; ++i)
                twoCards[i] = deck.draw();
            for (int i = 0; i < 5; ++i)
                fiveCards[i] = deck.draw();
            BestHandFinder.from(twoCards, fiveCards);
            deck.reset();
        }
        final long t1 = System.currentTimeMillis();
        System.out.println(t1 - t0 + " ms");
    }

}
