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

import java.util.HashMap;
import java.util.Map;

import abstraction.card.CardInterface;
import abstraction.card.CardRank;
import abstraction.card.CardType;
import abstraction.card.Cards;

public final class Hands {

    public static int cuttOffs = 0;

    private final static Map<Long, Hand> DATA = new HashMap<Long, Hand>(); // C(52,5) = 2 598 960

    //private final static Map<Integer, Hand> DATA = new HashMap<Integer, Hand>(594787);
    //private final static Map<CardInterface[], Hand> DATA = new HashMap<CardInterface[], Hand>();

    private Hands() {}

    /*
    public static Hand get(final CardInterface... cards) {
        Integer cardsHashCodeProduct = 1;
        for (final CardInterface card : cards)
            cardsHashCodeProduct *= card.hashCode();
        Hand hand = DATA.get(cardsHashCodeProduct);
        if (hand == null) {
            hand = new Hand(cards);
            DATA.put(cardsHashCodeProduct, hand);
        }
        else
            //System.out.println("from cache: " + hand);
            ++cuttOffs;
        return hand;
    }
    */

    /*
    public static Hand get(final CardInterface... cards) {
        Hand hand = DATA.get(cards);
        if (hand == null) {
            hand = new Hand(cards);
            DATA.put(cards, hand);
        }
        else
            System.out.println("from cache: " + hand);
        return hand;
    }
    */

    public static Hand get(final CardInterface... cards) {
        long cardsHashCodeProduct = 1l;
        int rankProduct = 1, typeProduct = 1, colorSum = 0;
        for (final CardInterface card : cards) {
            cardsHashCodeProduct *= card.hashCode();
            rankProduct *= card.getRank().getValue();
            typeProduct *= card.getType().getId();
            colorSum += card.getColor().getId();
        }
        Hand hand = DATA.get(cardsHashCodeProduct);
        if (hand == null) {
            hand = new Hand(rankProduct, typeProduct, colorSum, cards);
            DATA.put(cardsHashCodeProduct, hand);
        }
        /*
        else
            System.out.println("from cache: " + hand);
        */
        return hand;
    }

    public static void debug() {
        System.out.println(DATA.keySet().size());
    }

    public static void main(final String[] args) {

        final CardInterface[] cards = {
                Cards.get(CardType.HEARTS, CardRank.DEUCE),
                Cards.get(CardType.HEARTS, CardRank.TREY),
                Cards.get(CardType.HEARTS, CardRank.FOUR),
                Cards.get(CardType.HEARTS, CardRank.FIVE),
                Cards.get(CardType.HEARTS, CardRank.SIX)
        };

        Hands.get(cards);

        Hands.get(cards);

        Hands.get(
                Cards.get(CardType.HEARTS, CardRank.DEUCE),
                Cards.get(CardType.HEARTS, CardRank.TREY),
                Cards.get(CardType.HEARTS, CardRank.FOUR),
                Cards.get(CardType.HEARTS, CardRank.FIVE),
                Cards.get(CardType.HEARTS, CardRank.SIX)
                );
    }

}
