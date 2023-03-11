package sk.stuba.fei.uim.oop.game.cards.blueCards;

import sk.stuba.fei.uim.oop.game.BangLite;
import sk.stuba.fei.uim.oop.game.cards.Card;

import java.util.Objects;
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
    public boolean isPlayable(BangLite bangLite) {
        for (BlueCard card : bangLite.getCurrentPlayer().getCardsOnTable()) {
            if (Objects.equals(card.getClass(), this.getClass())) {  // if (Objects.equals(card, this)) {
                return false;
            }
        }
        return true;
    }
}
