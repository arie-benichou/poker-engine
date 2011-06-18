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

// TODO à documenter
import abstraction.card.CardInterface;
import abstraction.card.CardRank;
import abstraction.card.CardType;
import abstraction.card.Cards;
import abstraction.hand.Hand;

// TODO pouvoir gérer le preflop, flop et turn
// TODO avoir un objet Board
final class Probabilities {

    private final static int LOWER = -1;
    private final static int GREATER = 1;

    private final static int NUMBER_OF_CARDS_NOT_SEEN = Deck.NUMBER_OF_CARDS
            - (BestHandFinder.NUMBER_OF_CARDS_BY_PLAYER + BestHandFinder.NUMBER_OF_CARDS_IN_FLOP);

    private final static Combinations COMBINATIONS = new Combinations(NUMBER_OF_CARDS_NOT_SEEN, BestHandFinder.NUMBER_OF_CARDS_BY_PLAYER);
    private final static int[][] NUMBER_OF_COMBINATIONS_FOR_45_ELEMENTS_TAKEN_BY_2 = new int[COMBINATIONS.getNumberOfCombinations().intValue()][1];
    static {
        int i = -1;
        while (COMBINATIONS.hasNext())
            NUMBER_OF_COMBINATIONS_FOR_45_ELEMENTS_TAKEN_BY_2[++i] = COMBINATIONS.next();
    }

    private final static CardInterface[] BEST_CARDS = new CardInterface[5];
    static {
        BEST_CARDS[0] = Cards.get(CardType.HEARTS, CardRank.ACE);
        BEST_CARDS[1] = Cards.get(CardType.HEARTS, CardRank.KING);
        BEST_CARDS[2] = Cards.get(CardType.HEARTS, CardRank.QUEEN);
        BEST_CARDS[3] = Cards.get(CardType.HEARTS, CardRank.JACK);
        BEST_CARDS[4] = Cards.get(CardType.HEARTS, CardRank.TEN);
    }
    private final static Hand BEST_HAND = new Hand(BEST_CARDS);

    private Probabilities() {}

    // TODO interpréter et retouner les données
    public static void from(final CardInterface[] twoCards, final CardInterface[] fiveCards, final Hand bestHand) {
        final long t0 = System.currentTimeMillis();
        int numberOfLowerBestHands = 0;
        int numberOfGreaterBestHands = 0;
        int numberOfEquivalentBestHands = 0;
        Hand minimalGreaterBestHand = BEST_HAND; // TODO avoir une liste des plus petites meilleures meilleures mains possibles
        final Deck deck = new Deck(false);
        for (final CardInterface seenCard : twoCards)
            deck.draw(seenCard);
        for (final CardInterface seenCard : fiveCards)
            deck.draw(seenCard);
        final CardInterface[] cards = deck.toArray();
        for (final int[] combination : NUMBER_OF_COMBINATIONS_FOR_45_ELEMENTS_TAKEN_BY_2) {
            final Hand currentHand = BestHandFinder.from(cards[combination[0]], cards[combination[1]], fiveCards);
            switch (currentHand.compareTo(bestHand)) {
                case LOWER:
                    ++numberOfLowerBestHands;
                    break;
                case GREATER:
                    ++numberOfGreaterBestHands;
                    if (minimalGreaterBestHand.compareTo(currentHand) == 1)
                        minimalGreaterBestHand = currentHand;
                    break;
                default:
                    ++numberOfEquivalentBestHands;
            }
        }
        final long t1 = System.currentTimeMillis();
        System.out.println("Probabilités calculées en " + (t1 - t0) + " ms\n");
        System.out.println("Nombre de meilleures meilleures mains possibles   : " + numberOfGreaterBestHands);
        System.out.println("Nombre de moins bonnes meilleures mains possibles : " + numberOfLowerBestHands);
        System.out.println("Nombre de meilleures mains équivalentes possibles : " + numberOfEquivalentBestHands);
        System.out.println("\nTotal : " + (numberOfGreaterBestHands + numberOfLowerBestHands + numberOfEquivalentBestHands) + "\n");

        if (numberOfGreaterBestHands > 0)
            System.out.println("Exemple d'une plus petite meilleure meilleure main possible :\n" + minimalGreaterBestHand);
        /* TODO
        if (numberOfEquivalentBestHands > 0)
            System.out.println("Exemple d'une meilleure main équivalente possible : " + );
        */
        else
            System.out.println("Victoire garantie.");

    }

    public static void main(final String[] args) {
        //System.out.println(NUMBER_OF_COMBINATIONS_FOR_45_ELEMENTS_TAKEN_BY_2.length);
        //test1();
        test2();
    }

    public static void test1() {
        final Deck deck = new Deck();
        final CardInterface[] twoCards = new CardInterface[2];
        final CardInterface[] fiveCards = new CardInterface[5];
        for (int i = 0; i < 2; ++i)
            twoCards[i] = deck.draw();
        for (final CardInterface card : twoCards)
            System.out.print(card + "    ");
        for (int i = 0; i < 5; ++i)
            fiveCards[i] = deck.draw();
        System.out.println();
        for (final CardInterface card : fiveCards)
            System.out.print(card + "    ");
        System.out.println();
        final Hand bestHand = BestHandFinder.from(twoCards, fiveCards);
        System.out.println("\nMeilleure main possible :\n" + bestHand);
        Probabilities.from(twoCards, fiveCards, bestHand);
    }

    private static void test2() {
        final CardInterface[] twoCards = {
                Cards.get(CardType.CLUBS, CardRank.DEUCE),
                Cards.get(CardType.DIAMONDS, CardRank.FIVE)
                };

        final CardInterface[] fiveCards = {
                Cards.get(CardType.DIAMONDS, CardRank.EIGHT),
                Cards.get(CardType.HEARTS, CardRank.DEUCE),
                Cards.get(CardType.HEARTS, CardRank.JACK),
                Cards.get(CardType.DIAMONDS, CardRank.SIX),
                Cards.get(CardType.HEARTS, CardRank.EIGHT)
                };

        final Hand bestHand = BestHandFinder.from(twoCards, fiveCards);
        System.out.println(bestHand);
        Probabilities.from(twoCards, fiveCards, bestHand);
    }

    /*
    QUEEN of DIAMONDS    KING of SPADES    
    ACE of SPADES    TREY of SPADES    TREY of DIAMONDS    EIGHT of SPADES    JACK of SPADES    

    Meilleure main possible :
    Flush :
    - ACE of SPADES
    - TREY of SPADES
    - EIGHT of SPADES
    - JACK of SPADES
    - KING of SPADES

    Probabilités calculées en 54 ms

    Nombre de meilleures meilleures mains possibles   : 28
    Nombre de moins bonnes meilleures mains possibles : 962
    Nombre de meilleures mains équivalentes possibles : 0

    Total : 990

    Exemple d'une plus petite meilleure meilleure main possible :
    FullHouse :
    - TREY of SPADES
    - TREY of DIAMONDS
    - EIGHT of SPADES
    - TREY of HEARTS
    - EIGHT of HEARTS
     */

}
