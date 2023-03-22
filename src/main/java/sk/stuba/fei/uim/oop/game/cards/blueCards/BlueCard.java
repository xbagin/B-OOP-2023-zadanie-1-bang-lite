package sk.stuba.fei.uim.oop.game.cards.blueCards;

import sk.stuba.fei.uim.oop.game.GameValues;
import sk.stuba.fei.uim.oop.game.cards.Card;

import java.util.List;
import java.util.Random;

public abstract class BlueCard extends Card {
    private final int probabilityOneIn;
    private final Random chance;

    public BlueCard(int probabilityOneIn, GameValues bangLite) {
        super(bangLite);
        this.probabilityOneIn = probabilityOneIn;
        this.chance = new Random();
    }

    public boolean hasEffect() {
        return this.chance.nextInt(this.probabilityOneIn) == 0;
    }

    public boolean applyEffect(List<Card> toRemove) {
        return true;
    }

    @Override
    public void play() {
        this.game.getCurrentPlayer().getCardsInHand().remove(this);
    }
}
