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

import abstraction.hand.component.HandComponentType;
import abstraction.hand.component.HandComponents;

// TODO bon candidat pour le pattern visitor
final class HandParser {

    private HandParser() {}

    public static Rankable[] parse(final Hand hand) {
        int n;
        final int[] factors = hand.getFactors();
        final Rankable[] elements;
        switch (hand.getType()) {
            case OnePair:
                elements = new Rankable[4];
                n = 0;
                for (int i = factors[0]; i > 0; --i)
                    if (factors[2 * i] == 1)
                        elements[++n] = HandComponents.get(HandComponentType.HighCard, factors[2 * i - 1]);
                    else
                        elements[0] = HandComponents.get(HandComponentType.OnePair, factors[2 * i - 1]);
                break;
            case TwoPair:
                elements = new Rankable[3];
                n = -1;
                for (int i = factors[0]; i > 0; --i)
                    if (factors[2 * i] == 2)
                        elements[++n] = HandComponents.get(HandComponentType.OnePair, factors[2 * i - 1]);
                    else
                        elements[2] = HandComponents.get(HandComponentType.HighCard, factors[2 * i - 1]);
                break;
            case ThreeOfAKind:
                elements = new Rankable[3];
                n = 0;
                for (int i = factors[0]; i > 0; --i)
                    if (factors[2 * i] == 1)
                        elements[++n] = HandComponents.get(HandComponentType.HighCard, factors[2 * i - 1]);
                    else
                        elements[0] = HandComponents.get(HandComponentType.ThreeOfAKind, factors[2 * i - 1]);
                break;
            case FullHouse:
                elements = new Rankable[2];
                if (factors[4] == 2) {
                    elements[0] = HandComponents.get(HandComponentType.ThreeOfAKind, factors[1]);
                    elements[1] = HandComponents.get(HandComponentType.OnePair, factors[3]);
                }
                else {
                    elements[0] = HandComponents.get(HandComponentType.ThreeOfAKind, factors[3]);
                    elements[1] = HandComponents.get(HandComponentType.OnePair, factors[1]);
                }
                break;
            case FourOfAKind:
                elements = new Rankable[2];
                if (factors[4] == 1) {
                    elements[0] = HandComponents.get(HandComponentType.FourOfAKind, factors[1]);
                    elements[1] = HandComponents.get(HandComponentType.HighCard, factors[3]);
                }
                else {
                    elements[0] = HandComponents.get(HandComponentType.FourOfAKind, factors[3]);
                    elements[1] = HandComponents.get(HandComponentType.HighCard, factors[1]);
                }
                break;
            case Straight:
            case StraightFlush:
                elements = new Rankable[1];
                elements[0] = HandComponents.get(HandComponentType.HighCard, factors[9]); // 2 * 5 - 1
                break;
            /*
            case HighCard:
            case Flush:
            */
            default:
                elements = new Rankable[5];
                n = -1;
                for (int i = factors[0]; i > 0; --i)
                    elements[++n] = HandComponents.get(HandComponentType.HighCard, factors[2 * i - 1]);
        }
        return elements;
    }

}
