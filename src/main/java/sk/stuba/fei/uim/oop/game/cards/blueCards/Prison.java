package sk.stuba.fei.uim.oop.game.cards.blueCards;

import sk.stuba.fei.uim.oop.game.BangLite;
import sk.stuba.fei.uim.oop.game.cards.Card;

import java.util.Objects;

public class Prison extends BlueCard {
    private static final int PROBABILITY_ONE_IN = 4;

    public Prison() {
        super(Prison.PROBABILITY_ONE_IN);
    }

    @Override
    public void play(BangLite bangLite) {
        super.play(bangLite);
        bangLite.getTargetPlayer().getCardsOnTable().add(this);
    }

    @Override
    public boolean requireTargetPlayer() {
        return true;
    }

    @Override
    public boolean isPlayable(BangLite bangLite) {
        for (Card card : bangLite.getTargetPlayer().getCardsOnTable()) {
            if (Objects.equals(card.getClass().getSimpleName(), this.getClass().getSimpleName())) {
                return false;
            }
        }
        return true;
    }

}
