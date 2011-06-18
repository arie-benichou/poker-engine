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

import abstraction.card.CardRank;
import abstraction.hand.Rankable;

abstract class AbstractHandComponent implements Rankable {

    private final CardRank rank;
    private final HandComponentType type;

    public AbstractHandComponent(final HandComponentType type, final CardRank rank) {
        this.type = type;
        this.rank = rank;
    }

    public final HandComponentType getType() {
        return this.type;
    }

    @Override
    public final int getRankValue() {
        return this.rank.getValue();
    }

    @Override
    public final String toString() {
        return this.getType() + "(" + this.rank + ")";
    }

}
