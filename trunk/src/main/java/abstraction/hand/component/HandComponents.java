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

package abstraction.hand.component;

import java.util.HashMap;
import java.util.Map;

import abstraction.card.CardRank;
import abstraction.hand.Rankable;

public final class HandComponents {

    private final static Map<Integer, Rankable> DATA = new HashMap<Integer, Rankable>(HandComponentType.values().length * CardRank.values().length);

    static {
        for (final HandComponentType handComponentType : HandComponentType.values())
            for (final CardRank cardRank : CardRank.values())
                DATA.put(handComponentType.getHashCodeFactor() * cardRank.getValue(), handComponentType.newIntance(cardRank));

    }

    private HandComponents() {}

    public static Rankable get(final HandComponentType handComponentType, final int rank) {
        return DATA.get(handComponentType.getHashCodeFactor() * rank);
    }

    public static void main(final String[] args) {

        // TODO unit tests

        System.out.println(DATA.keySet().size());
        System.out.println(DATA.keySet());
        System.out.println(DATA.values());

        System.out.println(HandComponents.get(HandComponentType.FourOfAKind, 1));

        System.out.println(HandComponents.get(HandComponentType.FourOfAKind, 2));
        System.out.println(HandComponents.get(HandComponentType.FourOfAKind, 3));
        System.out.println(HandComponents.get(HandComponentType.FourOfAKind, 5));
        System.out.println(HandComponents.get(HandComponentType.FourOfAKind, 7));
        System.out.println(HandComponents.get(HandComponentType.FourOfAKind, 11));
        System.out.println(HandComponents.get(HandComponentType.FourOfAKind, 13));
        System.out.println(HandComponents.get(HandComponentType.FourOfAKind, 17));
        System.out.println(HandComponents.get(HandComponentType.FourOfAKind, 19));
        System.out.println(HandComponents.get(HandComponentType.FourOfAKind, 23));
        System.out.println(HandComponents.get(HandComponentType.FourOfAKind, 29));
        System.out.println(HandComponents.get(HandComponentType.FourOfAKind, 31));
        System.out.println(HandComponents.get(HandComponentType.FourOfAKind, 37));
        System.out.println(HandComponents.get(HandComponentType.FourOfAKind, 41));
    }

}