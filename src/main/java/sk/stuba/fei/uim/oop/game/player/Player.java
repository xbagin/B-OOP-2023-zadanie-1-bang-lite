package sk.stuba.fei.uim.oop.game.player;

public class Player {
    private final String name;
    private int lives;

    private static final int START_LIVES_COUNT = 4;

    public Player(String name) {
        this.name = name;
        this.lives = Player.START_LIVES_COUNT;
    }

    public boolean isAlive() {
        return this.lives > 0;
    }

    public String getName() {
        return this.name;
    }
}
