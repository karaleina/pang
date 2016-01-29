package org.eiti.java.pang.game;

/**
 * Class tht represents entry in best scores XML document; entry consist of score and nickname
 */
public class HighScoreEntry {

    private String nickname;
    private int score;

    public HighScoreEntry(String nickname, int score) {
        this.nickname = nickname;
        this.score = score;
    }

    public String getNickname() {
        return nickname;
    }

    public int getScore() {
        return score;
    }
}
