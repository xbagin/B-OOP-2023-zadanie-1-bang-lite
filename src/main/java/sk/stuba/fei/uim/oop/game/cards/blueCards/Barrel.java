package sk.stuba.fei.uim.oop.game.cards.blueCards;

import sk.stuba.fei.uim.oop.game.BangLite;

public class Barrel extends BlueCard {
    private static final int PROBABILITY_ONE_IN = 4;

    public Barrel() {
        super(Barrel.PROBABILITY_ONE_IN);
    }

    @Override
    public void play(BangLite bangLite) {
        bangLite.getCurrentPlayer().getCardsOnTable().add(this);
    }
}
