package org.eiti.java.pang.game;

/**
 * Wpis na liście najlepszych wyników
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
