
package abstraction;

import java.util.Arrays;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import abstraction.card.CardInterface;

public class DeckTest {

    private Deck deck;

    @Before
    public void setUp() throws Exception {
        this.deck = new Deck();
    }

    @After
    public void tearDown() throws Exception {
        this.deck = null;
    }

    @Test
    public void testIterator() {

        int numberOfIterations = 0;
        final Deck deckClone = new Deck();
        for (final CardInterface card : this.deck) {
            Assert.assertTrue(deckClone.draw(card).equals(card));
            ++numberOfIterations;
        }
        Assert.assertTrue(numberOfIterations == Deck.NUMBER_OF_CARDS);

    }

    @Test(expected = UnsupportedOperationException.class)
    public void remove() {
        for (@SuppressWarnings("unused")
        final CardInterface card : this.deck)
            this.deck.remove();
    }

    @Test
    public void testToArray() {

        CardInterface[] cards = new CardInterface[52];
        int n = -1;

        for (final CardInterface card : this.deck)
            cards[++n] = card;
        Assert.assertTrue(Arrays.equals(cards, this.deck.toArray()));

        for (final CardInterface card : this.deck.toArray()) {
            this.deck.draw(card);
            cards = Arrays.copyOfRange(cards, 1, cards.length);
            Assert.assertTrue(Arrays.equals(cards, this.deck.toArray()));
        }

    }

    @Test
    public void testDrawCardInterface() {

        for (final CardInterface card : this.deck.toArray()) {
            Assert.assertTrue(card.equals(this.deck.draw(card)));
            Assert.assertTrue(null == this.deck.draw(card));
        }

    }

    @Test
    public void testContains() {

        for (final CardInterface card : this.deck.toArray()) {
            Assert.assertTrue(this.deck.contains(card));
            this.deck.draw(card);
            Assert.assertFalse(this.deck.contains(card));
        }

    }

    @Test
    public void testPutBack() {

        for (final CardInterface card : this.deck.toArray()) {
            Assert.assertFalse(this.deck.contains(this.deck.draw(card)));
            this.deck.putBack(card);
            Assert.assertTrue(this.deck.contains(card));
        }

    }

    @Test
    public void testToString() {

        final StringBuilder sb = new StringBuilder();
        for (final CardInterface card : this.deck) {
            sb.append(card.toString());
            sb.append("\n");
        }
        Assert.assertTrue(sb.toString().equals(this.deck.toString()));

    }
}
