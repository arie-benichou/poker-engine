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

// TODO ? cardset (paquet propriétaire de la carte)
final class Card implements CardInterface {

    private final CardType type;
    private final CardRank rank;
    private final int id;

    public Card(final int id, final CardType type, final CardRank rank) {
        this.id = id;
        this.type = type;
        this.rank = rank;
    }

    @Override
    public CardType getType() {
        return this.type;
    }

    @Override
    public CardTypeColor getColor() {
        return this.type.getColor();
    }

    @Override
    public CardRank getRank() {
        return this.rank;
    }

    @Override
    public String toString() {
        return this.getRank().name() + " of " + this.getType().name(); // + " (" + this.getColor().name() + ")";
    }

    @Override
    public int hashCode() {
        return this.id;
    }

    @Override
    public boolean equals(final Object object) {
        if (object == this)
            return true;
        if (object == null)
            return false;
        if (!(object instanceof Card))
            return false;
        return ((Card) object).hashCode() == this.hashCode();
    }

    @Override
    public int compareTo(final CardInterface that) {
        return this.hashCode() - that.hashCode();
    }

}
