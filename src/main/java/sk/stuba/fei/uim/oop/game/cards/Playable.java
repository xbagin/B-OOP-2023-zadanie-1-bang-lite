package sk.stuba.fei.uim.oop.game.cards;

import sk.stuba.fei.uim.oop.game.BangLite;

public interface Playable {
    void play(BangLite bangLite);
    boolean isPlayable(BangLite bangLite);
}
