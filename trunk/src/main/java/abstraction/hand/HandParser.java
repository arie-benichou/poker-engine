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

import abstraction.card.CardRank;
import abstraction.card.CardType;
import abstraction.card.Cards;
import abstraction.hand.component.HandComponentType;
import abstraction.hand.component.HandComponents;

// TODO bon candidat pour le pattern visitor
// TODO à documenter
final class HandParser {

    private HandParser() {}

    private static HandType getType(final Hand hand, final int[] factors) {

        if (!hand.isMadeOfSameType() && !hand.isSequence()) {

            if (factors[0] == 5)
                return HandType.HighCard;

            if (factors[0] == 4)
                return HandType.OnePair;

            if (factors[0] == 3) {
                if (factors[2] == 2 || factors[4] == 2) // inutile de tester si factors[6] == 2
                    return HandType.TwoPair;

                return HandType.ThreeOfAKind;
            }

            if (factors[2] == 2 || factors[2] == 3) //if (numberOfPrimeFactors == 2)
                return HandType.FullHouse;

            return HandType.FourOfAKind;
        }

        if (!hand.isMadeOfSameType() && hand.isSequence())
            return HandType.Straight;

        if (hand.isMadeOfSameType() && !hand.isSequence())
            return HandType.Flush;

        return HandType.StraightFlush;
    }

    public static Rankable[] parse(final Hand hand) {

        int n;

        final int[] factors = HandFactors.get(hand.getRankProduct());
        final HandType type = HandParser.getType(hand, factors);
        final Rankable[] elements;

        switch (type) {

            case OnePair:
                elements = new Rankable[1 + 4];
                elements[0] = HandType.OnePair;
                n = 1;
                for (int i = factors[0]; i > 0; --i)
                    if (factors[2 * i] == 1)
                        elements[++n] = HandComponents.get(HandComponentType.HighCard, factors[2 * i - 1]);
                    else
                        elements[1] = HandComponents.get(HandComponentType.OnePair, factors[2 * i - 1]);
                break;

            case TwoPair:
                elements = new Rankable[1 + 3];
                elements[0] = HandType.TwoPair;
                n = 0;
                for (int i = factors[0]; i > 0; --i)
                    if (factors[2 * i] == 2)
                        elements[++n] = HandComponents.get(HandComponentType.OnePair, factors[2 * i - 1]);
                    else
                        elements[3] = HandComponents.get(HandComponentType.HighCard, factors[2 * i - 1]);
                break;

            case ThreeOfAKind:
                elements = new Rankable[1 + 3];
                elements[0] = HandType.ThreeOfAKind;
                n = 1;
                for (int i = factors[0]; i > 0; --i)
                    if (factors[2 * i] == 1)
                        elements[++n] = HandComponents.get(HandComponentType.HighCard, factors[2 * i - 1]);
                    else
                        elements[1] = HandComponents.get(HandComponentType.ThreeOfAKind, factors[2 * i - 1]);
                break;

            case FullHouse:
                elements = new Rankable[1 + 2];
                elements[0] = HandType.FullHouse;
                if (factors[4] == 2) {
                    elements[1] = HandComponents.get(HandComponentType.ThreeOfAKind, factors[1]);
                    elements[2] = HandComponents.get(HandComponentType.OnePair, factors[3]);
                }
                else {
                    elements[1] = HandComponents.get(HandComponentType.ThreeOfAKind, factors[3]);
                    elements[2] = HandComponents.get(HandComponentType.OnePair, factors[1]);
                }
                break;

            case FourOfAKind:
                elements = new Rankable[1 + 2];
                elements[0] = HandType.FourOfAKind;
                if (factors[4] == 1) {
                    elements[1] = HandComponents.get(HandComponentType.FourOfAKind, factors[1]);
                    elements[2] = HandComponents.get(HandComponentType.HighCard, factors[3]);
                }
                else {
                    elements[1] = HandComponents.get(HandComponentType.FourOfAKind, factors[3]);
                    elements[2] = HandComponents.get(HandComponentType.HighCard, factors[1]);
                }
                break;

            /*
            case HighCard:
            case Straight:
            case Flush:
            case StraightFlush:
            */

            default:
                elements = new Rankable[1 + 5];
                elements[0] = type;
                n = 0;
                for (int i = factors[0]; i > 0; --i)
                    elements[++n] = HandComponents.get(HandComponentType.HighCard, factors[2 * i - 1]);
        }

        return elements;
    }

