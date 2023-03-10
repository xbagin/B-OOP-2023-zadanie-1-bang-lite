package sk.stuba.fei.uim.oop.game.cards.blueCards;

import sk.stuba.fei.uim.oop.game.BangLite;

public class Dynamite extends BlueCard {
    private static final int PROBABILITY_ONE_IN = 8;

    public Dynamite() {
        super(Dynamite.PROBABILITY_ONE_IN);
    }

    @Override
    public void play(BangLite bangLite) {
        bangLite.getCurrentPlayer().getCardsOnTable().add(this);
        //this.hasEffect();
    }
}
