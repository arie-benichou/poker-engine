
package abstraction.hand;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import abstraction.card.CardInterface;
import abstraction.card.CardRank;
import abstraction.card.CardType;
import abstraction.card.Cards;

public class HandTest {

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalHandWithLessThanFiveCards() {
        final CardInterface[] cards = {
                Cards.get(CardType.HEARTS, CardRank.ACE),
                Cards.get(CardType.HEARTS, CardRank.DEUCE),
                Cards.get(CardType.HEARTS, CardRank.TREY),
                Cards.get(CardType.HEARTS, CardRank.FOUR)
                };
        new Hand(cards);
    }

    @Test
    public void testLegalHandWithFiveCards() {
        final CardInterface[] cards = {
                Cards.get(CardType.HEARTS, CardRank.ACE),
                Cards.get(CardType.HEARTS, CardRank.DEUCE),
                Cards.get(CardType.HEARTS, CardRank.TREY),
                Cards.get(CardType.HEARTS, CardRank.FOUR),
                Cards.get(CardType.HEARTS, CardRank.FIVE)
                };
        new Hand(cards);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalHandWithMoreThanFiveCards() {
        final CardInterface[] cards = {
                Cards.get(CardType.HEARTS, CardRank.ACE),
                Cards.get(CardType.HEARTS, CardRank.DEUCE),
                Cards.get(CardType.HEARTS, CardRank.TREY),
                Cards.get(CardType.HEARTS, CardRank.FOUR),
                Cards.get(CardType.HEARTS, CardRank.FIVE),
                Cards.get(CardType.HEARTS, CardRank.SIX)
                };
        new Hand(cards);
    }

    /*
    @Test
    public void testGetRankProduct() {
        final CardInterface[] cards = {
                Cards.get(CardType.HEARTS, CardRank.ACE),
                Cards.get(CardType.HEARTS, CardRank.DEUCE),
                Cards.get(CardType.HEARTS, CardRank.TREY),
                Cards.get(CardType.HEARTS, CardRank.FOUR),
                Cards.get(CardType.HEARTS, CardRank.FIVE)
                };
        final Hand hand = new Hand(cards);
        final int rankProduct = CardRank.ACE.getValue() * CardRank.DEUCE.getValue() * CardRank.TREY.getValue() * CardRank.FOUR.getValue()
                * CardRank.FIVE.getValue();
        assertTrue(hand.getRankProduct() == rankProduct);
    }
    */

    /*
    @Test
    public void testIsMadeOfSameType() {
        final CardInterface[] cardsMadeOfDifferentTypes = {
                Cards.get(CardType.DIAMONDS, CardRank.ACE),
                Cards.get(CardType.HEARTS, CardRank.DEUCE),
                Cards.get(CardType.HEARTS, CardRank.TREY),
                Cards.get(CardType.HEARTS, CardRank.FOUR),
                Cards.get(CardType.HEARTS, CardRank.FIVE)
                };
        assertFalse(new Hand(cardsMadeOfDifferentTypes).isMadeOfSameType());
        final CardInterface[] cardsMadeOfSameType = {
                Cards.get(CardType.HEARTS, CardRank.ACE),
                Cards.get(CardType.HEARTS, CardRank.DEUCE),
                Cards.get(CardType.HEARTS, CardRank.TREY),
                Cards.get(CardType.HEARTS, CardRank.FOUR),
                Cards.get(CardType.HEARTS, CardRank.FIVE)
                };
        assertTrue(new Hand(cardsMadeOfSameType).isMadeOfSameType());
    }
    */

    /*
    @Test
    public void testIsSequence() {
        final CardInterface[] fiveCardsWithNoConsecutiveRanks = {
                Cards.get(CardType.HEARTS, CardRank.DEUCE),
                Cards.get(CardType.SPADES, CardRank.TREY),
                Cards.get(CardType.DIAMONDS, CardRank.FOUR),
                Cards.get(CardType.CLUBS, CardRank.FIVE),
                Cards.get(CardType.HEARTS, CardRank.SEVEN),
                };
        assertFalse(new Hand(fiveCardsWithNoConsecutiveRanks).isSequence());
        final CardInterface[] fiveCardsWithConsecutiveRanks = {
                Cards.get(CardType.HEARTS, CardRank.SIX),
                Cards.get(CardType.HEARTS, CardRank.DEUCE),
                Cards.get(CardType.SPADES, CardRank.FOUR),
                Cards.get(CardType.DIAMONDS, CardRank.TREY),
                Cards.get(CardType.CLUBS, CardRank.FIVE)
                };
        assertTrue(new Hand(fiveCardsWithConsecutiveRanks).isSequence());
    }
    */

    @Test
    public void testGetType() {
        final CardInterface[] cards = {
                Cards.get(CardType.HEARTS, CardRank.SIX),
                Cards.get(CardType.HEARTS, CardRank.DEUCE),
                Cards.get(CardType.SPADES, CardRank.FOUR),
                Cards.get(CardType.DIAMONDS, CardRank.TREY),
                Cards.get(CardType.CLUBS, CardRank.FIVE)
                };
        assertTrue(new Hand(cards).getType().equals(HandType.Straight));
    }

    @Test
    public void testCompareTo() {
        final CardInterface[] straight = {
                Cards.get(CardType.HEARTS, CardRank.SIX),
                Cards.get(CardType.HEARTS, CardRank.DEUCE),
                Cards.get(CardType.SPADES, CardRank.FOUR),
                Cards.get(CardType.DIAMONDS, CardRank.TREY),
                Cards.get(CardType.CLUBS, CardRank.FIVE)
                };
        final Hand hand1 = new Hand(straight);
        final CardInterface[] straightFlush = {
                Cards.get(CardType.HEARTS, CardRank.SIX),
                Cards.get(CardType.HEARTS, CardRank.DEUCE),
                Cards.get(CardType.HEARTS, CardRank.FOUR),
                Cards.get(CardType.HEARTS, CardRank.TREY),
                Cards.get(CardType.HEARTS, CardRank.FIVE)
                };
        final Hand hand2 = new Hand(straightFlush);
        assertTrue(hand1.compareTo(hand2) == -1);
        assertTrue(hand1.compareTo(hand1) == 0);
        assertTrue(hand2.compareTo(hand2) == 0);
        assertTrue(hand2.compareTo(hand1) == 1);
    }

    @Test
    public void testToString() {
        final CardInterface[] cards = {
                Cards.get(CardType.HEARTS, CardRank.ACE),
                Cards.get(CardType.HEARTS, CardRank.KING),
                Cards.get(CardType.CLUBS, CardRank.KING),
                Cards.get(CardType.SPADES, CardRank.KING),
                Cards.get(CardType.DIAMONDS, CardRank.KING)
                };
        final String expectedString = "KING of HEARTS    final KING of CLUBS    final KING of SPADES    final KING of DIAMONDS    final ACE of HEARTS    "
                + "\n" +
                "FourOfAKind :" + "\n" +
                " - FourOfAKind(KING)" + "\n" +
                " - HighCard(ACE)" + "\n";
        new Hand(cards).toString().equals(expectedString);
    }
}
