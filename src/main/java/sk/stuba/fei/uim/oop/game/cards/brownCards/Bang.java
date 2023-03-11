package sk.stuba.fei.uim.oop.game.cards.brownCards;

import sk.stuba.fei.uim.oop.game.BangLite;

public class Bang extends BrownCard {
    @Override
    public void play(BangLite bangLite) {
        super.play(bangLite);
        if (bangLite.getTargetPlayer().dealWithBang(bangLite)) {
            return;
        }
        bangLite.getCurrentPlayer().drawCards(BangLite.CARDS_TO_DRAW_WHEN_KILL_COUNT, bangLite.getDeck());
    }

    @Override
    public boolean requireTargetPlayer() {
        return true;
    }
}
