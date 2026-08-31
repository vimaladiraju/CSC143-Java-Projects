/**
 * Represents a player in a game, a simple class not tied to any particular game or game type
 */
public class Player {
    /** Player's name */
    private String name;
    /** Player's current score */
    private int score;

    /**
     * Constructor; sets up player and gets them ready for play.  Score starts at 0.
     * @param name  player's name; must not be null or blank
     */
    public Player(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("name can't be null or blank");
        }
        this.name = name;
        this.score = 0;
    }

    /**
     * Retrieves the player's name
     * @return      player's name
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieves the current score
     * @return      current score
     */
    public int getScore() {
        return score;
    }

    /**
     * Adds to the current score, e.g., for round/hand scoring
     * @param scoreToAdd    score to add to the total
     * @return              updated current score
     */
    public int addToScore(int scoreToAdd) {
        score += scoreToAdd;
        return score;
    }
}
