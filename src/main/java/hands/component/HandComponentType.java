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

package hands.component;

import java.lang.reflect.InvocationTargetException;

import cards.CardRank;


public enum HandComponentType {

    HighCard(43, HighCard.class),
    OnePair(47, OnePair.class),
    ThreeOfAKind(53, ThreeOfAKind.class),
    FourOfAKind(59, FourOfAKind.class);

    private int hashCodeFactor;
    private Class<? extends AbstractHandComponent> classObject;

    private HandComponentType(final int hashCodeFactor, final Class<? extends AbstractHandComponent> classObject) {
        this.hashCodeFactor = hashCodeFactor;
        this.classObject = classObject;
    }

    public AbstractHandComponent newIntance(final CardRank rank) {
        AbstractHandComponent newIntance = null;
        try {
            newIntance = this.classObject.getConstructor(CardRank.class).newInstance(rank);
        }
        catch (final IllegalArgumentException e) {
            e.printStackTrace();
        }
        catch (final SecurityException e) {
            e.printStackTrace();
        }
        catch (final InstantiationException e) {
            e.printStackTrace();
        }
        catch (final IllegalAccessException e) {
            e.printStackTrace();
        }
        catch (final InvocationTargetException e) {
            e.printStackTrace();
        }
        catch (final NoSuchMethodException e) {
            e.printStackTrace();
        }
        return newIntance;
    }

    public int getHashCodeFactor() {
        return this.hashCodeFactor;
    }

}