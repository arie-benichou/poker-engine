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

package abstraction.card;

import java.util.HashMap;
import java.util.Map;

public final class Cards {

    private final static int[][] CARD_PRIME_IDS = {
            { 2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41 },
            { 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101 },
            { 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167 },
            { 173, 179, 181, 191, 193, 197, 199, 211, 223, 227, 229, 233, 239 }
    };

    private final static Map<Integer, Card> DATA = new HashMap<Integer, Card>(52);

    private Cards() {}

    public static CardInterface get(final CardType type, final CardRank rank) {
        final int key = type.getId() * rank.getValue(); // TODO utiliser CARD_PRIME_IDS
        Card card = DATA.get(key);
        if (card == null) {
            card = new Card(CARD_PRIME_IDS[type.ordinal()][rank.ordinal()], type, rank);
            DATA.put(key, card);
        }
        return card;
    }
}
