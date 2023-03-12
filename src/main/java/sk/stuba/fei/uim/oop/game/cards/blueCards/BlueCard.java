package sk.stuba.fei.uim.oop.game.cards.blueCards;

import sk.stuba.fei.uim.oop.game.BangLite;
import sk.stuba.fei.uim.oop.game.cards.Card;

import java.util.Random;

public abstract class BlueCard extends Card {
    protected final int probabilityOneIn;
    private final Random chance;

    public BlueCard(int probabilityOneIn) {
        this.chance = new Random();
        this.probabilityOneIn = probabilityOneIn;
    }

    public boolean hasEffect() {
        return this.chance.nextInt(this.probabilityOneIn) == 0;
    }

    @Override
    public void play(BangLite bangLite) {
        bangLite.getCurrentPlayer().getCardsInHand().remove(this);
    }
}
