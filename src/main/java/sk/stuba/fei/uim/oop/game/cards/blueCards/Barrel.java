package sk.stuba.fei.uim.oop.game.cards.blueCards;

import sk.stuba.fei.uim.oop.game.BangLite;
import sk.stuba.fei.uim.oop.game.cards.Card;

import java.util.Objects;

public class Barrel extends BlueCard {
    private static final int PROBABILITY_ONE_IN = 4;

    public Barrel() {
        super(Barrel.PROBABILITY_ONE_IN);
    }

    @Override
    public void play(BangLite bangLite) {
        super.play(bangLite);
        bangLite.getCurrentPlayer().getCardsOnTable().add(this);
    }

    @Override
    public boolean isPlayable(BangLite bangLite) {
        for (Card card : bangLite.getCurrentPlayer().getCardsOnTable()) {
            if (Objects.equals(card.getClass().getSimpleName(), this.getClass().getSimpleName())) {
                return false;
            }
        }
        return true;
    }

}
