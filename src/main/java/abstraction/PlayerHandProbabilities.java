
package abstraction;

import java.text.DecimalFormat;

// Cet objet doit être crée en interne
public final class PlayerHandProbabilities { // TODO ? renommer en PlayerGameData

    public int twoCardsScore; // TODO utiliser un autre objet et modifier Probabilities

    private int numberOfTimesPlayerBestHandIsWinning = 0;
    private int numberOfTimesPlayerBestHandIsLoosing = 0;
    private int numberOfTimesPlayerBestHandIsDraw = 0;

    public void incrementVictories() {
        ++this.numberOfTimesPlayerBestHandIsWinning;
    }

    public void incrementDefeats() {
        ++this.numberOfTimesPlayerBestHandIsLoosing;
    }

    public void incrementDraws() {
        ++this.numberOfTimesPlayerBestHandIsDraw;
    }

    public double computeVictoryProbability() {
        return (this.numberOfTimesPlayerBestHandIsWinning + this.numberOfTimesPlayerBestHandIsDraw / 2.0)
                / (this.numberOfTimesPlayerBestHandIsLoosing + this.numberOfTimesPlayerBestHandIsDraw + this.numberOfTimesPlayerBestHandIsWinning);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Number of times player's best hand is winning : " + /*df.format*/this.numberOfTimesPlayerBestHandIsWinning + "\n");
        sb.append("Number of times player's best hand is loosing : " + /*df.format*/this.numberOfTimesPlayerBestHandIsLoosing + "\n");
        sb.append("Number of times player's best hand is a draw  : " + /*df.format*/this.numberOfTimesPlayerBestHandIsDraw + "\n");
        sb.append("\n");
        sb.append("Victory probability: " + new DecimalFormat("#.###").format(100 * this.computeVictoryProbability()) + " %");
        return sb.toString();
    }
}