    public static void main(final String[] args) {

        final long t0 = System.currentTimeMillis();

        for (int n = 0; n < 1111111; ++n) {
            //HighCard
            HandParser.parse(
                    new Hand(
                            Cards.get(CardType.HEARTS, CardRank.ACE),
                            Cards.get(CardType.HEARTS, CardRank.KING),
                            Cards.get(CardType.HEARTS, CardRank.QUEEN),
                            Cards.get(CardType.HEARTS, CardRank.JACK),
                            Cards.get(CardType.DIAMONDS, CardRank.NINE)
                    )
                    );

            //OnePair
            HandParser.parse(
                    new Hand(
                            Cards.get(CardType.HEARTS, CardRank.ACE),
                            Cards.get(CardType.DIAMONDS, CardRank.ACE),
                            Cards.get(CardType.HEARTS, CardRank.QUEEN),
                            Cards.get(CardType.HEARTS, CardRank.JACK),
                            Cards.get(CardType.HEARTS, CardRank.NINE)
                    )
                    );

            //TwoPair
            HandParser.parse(
                    new Hand(
                            Cards.get(CardType.HEARTS, CardRank.ACE),
                            Cards.get(CardType.DIAMONDS, CardRank.ACE),
                            Cards.get(CardType.HEARTS, CardRank.QUEEN),
                            Cards.get(CardType.DIAMONDS, CardRank.QUEEN),
                            Cards.get(CardType.HEARTS, CardRank.NINE)
                    )
                    );

            //ThreeOfAKind
            HandParser.parse(
                    new Hand(
                            Cards.get(CardType.HEARTS, CardRank.ACE),
                            Cards.get(CardType.DIAMONDS, CardRank.ACE),
                            Cards.get(CardType.CLUBS, CardRank.ACE),
                            Cards.get(CardType.DIAMONDS, CardRank.QUEEN),
                            Cards.get(CardType.HEARTS, CardRank.NINE)
                    )
                    );

            //Straight
            HandParser.parse(
                    new Hand(
                            Cards.get(CardType.HEARTS, CardRank.ACE),
                            Cards.get(CardType.DIAMONDS, CardRank.KING),
                            Cards.get(CardType.CLUBS, CardRank.QUEEN),
                            Cards.get(CardType.SPADES, CardRank.JACK),
                            Cards.get(CardType.HEARTS, CardRank.TEN)
                    )
                    );

            //Flush
            HandParser.parse(
                    new Hand(
                            Cards.get(CardType.HEARTS, CardRank.ACE),
                            Cards.get(CardType.HEARTS, CardRank.QUEEN),
                            Cards.get(CardType.HEARTS, CardRank.TEN),
                            Cards.get(CardType.HEARTS, CardRank.EIGHT),
                            Cards.get(CardType.HEARTS, CardRank.DEUCE)
                    )
                    );

            //FullHouse
            HandParser.parse(
                    new Hand(
                            Cards.get(CardType.HEARTS, CardRank.ACE),
                            Cards.get(CardType.SPADES, CardRank.ACE),
                            Cards.get(CardType.DIAMONDS, CardRank.TEN),
                            Cards.get(CardType.CLUBS, CardRank.TEN),
                            Cards.get(CardType.HEARTS, CardRank.TEN)
                    )
                    );

            //FourOfAKind
            HandParser.parse(
                    new Hand(
                            Cards.get(CardType.HEARTS, CardRank.ACE),
                            Cards.get(CardType.SPADES, CardRank.TEN),
                            Cards.get(CardType.DIAMONDS, CardRank.TEN),
                            Cards.get(CardType.CLUBS, CardRank.TEN),
                            Cards.get(CardType.HEARTS, CardRank.TEN)
                    )
                    );

            //StraightFlush
            HandParser.parse(
                    new Hand(
                            Cards.get(CardType.HEARTS, CardRank.DEUCE),
                            Cards.get(CardType.HEARTS, CardRank.TREY),
                            Cards.get(CardType.HEARTS, CardRank.FOUR),
                            Cards.get(CardType.HEARTS, CardRank.FIVE),
                            Cards.get(CardType.HEARTS, CardRank.SIX)
                    )
                    );
        }

        final long t1 = System.currentTimeMillis();

        System.out.println(t1 - t0 + " ms");
        // 1 111 111 x 9 Hands parsées | ~ 10 secondes | 52 instances de HandComponents
    }
}
