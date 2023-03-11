package sk.stuba.fei.uim.oop.game.cards.blueCards;

import sk.stuba.fei.uim.oop.game.BangLite;

public class Prison extends BlueCard {
    private static final int PROBABILITY_ONE_IN = 4;

    public Prison() {
        super(Prison.PROBABILITY_ONE_IN);
    }

    @Override
    public void play(BangLite bangLite) {
        bangLite.getTargetPlayer().getCardsOnTable().add(this);
    }

    @Override
    public boolean requireTargetPlayer() {
        return true;
    }
}
