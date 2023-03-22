package sk.stuba.fei.uim.oop.game.cards.brownCards;

import sk.stuba.fei.uim.oop.game.BangLite;
import sk.stuba.fei.uim.oop.game.GameValues;

public class Bang extends BrownCard {
    public Bang(GameValues bangLite) {
        super(bangLite);
    }

    @Override
    public void play() {
        super.play();
        if (this.game.getTargetPlayer().dealWithBang()) {
            return;
        }
        this.game.getCurrentPlayer().drawCards(BangLite.CARDS_TO_DRAW_WHEN_KILL_COUNT, this.game);
    }

    @Override
    public boolean requireTargetPlayer() {
        return true;
    }
}
