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

import java.util.HashMap;
import java.util.Map;

public final class HandFactors {

    private HandFactors() {}

    public final static Map<Integer, int[]> DATA = new HashMap<Integer, int[]>();

    public static int[] factors(int product) {
        final int[] result = new int[1 + 2 * 5];
        result[0] = 1;
        for (int prime = 2; prime * prime <= product; prime += 2) {
            int power = 1;
            if (product % prime == 0) {
                product /= prime;
                while (product % prime == 0) {
                    product /= prime;
                    ++power;
                }
                result[2 * result[0] - 1] = prime;
                result[2 * result[0]] = power;
                ++result[0];
            }
            if (prime == 2)
                --prime;
        }
        if (product == 1)
            --result[0];
        else {
            result[2 * result[0] - 1] = product;
            result[2 * result[0]] = 1;
        }
        return result;
    }

    // TODO à documenter
    public static int[] get(final int product) {
        int[] factors = DATA.get(product);
        if (factors == null) {
            factors = factors(product);
            DATA.put(product, factors);
        }
        return factors;
    }

}
