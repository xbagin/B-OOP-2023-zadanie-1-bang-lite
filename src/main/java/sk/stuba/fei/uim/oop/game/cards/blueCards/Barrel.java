package sk.stuba.fei.uim.oop.game.cards.blueCards;

import sk.stuba.fei.uim.oop.game.BangLite;
import sk.stuba.fei.uim.oop.game.cards.Card;

import java.util.Objects;

public class Barrel extends BlueCard {
    private static final int PROBABILITY_ONE_IN = 4;

    public Barrel(BangLite bangLite) {
        super(Barrel.PROBABILITY_ONE_IN, bangLite);
    }

    @Override
    public boolean isPlayable() {
        for (Card card : this.game.getCurrentPlayer().getCardsOnTable()) {
            if (Objects.equals(card.getClass().getSimpleName(), this.getClass().getSimpleName())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void play() {
        super.play();
        this.game.getCurrentPlayer().getCardsOnTable().add(this);
    }
}
