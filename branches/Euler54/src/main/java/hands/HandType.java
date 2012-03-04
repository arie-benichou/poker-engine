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

package hands;

import cards.CardRank;

// TODO ? overrider compareTo
public enum HandType implements Rankable {

    HighCard {

        @Override
        public CardRank[] getMaximalHandRanks() {
            final CardRank[] hand = {
                    CardRank.ACE,
                    CardRank.KING,
                    CardRank.QUEEN,
                    CardRank.JACK,
                    CardRank.NINE
            };
            return hand;
        }

        @Override
        protected int computeDelta() {
            return 0;
        }
    },
    OnePair {

        @Override
        public CardRank[] getMaximalHandRanks() {
            final CardRank[] hand = {
                    CardRank.ACE,
                    CardRank.ACE,
                    CardRank.KING,
                    CardRank.QUEEN,
                    CardRank.JACK
            };
            return hand;
        }

        @Override
        protected int computeDelta() {
            return HandType.HighCard.computeSumOfMaximalHandRanks() + HandType.HighCard.getDelta();
        }
    },
    TwoPair {

        @Override
        public CardRank[] getMaximalHandRanks() {
            final CardRank[] hand = {
                    CardRank.ACE,
                    CardRank.ACE,
                    CardRank.KING,
                    CardRank.KING,
                    CardRank.QUEEN
            };
            return hand;
        }

        @Override
        protected int computeDelta() {
            return HandType.OnePair.computeSumOfMaximalHandRanks() + HandType.OnePair.getDelta();
        }
    },
    ThreeOfAKind {

        @Override
        public CardRank[] getMaximalHandRanks() {
            final CardRank[] hand = {
                    CardRank.ACE,
                    CardRank.ACE,
                    CardRank.ACE,
                    CardRank.KING,
                    CardRank.QUEEN
            };
            return hand;
        }

        @Override
        protected int computeDelta() {
            return HandType.TwoPair.computeSumOfMaximalHandRanks() + HandType.TwoPair.getDelta();
        }

    },
    Straight {

        @Override
        public CardRank[] getMaximalHandRanks() {
            final CardRank[] hand = {
                    CardRank.ACE,
                    CardRank.KING,
                    CardRank.QUEEN,
                    CardRank.JACK,
                    CardRank.TEN
            };
            return hand;
        }

        @Override
        protected int computeDelta() {
            return HandType.ThreeOfAKind.computeSumOfMaximalHandRanks() + HandType.ThreeOfAKind.getDelta();
        }
    },
    Flush {

        @Override
        public CardRank[] getMaximalHandRanks() {
            final CardRank[] hand = {
                    CardRank.ACE,
                    CardRank.KING,
                    CardRank.QUEEN,
                    CardRank.JACK,
                    CardRank.NINE
            };
            return hand;
        }

        @Override
        protected int computeDelta() {
            return HandType.Straight.computeSumOfMaximalHandRanks() + HandType.Straight.getDelta();
        }

    },
    FullHouse {

        @Override
        public CardRank[] getMaximalHandRanks() {
            final CardRank[] hand = {
                    CardRank.ACE,
                    CardRank.ACE,
                    CardRank.ACE,
                    CardRank.KING,
                    CardRank.KING
            };
            return hand;
        }

        @Override
        protected int computeDelta() {
            return HandType.Flush.computeSumOfMaximalHandRanks() + HandType.Flush.getDelta();
        }
    },
    FourOfAKind {

        @Override
        public CardRank[] getMaximalHandRanks() {
            final CardRank[] hand = {
                    CardRank.ACE,
                    CardRank.ACE,
                    CardRank.ACE,
                    CardRank.ACE,
                    CardRank.KING
            };
            return hand;
        }

        @Override
        protected int computeDelta() {
            return HandType.FullHouse.computeSumOfMaximalHandRanks() + HandType.FullHouse.getDelta();
        }
    },
    StraightFlush {

        @Override
        public CardRank[] getMaximalHandRanks() {
            final CardRank[] hand = {
                    CardRank.ACE,
                    CardRank.KING,
                    CardRank.QUEEN,
                    CardRank.JACK,
                    CardRank.TEN
            };
            return hand;
        }

        @Override
        protected int computeDelta() {
            return HandType.FourOfAKind.computeSumOfMaximalHandRanks() + HandType.FourOfAKind.getDelta();
        }
    };

    private final int delta;

    private HandType() {
        this.delta = this.computeDelta();
    }

    @Override
    public int getRankValue() {
        return this.ordinal(); // ? TODO utiliser le delta
    }

    protected int computeSumOfMaximalHandRanks() {
        int max = 0;
        for (final CardRank cardRank : this.getMaximalHandRanks()) {
            max += cardRank.getValue();
        }
        return max;
    }

    abstract protected int computeDelta();

    public int getDelta() {
        return this.delta;
    }

    abstract public CardRank[] getMaximalHandRanks();

}