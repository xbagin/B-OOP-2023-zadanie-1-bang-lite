package sk.stuba.fei.uim.oop.game.cards;

import sk.stuba.fei.uim.oop.game.GameValues;

abstract public class Card implements Playable {
    protected final GameValues game;

    protected Card(GameValues bangLite) {
        this.game = bangLite;
    }

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
