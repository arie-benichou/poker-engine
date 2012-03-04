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

package euler54;

import hands.Hand;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import cards.CardInterface;
import cards.CardRank;
import cards.CardType;
import cards.Cards;

import com.google.common.base.Charsets;
import com.google.common.base.Splitter;
import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableMap;
import com.google.common.io.Files;
import com.google.common.io.LineProcessor;

public final class Euler54 {

    private final static Stopwatch STOP_WATCH = new Stopwatch().start();

    private final static Map<Character, CardType> CARD_TYPES_MAP = new ImmutableMap.Builder<Character, CardType>()
            .put('H', CardType.HEARTS)
            .put('S', CardType.SPADES)
            .put('D', CardType.DIAMONDS)
            .put('C', CardType.CLUBS)
            .build();

    private final static Map<Character, CardRank> CARD_RANKS_MAP = new ImmutableMap.Builder<Character, CardRank>()
            .put('A', CardRank.ACE)
            .put('2', CardRank.DEUCE)
            .put('3', CardRank.TREY)
            .put('4', CardRank.FOUR)
            .put('5', CardRank.FIVE)
            .put('6', CardRank.SIX)
            .put('7', CardRank.SEVEN)
            .put('8', CardRank.EIGHT)
            .put('9', CardRank.NINE)
            .put('T', CardRank.TEN)
            .put('J', CardRank.JACK)
            .put('Q', CardRank.QUEEN)
            .put('K', CardRank.KING)
            .build();

    private static CardType extractCardType(final Character type) {
        return CARD_TYPES_MAP.get(type);
    }

    private static CardRank extractCardRank(final Character rank) {
        return CARD_RANKS_MAP.get(rank);
    }

    private static CardInterface extractCard(final String card) {
        return Cards.get(extractCardType(card.charAt(1)), extractCardRank(card.charAt(0)));
    }

    private static Hand extractOneHand(final String hand) {
        final CardInterface[] cards = { null, null, null, null, null };
        int i = -1;
        for (final String card : Splitter.on(' ').split(hand)) {
            cards[++i] = extractCard(card);
        }
        return new Hand(cards);
    }

    private static Hand[] extractTwoHands(final String hands) {
        final Hand hand1 = extractOneHand(hands.substring(0, (2 + 1) * 5 - 1));
        final Hand hand2 = extractOneHand(hands.substring((2 + 1) * 5));
        return new Hand[] { hand1, hand2 };
    }

    private static int compareFirstHandWithSecondHand(final Hand[] hands) {
        return hands[0].compareTo(hands[1]);
    }

    public static void main(final String[] args) throws IOException {

        //STOP_WATCH.reset().start();

        final Integer numberOfTimesFirstHandWins = Files.readLines(
                new File("poker.txt"), Charsets.UTF_8, new LineProcessor<Integer>() {

                    private Integer numberOfTimesFirstHandWins = 0;

                    @Override
                    public boolean processLine(final String line) throws IOException {
                        if (compareFirstHandWithSecondHand(extractTwoHands(line)) == 1) {
                            ++this.numberOfTimesFirstHandWins;
                        }
                        return true;
                    }

                    @Override
                    public Integer getResult() {
                        return this.numberOfTimesFirstHandWins;
                    }
                });

        System.out.println("First hand wins " + numberOfTimesFirstHandWins + " times.");
        System.out.println("Done within " + STOP_WATCH.elapsedTime(TimeUnit.MILLISECONDS) + " ms !");

    }

    private Euler54() {}

}