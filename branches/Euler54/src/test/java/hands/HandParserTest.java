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

import static org.junit.Assert.assertTrue;
import hands.Hand;
import hands.HandType;

import org.junit.Test;

import cards.CardRank;
import cards.CardType;
import cards.Cards;


public class HandParserTest {

    @Test
    public void parseHighCard() {

        assertTrue(new Hand(
                Cards.get(CardType.HEARTS, CardRank.ACE),
                Cards.get(CardType.HEARTS, CardRank.KING),
                Cards.get(CardType.HEARTS, CardRank.QUEEN),
                Cards.get(CardType.HEARTS, CardRank.JACK),
                Cards.get(CardType.DIAMONDS, CardRank.NINE))
                .getType().equals(HandType.HighCard));

    }

    @Test
    public void parseOnePair() {
        assertTrue(new Hand(
                Cards.get(CardType.HEARTS, CardRank.ACE),
                Cards.get(CardType.DIAMONDS, CardRank.ACE),
                Cards.get(CardType.HEARTS, CardRank.QUEEN),
                Cards.get(CardType.HEARTS, CardRank.JACK),
                Cards.get(CardType.HEARTS, CardRank.NINE))
                .getType().equals(HandType.OnePair));
    }

    @Test
    public void parseTwoPair() {
        assertTrue(new Hand(
                Cards.get(CardType.HEARTS, CardRank.ACE),
                Cards.get(CardType.DIAMONDS, CardRank.ACE),
                Cards.get(CardType.HEARTS, CardRank.QUEEN),
                Cards.get(CardType.DIAMONDS, CardRank.QUEEN),
                Cards.get(CardType.HEARTS, CardRank.NINE))
                .getType().equals(HandType.TwoPair));
    }

    @Test
    public void parseThreeOfAKind() {
        assertTrue(new Hand(
                Cards.get(CardType.HEARTS, CardRank.ACE),
                Cards.get(CardType.DIAMONDS, CardRank.ACE),
                Cards.get(CardType.CLUBS, CardRank.ACE),
                Cards.get(CardType.DIAMONDS, CardRank.QUEEN),
                Cards.get(CardType.HEARTS, CardRank.NINE))
                .getType().equals(HandType.ThreeOfAKind));
    }

    @Test
    public void parseStraight() {
        assertTrue(new Hand(
                Cards.get(CardType.HEARTS, CardRank.ACE),
                Cards.get(CardType.DIAMONDS, CardRank.KING),
                Cards.get(CardType.CLUBS, CardRank.QUEEN),
                Cards.get(CardType.SPADES, CardRank.JACK),
                Cards.get(CardType.HEARTS, CardRank.TEN))
                .getType().equals(HandType.Straight));
    }

    @Test
    public void parseFlush() {
        assertTrue(new Hand(
                Cards.get(CardType.HEARTS, CardRank.ACE),
                Cards.get(CardType.HEARTS, CardRank.QUEEN),
                Cards.get(CardType.HEARTS, CardRank.TEN),
                Cards.get(CardType.HEARTS, CardRank.EIGHT),
                Cards.get(CardType.HEARTS, CardRank.DEUCE))
                .getType().equals(HandType.Flush));
    }

    @Test
    public void parseFullHouse() {
        assertTrue(new Hand(
                Cards.get(CardType.HEARTS, CardRank.ACE),
                Cards.get(CardType.SPADES, CardRank.ACE),
                Cards.get(CardType.DIAMONDS, CardRank.TEN),
                Cards.get(CardType.CLUBS, CardRank.TEN),
                Cards.get(CardType.HEARTS, CardRank.TEN))
                .getType().equals(HandType.FullHouse));
    }

    @Test
    public void parseFourOfAKind() {
        assertTrue(new Hand(
                Cards.get(CardType.HEARTS, CardRank.ACE),
                Cards.get(CardType.SPADES, CardRank.TEN),
                Cards.get(CardType.DIAMONDS, CardRank.TEN),
                Cards.get(CardType.CLUBS, CardRank.TEN),
                Cards.get(CardType.HEARTS, CardRank.TEN))
                .getType().equals(HandType.FourOfAKind));
    }

    @Test
    public void parseStraightFlush() {
        assertTrue(new Hand(
                Cards.get(CardType.HEARTS, CardRank.DEUCE),
                Cards.get(CardType.HEARTS, CardRank.TREY),
                Cards.get(CardType.HEARTS, CardRank.FOUR),
                Cards.get(CardType.HEARTS, CardRank.FIVE),
                Cards.get(CardType.HEARTS, CardRank.SIX))
                .getType().equals(HandType.StraightFlush));
    }

}