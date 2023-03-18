package sk.stuba.fei.uim.oop.game.cards;

import sk.stuba.fei.uim.oop.game.BangLite;

abstract public class Card implements Playable {
    protected final BangLite game;

    protected Card(BangLite bangLite) {
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
