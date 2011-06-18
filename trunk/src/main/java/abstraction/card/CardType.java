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

public enum CardType {

    HEARTS(43, CardTypeColor.RED),
    SPADES(47, CardTypeColor.BLACK),
    DIAMONDS(53, CardTypeColor.RED),
    CLUBS(59, CardTypeColor.BLACK);

    private final int id;
    private final CardTypeColor color;

    private CardType(final int id, final CardTypeColor color) {
        this.id = id;
        this.color = color;
    }

    public int getId() {
        return this.id;
    }

    public CardTypeColor getColor() {
        return this.color;
    }

}
