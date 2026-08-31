import java.io.*;
import java.util.*;
import javax.sound.sampled.*;

public class LuckOfTheDraw {
    public static final int WINNING_SCORE = 100;

    /**
     * Private constructor to prevent instantiation
     */
    private LuckOfTheDraw() {
    }

    /**
     * Main entry point for the applications
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        CircularLinkedList<Player> players = new CircularLinkedList<>(
                new Player("Siri"),
                new Player("Jennifer"),
                new Player("Jasper"),
                new Player("Gerald")
        );

        Deck deck = new Deck();
        deck.shuffle();

        System.out.println("New Game Starting");
        playSound("start.wav");

        var playerIterator = players.iterator();
        int randomStart = (int) (Math.random() * players.size());
        for (int i = 0; i < randomStart; i++) {
            playerIterator.next();
        }

        boolean gameRunning = true;
        Player winner = null;
        int highScore = 0;

        while (gameRunning && deck.getCardCount() >= 2) {
            System.out.println("New Round Starting");
            playSound("round_start.wav");

            for (int i = 0; i < players.size(); i++) {
                if (deck.getCardCount() < 2) {
                    gameRunning = false;
                    break;
                }

                Player player = playerIterator.next();
                Card c1 = deck.drawCard();
                Card c2 = deck.drawCard();

                int roundScore = calculateCardScore(c1) + calculateCardScore(c2);
                player.addToScore(roundScore);

                playSound("draw.wav");
                System.out.printf("%s draws these cards:%n", player.getName());
                System.out.printf("%s, %s%n", c1, c2);

                if (player.getScore() > highScore) {
                    highScore = player.getScore();
                    System.out.printf("...with a score now totaling %d...new high score!%n", player.getScore());
                } else {
                    System.out.printf("...with a score now totaling %d%n", player.getScore());
                }

                if (player.getScore() >= WINNING_SCORE) {
                    winner = player;
                    gameRunning = false;
                    break;
                }

            }
        }

        System.out.println();

        if (winner != null) {
            System.out.printf("The winner is %s with a score of %d!%n", winner.getName(), winner.getScore());
            playSound("win.wav");
        } else {
            System.out.println("Game over! Deck exhausted without a clear winner.");
            playSound("gameover.wav");
        }

    }

    /**
     * Helper method to convert card parameters to point value
     * @param card the card that has been drawn
     * @return score associated with card
     */
    private static int calculateCardScore(Card card) {
        byte num = card.number();
        if (num == 1) { // ace
            return 15;
        } else if (num >= 11) { // face cards
            return 10;
        } else { // numbered cards
            return 5;
        }
    }

    /**
     * Plays audio clip and sleeps for exact duration of the audio.
     * @param fileName file name within sounds directory
     */
    public static void playSound(String fileName) {
        File soundfile = new File("sounds/" + fileName);
        if (!soundfile.exists()) return;
        try (AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundfile)) {
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();

            long durationMillis = clip.getMicrosecondLength() / 1000;

            Thread.sleep(durationMillis);

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            return;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
