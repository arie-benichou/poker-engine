
package gui;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.Arrays;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;

import abstraction.BestHandFinder;
import abstraction.Board;
import abstraction.PlayerHandProbabilities;
import abstraction.Probabilities;
import abstraction.card.CardInterface;
import abstraction.card.CardRank;
import abstraction.card.CardType;
import abstraction.card.Cards;

public class PokerProba extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;

    private final JPanel contentPane;
    private final ButtonGroup types = new ButtonGroup();
    private final ButtonGroup ranks = new ButtonGroup();
    private final JLabel card7;
    private final JLabel card6;
    private final JLabel card5;
    private final JLabel card4;
    private final JLabel card3;
    private final JLabel card2;
    private final JLabel card1;
    private final JLabel bestHand;
    private final JLabel probalityToWin;

    private CardType type = CardType.HEARTS;
    private CardRank rank = CardRank.DEUCE;
    private int numberOfAddedSeenCards = 0;
    private final CardInterface[] twoCards = new CardInterface[2];
    private final CardInterface[] flopCards = new CardInterface[3];
    private Board board;
    private PlayerHandProbabilities phb;
    private final JLabel score;

    /**
     * Launch the application.
     */
    public static void main(final String[] args) {
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                try {
                    final PokerProba frame = new PokerProba();
                    frame.setVisible(true);
                }
                catch (final Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public PokerProba() {
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.contentPane = new JPanel();
        this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.setContentPane(this.contentPane);
        this.contentPane.setLayout(new GridLayout(2, 1, 0, 0));

        final JPanel topPanel = new JPanel();
        this.contentPane.add(topPanel);
        topPanel.setLayout(new GridLayout(3, 1, 0, 0));

        final JPanel panel1 = new JPanel();
        topPanel.add(panel1);
        panel1.setLayout(new GridLayout(0, 4, 0, 0));

        final JRadioButton hearts = new JRadioButton("HEARTS");
        hearts.setActionCommand(CardType.HEARTS.toString());
        hearts.addActionListener(new TypeListener());
        hearts.setSelected(true);
        this.types.add(hearts);
        panel1.add(hearts);

        final JRadioButton diamonds = new JRadioButton("DIAMONDS");
        diamonds.setActionCommand(CardType.DIAMONDS.toString());
        diamonds.addActionListener(new TypeListener());
        this.types.add(diamonds);
        panel1.add(diamonds);

        final JRadioButton clubs = new JRadioButton("CLUBS");
        clubs.setActionCommand(CardType.CLUBS.toString());
        clubs.addActionListener(new TypeListener());
        this.types.add(clubs);
        panel1.add(clubs);

        final JRadioButton spades = new JRadioButton("SPADES");
        spades.setActionCommand(CardType.SPADES.toString());
        spades.addActionListener(new TypeListener());
        this.types.add(spades);
        panel1.add(spades);

        final JPanel panel2 = new JPanel();
        topPanel.add(panel2);
        panel2.setLayout(new GridLayout(0, 4, 0, 0));

        final JPanel panel = new JPanel();
        panel2.add(panel);
        panel.setLayout(new GridLayout(0, 1, 0, 0));

        final JRadioButton deuce = new JRadioButton("2");
        deuce.setActionCommand(CardRank.DEUCE.toString());
        deuce.addActionListener(new RankListener());
        deuce.setSelected(true);
        this.ranks.add(deuce);
        panel.add(deuce);

        final JRadioButton trey = new JRadioButton("3");
        trey.setActionCommand(CardRank.TREY.toString());
        trey.addActionListener(new RankListener());
        this.ranks.add(trey);
        panel.add(trey);

        final JRadioButton four = new JRadioButton("4");
        four.setActionCommand(CardRank.FOUR.toString());
        four.addActionListener(new RankListener());
        this.ranks.add(four);
        panel.add(four);

        final JRadioButton five = new JRadioButton("5");
        five.setActionCommand(CardRank.FIVE.toString());
        five.addActionListener(new RankListener());
        this.ranks.add(five);
        panel.add(five);

        final JPanel panel_1 = new JPanel();
        panel2.add(panel_1);
        panel_1.setLayout(new GridLayout(0, 1, 0, 0));

        final JRadioButton six = new JRadioButton("6");
        six.setActionCommand(CardRank.SIX.toString());
        six.addActionListener(new RankListener());
        this.ranks.add(six);
        panel_1.add(six);

        final JRadioButton seven = new JRadioButton("7");
        seven.setActionCommand(CardRank.SEVEN.toString());
        seven.addActionListener(new RankListener());
        this.ranks.add(seven);
        panel_1.add(seven);

        final JRadioButton eight = new JRadioButton("8");
        eight.setActionCommand(CardRank.EIGHT.toString());
        eight.addActionListener(new RankListener());
        this.ranks.add(eight);
        panel_1.add(eight);

        final JRadioButton nine = new JRadioButton("9");
        nine.setActionCommand(CardRank.NINE.toString());
        nine.addActionListener(new RankListener());
        this.ranks.add(nine);
        panel_1.add(nine);

        final JPanel panel_2 = new JPanel();
        panel2.add(panel_2);
        panel_2.setLayout(new GridLayout(0, 1, 0, 0));

        final JRadioButton ten = new JRadioButton("10");
        ten.setActionCommand(CardRank.TEN.toString());
        ten.addActionListener(new RankListener());
        this.ranks.add(ten);
        panel_2.add(ten);

        final JRadioButton jack = new JRadioButton("JACK");
        jack.setActionCommand(CardRank.JACK.toString());
        jack.addActionListener(new RankListener());
        this.ranks.add(jack);
        panel_2.add(jack);

        final JRadioButton queen = new JRadioButton("QUEEN");
        queen.setActionCommand(CardRank.QUEEN.toString());
        queen.addActionListener(new RankListener());
        this.ranks.add(queen);
        panel_2.add(queen);

        final JRadioButton king = new JRadioButton("KING");
        king.setActionCommand(CardRank.KING.toString());
        king.addActionListener(new RankListener());
        this.ranks.add(king);
        panel_2.add(king);

        final JPanel panel_3 = new JPanel();
        panel_3.setAlignmentY(Component.TOP_ALIGNMENT);
        panel2.add(panel_3);
        panel_3.setLayout(new GridLayout(0, 1, 0, 0));

        final JRadioButton ace = new JRadioButton("ACE");
        ace.setActionCommand(CardRank.ACE.toString());
        ace.addActionListener(new RankListener());
        this.ranks.add(ace);
        panel_3.add(ace);

        final JPanel panel3 = new JPanel();
        topPanel.add(panel3);
        panel3.setLayout(new GridLayout(0, 1, 0, 0));

        final JButton addSeenCardButton = new JButton("Ajouter une carte connue");
        addSeenCardButton.addActionListener(this);
        panel3.add(addSeenCardButton);

        final JPanel bottomPanel = new JPanel();
        this.contentPane.add(bottomPanel);
        bottomPanel.setLayout(new GridLayout(2, 1, 0, 0));

        final JPanel panel_8 = new JPanel();
        bottomPanel.add(panel_8);
        panel_8.setLayout(new GridLayout(2, 0, 0, 0));

        final JPanel panel_4 = new JPanel();
        panel_8.add(panel_4);
        panel_4.setLayout(new GridLayout(0, 5, 0, 0));

        final JPanel panel_6 = new JPanel();
        panel_4.add(panel_6);
        panel_6.setLayout(new GridLayout(0, 1, 0, 0));

        this.card1 = new JLabel("?");
        panel_6.add(this.card1);

        final JPanel panel_7 = new JPanel();
        panel_4.add(panel_7);
        panel_7.setLayout(new GridLayout(0, 1, 0, 0));

        this.card2 = new JLabel("?");
        panel_7.add(this.card2);

        final JPanel panel_14 = new JPanel();
        panel_4.add(panel_14);

        final JLabel label = new JLabel("150 < 213 < 370");
        panel_14.add(label);

        this.score = new JLabel("");
        panel_14.add(this.score);

        final JPanel panel_15 = new JPanel();
        panel_4.add(panel_15);

        final JPanel panel_16 = new JPanel();
        panel_4.add(panel_16);
        panel_16.setLayout(new GridLayout(0, 1, 0, 0));

        final JButton btnNewButton = new JButton("reset");
        btnNewButton.addActionListener(new ResetListener());
        panel_16.add(btnNewButton);

        final JPanel panel_5 = new JPanel();
        panel_8.add(panel_5);
        panel_5.setLayout(new GridLayout(0, 5, 0, 0));

        final JPanel panel_9 = new JPanel();
        panel_5.add(panel_9);
        panel_9.setLayout(new GridLayout(0, 1, 0, 0));

        this.card3 = new JLabel("?");
        panel_9.add(this.card3);

        final JPanel panel_10 = new JPanel();
        panel_5.add(panel_10);
        panel_10.setLayout(new GridLayout(0, 1, 0, 0));

        this.card4 = new JLabel("?");
        panel_10.add(this.card4);

        final JPanel panel_11 = new JPanel();
        panel_5.add(panel_11);
        panel_11.setLayout(new GridLayout(0, 1, 0, 0));

        this.card5 = new JLabel("?");
        panel_11.add(this.card5);

        final JPanel panel_12 = new JPanel();
        panel_5.add(panel_12);
        panel_12.setLayout(new GridLayout(0, 1, 0, 0));

        this.card6 = new JLabel("?");
        panel_12.add(this.card6);

        final JPanel panel_13 = new JPanel();
        panel_5.add(panel_13);
        panel_13.setLayout(new GridLayout(0, 1, 0, 0));

        this.card7 = new JLabel("?");
        panel_13.add(this.card7);

        final JPanel panel_17 = new JPanel();
        bottomPanel.add(panel_17);
        panel_17.setLayout(new GridLayout(1, 0, 0, 0));

        final JPanel panel_18 = new JPanel();
        panel_17.add(panel_18);

        this.bestHand = new JLabel("");
        panel_18.add(this.bestHand);

        this.probalityToWin = new JLabel("");
        panel_18.add(this.probalityToWin);
    }

    private final class TypeListener implements ActionListener {

        @Override
        public void actionPerformed(final ActionEvent e) {
            PokerProba.this.type = CardType.valueOf(e.getActionCommand());
        }

    }

    private final class RankListener implements ActionListener {

        @Override
        public void actionPerformed(final ActionEvent e) {
            PokerProba.this.rank = CardRank.valueOf(e.getActionCommand());
        }

    }

    private final class ResetListener implements ActionListener { // TODO méthode de reset

        @Override
        public void actionPerformed(final ActionEvent e) {
            PokerProba.this.card1.setText("?");
            PokerProba.this.card2.setText("?");
            PokerProba.this.card3.setText("?");
            PokerProba.this.card4.setText("?");
            PokerProba.this.card5.setText("?");
            PokerProba.this.card6.setText("?");
            PokerProba.this.card7.setText("?");
            PokerProba.this.bestHand.setText("");
            PokerProba.this.probalityToWin.setText("");
            PokerProba.this.score.setText("");
            PokerProba.this.numberOfAddedSeenCards = 0;
        }

    }

    @Override
    public void actionPerformed(final ActionEvent e) {

        final CardInterface seenCard = Cards.get(this.type, this.rank);

        if (++this.numberOfAddedSeenCards == 8) { // TODO méthode de reset
            PokerProba.this.card1.setText("?");
            PokerProba.this.card2.setText("?");
            PokerProba.this.card3.setText("?");
            PokerProba.this.card4.setText("?");
            PokerProba.this.card5.setText("?");
            PokerProba.this.card6.setText("?");
            PokerProba.this.card7.setText("?");
            PokerProba.this.bestHand.setText("");
            PokerProba.this.probalityToWin.setText("");
            PokerProba.this.score.setText("");
            this.numberOfAddedSeenCards = 1;
        }

        switch (this.numberOfAddedSeenCards) {

            case 1:
                this.twoCards[0] = seenCard;
                PokerProba.this.card1.setText(seenCard.toString());
                break;

            case 2:
                this.twoCards[1] = seenCard;
                PokerProba.this.card2.setText(seenCard.toString());

                this.board = new Board();
                //this.board.add(this.flopCards);
                this.phb = Probabilities.from(this.board, this.twoCards);
                PokerProba.this.score.setText(this.phb.twoCardsScore + " points");
                break;

            case 3:
                this.flopCards[0] = seenCard;
                PokerProba.this.card3.setText(seenCard.toString());
                break;

            case 4:
                this.flopCards[1] = seenCard;
                PokerProba.this.card4.setText(seenCard.toString());
                break;

            case 5:
                this.flopCards[2] = seenCard;
                PokerProba.this.card5.setText(seenCard.toString());
                this.board.add(this.flopCards);
                this.phb = Probabilities.from(this.board, this.twoCards);
                PokerProba.this.probalityToWin.setText(new DecimalFormat("#.###").format(100 * this.phb.computeVictoryProbability()) + " %");
                break;

            case 6:
                this.board.add(seenCard);
                PokerProba.this.card6.setText(seenCard.toString());
                this.phb = Probabilities.from(this.board, this.twoCards);
                PokerProba.this.probalityToWin.setText(new DecimalFormat("#.###").format(100 * this.phb.computeVictoryProbability()) + " %");
                break;

            case 7:
                this.board.add(seenCard);
                PokerProba.this.card7.setText(seenCard.toString());
                final CardInterface[] cards = Arrays.copyOf(this.board.toArray(), 7);
                cards[5] = this.twoCards[0];
                cards[6] = this.twoCards[1];

                PokerProba.this.bestHand.setText("<HTML>" + BestHandFinder.from(cards).toString().replace("\n", "<br>") + "</HTML>");
                this.phb = Probabilities.from(this.board, this.twoCards);
                PokerProba.this.probalityToWin.setText(new DecimalFormat("#.###").format(100 * this.phb.computeVictoryProbability()) + " %");
        }
    }
}