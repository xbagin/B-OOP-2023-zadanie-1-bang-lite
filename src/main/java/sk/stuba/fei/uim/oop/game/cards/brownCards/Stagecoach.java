package sk.stuba.fei.uim.oop.game.cards.brownCards;

import sk.stuba.fei.uim.oop.game.GameValues;

public class Stagecoach extends BrownCard {
    private static final int CARDS_TO_DRAW_COUNT = 2;

    public Stagecoach(GameValues bangLite) {
        super(bangLite);
    }

    @Override
    public void play() {
        super.play();
        this.game.getCurrentPlayer().drawCards(Stagecoach.CARDS_TO_DRAW_COUNT, this.game);
    }
}
