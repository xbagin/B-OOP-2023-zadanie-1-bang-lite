package sk.stuba.fei.uim.oop.game.cards;

import sk.stuba.fei.uim.oop.game.BangLite;

abstract public class Card implements Playable {
    @Override
    public boolean isPlayable(BangLite bangLite) {
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
