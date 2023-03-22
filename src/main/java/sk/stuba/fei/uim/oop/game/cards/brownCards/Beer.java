package sk.stuba.fei.uim.oop.game.cards.brownCards;

import sk.stuba.fei.uim.oop.game.GameValues;

public class Beer extends BrownCard {
    public Beer(GameValues bangLite) {
        super(bangLite);
    }

    @Override
    public void play() {
        super.play();
        this.game.getCurrentPlayer().addLive();
    }
}
