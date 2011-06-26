
package abstraction;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import abstraction.card.CardInterface;
import abstraction.card.CardRank;
import abstraction.card.CardType;
import abstraction.card.Cards;

public class BoardTest {

    private Board board;

    @Before
    public void setUp() throws Exception {
        this.board = new Board();
    }

    @After
    public void tearDown() throws Exception {
        this.board = null;
    }

    @Test
    public void testAddNone() {
        final CardInterface[] cards = new CardInterface[0];
        final BoardTimeLine previousBoardTimeLine = this.board.getTimeLine();
        Assert.assertTrue(this.board.getTimeLine().equals(BoardTimeLine.PREFLOP));
        Assert.assertTrue(this.board.add(cards).equals(previousBoardTimeLine));
    }

    @Test(expected = RuntimeException.class)
    public void testAddNotEnough1() {
        Assert.assertTrue(this.board.getTimeLine().equals(BoardTimeLine.PREFLOP));
        this.board.add(Cards.get(CardType.HEARTS, CardRank.ACE));
    }

    @Test(expected = RuntimeException.class)
    public void testAddNotEnough2() {
        Assert.assertTrue(this.board.getTimeLine().equals(BoardTimeLine.PREFLOP));
        this.board.add(Cards.get(CardType.HEARTS, CardRank.ACE), Cards.get(CardType.SPADES, CardRank.ACE));
    }

    @Test
    public void testAdd() {
        Assert.assertTrue(this.board.getTimeLine().equals(BoardTimeLine.PREFLOP));
        Assert.assertTrue(this.board.add(
                Cards.get(CardType.HEARTS, CardRank.ACE),
                Cards.get(CardType.SPADES, CardRank.ACE),
                Cards.get(CardType.DIAMONDS, CardRank.ACE))
                .equals(BoardTimeLine.FLOP)
                );
        Assert.assertTrue(this.board.add(Cards.get(CardType.CLUBS, CardRank.ACE)).equals(BoardTimeLine.TURN));
        Assert.assertTrue(this.board.add(Cards.get(CardType.HEARTS, CardRank.KING)).equals(BoardTimeLine.RIVER));
    }

    @Test(expected = RuntimeException.class)
    public void testAddTooMuch() {
        this.board.add(
                Cards.get(CardType.HEARTS, CardRank.ACE),
                Cards.get(CardType.SPADES, CardRank.ACE),
                Cards.get(CardType.DIAMONDS, CardRank.ACE),
                Cards.get(CardType.CLUBS, CardRank.ACE),
                Cards.get(CardType.HEARTS, CardRank.KING),
                Cards.get(CardType.HEARTS, CardRank.KING),
                Cards.get(CardType.SPADES, CardRank.KING)
                );
    }

    @Test
    public void testGetTimeLine() {
        Assert.assertTrue(this.board.getTimeLine().equals(BoardTimeLine.PREFLOP));

        this.board.add(
                Cards.get(CardType.HEARTS, CardRank.ACE),
                Cards.get(CardType.SPADES, CardRank.ACE),
                Cards.get(CardType.DIAMONDS, CardRank.ACE)
                );
        Assert.assertTrue(this.board.getTimeLine().equals(BoardTimeLine.FLOP));

        this.board.add(Cards.get(CardType.CLUBS, CardRank.ACE));
        Assert.assertTrue(this.board.getTimeLine().equals(BoardTimeLine.TURN));

        this.board.add(Cards.get(CardType.HEARTS, CardRank.KING));
        Assert.assertTrue(this.board.getTimeLine().equals(BoardTimeLine.RIVER));
    }

    @Test
    public void testToArray() {
        Assert.assertTrue(this.board.toArray().length == 0);

        this.board.add(
                Cards.get(CardType.HEARTS, CardRank.ACE),
                Cards.get(CardType.SPADES, CardRank.ACE),
                Cards.get(CardType.DIAMONDS, CardRank.ACE)
                );
        Assert.assertTrue(this.board.toArray().length == 3);

        this.board.add(Cards.get(CardType.CLUBS, CardRank.ACE));
        Assert.assertTrue(this.board.toArray().length == 4);

        this.board.add(Cards.get(CardType.HEARTS, CardRank.KING));
        Assert.assertTrue(this.board.toArray().length == 5);

        try {
            this.board.add(Cards.get(CardType.SPADES, CardRank.KING));
        }
        catch (final RuntimeException e) {} // TODO utiliser une BoardException
        finally {
            Assert.assertTrue(this.board.getTimeLine().equals(BoardTimeLine.RIVER));
            Assert.assertTrue(this.board.toArray().length == 5);
        }

    }
}
