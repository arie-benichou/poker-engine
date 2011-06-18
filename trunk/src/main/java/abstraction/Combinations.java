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

package abstraction;

import java.math.BigInteger;
import java.util.Iterator;

final class Combinations implements Iterator<int[]>, Iterable<int[]> {

    private final int numberOfElements;
    private final int numberOfElementsTaken;
    private final BigInteger numberOfCombinations;

    private int[] combination;
    private BigInteger numberOfCombinationsLeft;

    public int getNumberOfElements() {
        return this.numberOfElements;
    }

    public int getNumberOfElementsTaken() {
        return this.numberOfElementsTaken;
    }

    public BigInteger getNumberOfCombinations() {
        return this.numberOfCombinations;
    }

    private BigInteger getNumberOfCombinationsLeft() {
        return this.numberOfCombinationsLeft;
    }

    private static BigInteger factorial(final int n) {
        BigInteger factorial = BigInteger.ONE;
        for (int i = n; i > 1; --i)
            factorial = factorial.multiply(new BigInteger(Integer.toString(i)));
        return factorial;
    }

    public Combinations(final int n, final int k) {
        if (n < 1 || k > n)
            throw new IllegalArgumentException();
        this.numberOfElements = n;
        this.numberOfElementsTaken = k;
        this.numberOfCombinations = factorial(n).divide(factorial(k).multiply(factorial(n - k)));
        this.numberOfCombinationsLeft = new BigInteger(this.numberOfCombinations.toString());
    }

    @Override
    public Iterator<int[]> iterator() {
        return this;
    }

    @Override
    public boolean hasNext() {
        return 1 == this.getNumberOfCombinationsLeft().compareTo(BigInteger.ZERO);
    }

    @Override
    public int[] next() {
        // Algorithm from Kenneth H. Rosen
        if (this.getNumberOfCombinationsLeft().equals(this.getNumberOfCombinations())) {
            this.combination = new int[this.getNumberOfElementsTaken()];
            for (int i = 0; i < this.getNumberOfElementsTaken(); ++i)
                this.combination[i] = i;
        }
        else {
            int i = this.getNumberOfElementsTaken() - 1;
            while (this.combination[i] == i + this.getNumberOfElements() - this.getNumberOfElementsTaken())
                --i;
            this.combination[i] = this.combination[i] + 1;
            for (int j = i + 1; j < this.getNumberOfElementsTaken(); ++j)
                this.combination[j] = this.combination[i] + j - i;
        }
        this.numberOfCombinationsLeft = this.getNumberOfCombinationsLeft().subtract(BigInteger.ONE);
        return this.combination.clone();
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

    public static void main(final String[] args) {

        // TODO ! écrire tests unitaires (avant)

        Combinations combinations;

        combinations = new Combinations(7, 5);
        for (final int[] combination : combinations) {
            for (final int i : combination)
                System.out.print(i + " ");
            System.out.println();
        }

        combinations = new Combinations(4, 2);
        while (combinations.hasNext()) {
            for (final int i : combinations.next())
                System.out.print(i + " ");
            System.out.println();
        }
    }

}
