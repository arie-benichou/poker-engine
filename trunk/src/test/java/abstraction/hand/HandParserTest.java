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

import org.junit.Assert;
import org.junit.Test;

import abstraction.card.CardRank;
import abstraction.card.CardType;
import abstraction.card.Cards;

// TODO à compléter
public class HandParserTest {

    @Test
    public void parseHighCard() {
        Assert.assertTrue(
                HandParser.parse(
                        new Hand(
                                Cards.get(CardType.HEARTS, CardRank.ACE),
                                Cards.get(CardType.HEARTS, CardRank.KING),
                                Cards.get(CardType.HEARTS, CardRank.QUEEN),
                                Cards.get(CardType.HEARTS, CardRank.JACK),
                                Cards.get(CardType.DIAMONDS, CardRank.NINE)
                        )
                        )[0].equals(HandType.HighCard));
    }

    @Test
    public void parseOnePair() {
        Assert.assertTrue(
                HandParser.parse(
                        new Hand(
                                Cards.get(CardType.HEARTS, CardRank.ACE),
                                Cards.get(CardType.DIAMONDS, CardRank.ACE),
                                Cards.get(CardType.HEARTS, CardRank.QUEEN),
                                Cards.get(CardType.HEARTS, CardRank.JACK),
                                Cards.get(CardType.HEARTS, CardRank.NINE)
                        )
                        )[0].equals(HandType.OnePair));
    }

    @Test
    public void parseTwoPair() {
        Assert.assertTrue(
                HandParser.parse(
                        new Hand(
                                Cards.get(CardType.HEARTS, CardRank.ACE),
                                Cards.get(CardType.DIAMONDS, CardRank.ACE),
                                Cards.get(CardType.HEARTS, CardRank.QUEEN),
                                Cards.get(CardType.DIAMONDS, CardRank.QUEEN),
                                Cards.get(CardType.HEARTS, CardRank.NINE)
                        )
                        )[0].equals(HandType.TwoPair));
    }

    @Test
    public void parseThreeOfAKind() {
        Assert.assertTrue(
                HandParser.parse(
                        new Hand(
                                Cards.get(CardType.HEARTS, CardRank.ACE),
                                Cards.get(CardType.DIAMONDS, CardRank.ACE),
                                Cards.get(CardType.CLUBS, CardRank.ACE),
                                Cards.get(CardType.DIAMONDS, CardRank.QUEEN),
                                Cards.get(CardType.HEARTS, CardRank.NINE)
                        )
                        )[0].equals(HandType.ThreeOfAKind));
    }

    @Test
    public void parseStraight() {
        Assert.assertTrue(
                HandParser.parse(
                        new Hand(
                                Cards.get(CardType.HEARTS, CardRank.ACE),
                                Cards.get(CardType.DIAMONDS, CardRank.KING),
                                Cards.get(CardType.CLUBS, CardRank.QUEEN),
                                Cards.get(CardType.SPADES, CardRank.JACK),
                                Cards.get(CardType.HEARTS, CardRank.TEN)
                        )
                        )[0].equals(HandType.Straight));
    }

    @Test
    public void parseFlush() {
        Assert.assertTrue(
                HandParser.parse(
                        new Hand(
                                Cards.get(CardType.HEARTS, CardRank.ACE),
                                Cards.get(CardType.HEARTS, CardRank.QUEEN),
                                Cards.get(CardType.HEARTS, CardRank.TEN),
                                Cards.get(CardType.HEARTS, CardRank.EIGHT),
                                Cards.get(CardType.HEARTS, CardRank.DEUCE)
                        )
                        )[0].equals(HandType.Flush));
    }

    @Test
    public void parseFullHouse() {
        Assert.assertTrue(
                HandParser.parse(
                        new Hand(
                                Cards.get(CardType.HEARTS, CardRank.ACE),
                                Cards.get(CardType.SPADES, CardRank.ACE),
                                Cards.get(CardType.DIAMONDS, CardRank.TEN),
                                Cards.get(CardType.CLUBS, CardRank.TEN),
                                Cards.get(CardType.HEARTS, CardRank.TEN)
                        )
                        )[0].equals(HandType.FullHouse));
    }

    @Test
    public void parseFourOfAKind() {
        Assert.assertTrue(
                HandParser.parse(
                        new Hand(
                                Cards.get(CardType.HEARTS, CardRank.ACE),
                                Cards.get(CardType.SPADES, CardRank.TEN),
                                Cards.get(CardType.DIAMONDS, CardRank.TEN),
                                Cards.get(CardType.CLUBS, CardRank.TEN),
                                Cards.get(CardType.HEARTS, CardRank.TEN)
                        )
                        )[0].equals(HandType.FourOfAKind));
    }

    @Test
    public void parseStraightFlush() {
        Assert.assertTrue(
                HandParser.parse(
                        new Hand(
                                Cards.get(CardType.HEARTS, CardRank.DEUCE),
                                Cards.get(CardType.HEARTS, CardRank.TREY),
                                Cards.get(CardType.HEARTS, CardRank.FOUR),
                                Cards.get(CardType.HEARTS, CardRank.FIVE),
                                Cards.get(CardType.HEARTS, CardRank.SIX)
                        )
                        )[0].equals(HandType.StraightFlush));
    }

}
