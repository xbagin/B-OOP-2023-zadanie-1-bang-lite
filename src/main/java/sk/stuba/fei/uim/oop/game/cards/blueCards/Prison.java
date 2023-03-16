package sk.stuba.fei.uim.oop.game.cards.blueCards;

import sk.stuba.fei.uim.oop.game.cards.Card;
import sk.stuba.fei.uim.oop.game.player.Player;

import java.util.Objects;

public class Prison extends BlueCard {
    private static final int PROBABILITY_ONE_IN = 4;

    public Prison(Player targetPlayer) {
        super(Prison.PROBABILITY_ONE_IN, targetPlayer);
    }

    @Override
    public void play() {
        super.play();
        this.player.getCardsOnTable().add(this);
    }

    @Override
    public boolean requireTargetPlayer() {
        return true;
    }

    @Override
    public boolean isPlayable() {
        for (Card card : this.player.getCardsOnTable()) {
            if (Objects.equals(card.getClass().getSimpleName(), this.getClass().getSimpleName())) {
                return false;
            }
        }
        return true;
    }
}
