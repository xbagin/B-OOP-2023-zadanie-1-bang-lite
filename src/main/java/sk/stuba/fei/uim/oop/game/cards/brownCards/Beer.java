package sk.stuba.fei.uim.oop.game.cards.brownCards;

import sk.stuba.fei.uim.oop.game.BangLite;

public class Beer extends BrownCard {
    @Override
    public void play(BangLite bangLite) {
        super.play(bangLite);
        bangLite.getCurrentPlayer().addLive();
    }
}
