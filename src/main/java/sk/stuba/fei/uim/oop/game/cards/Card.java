package sk.stuba.fei.uim.oop.game.cards;

abstract public class Card implements Playable {
    @Override
    public boolean isPlayable() {
        return true;
    }

    @Override
    public boolean requireTargetPlayer() {
        return false;
    }

    @Override
    public boolean requireTargetPlayerDeck() {
        return false;
    }
}
