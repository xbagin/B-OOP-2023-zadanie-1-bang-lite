package sk.stuba.fei.uim.oop.game.cards;

public interface Playable {
    void play();
    boolean isPlayable();
    boolean requireTargetPlayer();
    boolean requireTargetPlayerDeck();
}
