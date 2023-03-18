package sk.stuba.fei.uim.oop.game.cards.brownCards;

import sk.stuba.fei.uim.oop.game.BangLite;

public class Beer extends BrownCard {
    public Beer(BangLite bangLite) {
        super(bangLite);
    }

    @Override
    public void play() {
        super.play();
        this.game.getCurrentPlayer().addLive();
    }
}
