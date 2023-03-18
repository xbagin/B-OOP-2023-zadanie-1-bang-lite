package sk.stuba.fei.uim.oop.game.cards.blueCards;

import sk.stuba.fei.uim.oop.game.BangLite;
import sk.stuba.fei.uim.oop.game.cards.Card;

import java.util.List;
import java.util.Objects;

public class Prison extends BlueCard {
    private static final int PROBABILITY_ONE_IN = 4;

    public Prison(BangLite bangLite) {
        super(Prison.PROBABILITY_ONE_IN, bangLite);
    }

    @Override
    public boolean applyEffect(List<Card> toRemove) {
        toRemove.add(this);  // this.player.getCardsOnTable().remove(this);  ConcurrentModificationException
        this.game.getDeck().add(this);
        if (!this.hasEffect()) {
            System.out.println(this.game.getCurrentPlayer().getName() + " did not escape from the prison.");
            return false;
        } else {
            System.out.println(this.game.getCurrentPlayer().getName() + " escaped from the prison.");
        }
        return true;
    }

    @Override
    public boolean requireTargetPlayer() {
        return true;
    }

    @Override
    public boolean isPlayable() {
        for (Card card : this.game.getTargetPlayer().getCardsOnTable()) {
            if (Objects.equals(card.getClass().getSimpleName(), this.getClass().getSimpleName())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void play() {
        super.play();
        this.game.getTargetPlayer().getCardsOnTable().add(this);
    }
}
