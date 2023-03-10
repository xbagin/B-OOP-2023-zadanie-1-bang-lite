package sk.stuba.fei.uim.oop.game.cards.brownCards;

import sk.stuba.fei.uim.oop.game.BangLite;

public class Stagecoach extends BrownCard {
    private static final int CARDS_TO_DRAW_COUNT = 2;

    @Override
    public void play(BangLite bangLite) {
        super.play(bangLite);
        bangLite.getCurrentPlayer().drawCards(Stagecoach.CARDS_TO_DRAW_COUNT, bangLite.getDeck());
    }
}
