package sk.stuba.fei.uim.oop.game.cards.blueCards;

import sk.stuba.fei.uim.oop.game.BangLite;

public class Dynamite extends BlueCard {
    private static final int PROBABILITY_ONE_IN = 8;
    public static final int LIVES_TO_REMOVE_COUNT = 3;

    public Dynamite() {
        super(Dynamite.PROBABILITY_ONE_IN);
    }

    @Override
    public void play(BangLite bangLite) {
        super.play(bangLite);
        bangLite.getCurrentPlayer().getCardsOnTable().add(this);
    }
}
