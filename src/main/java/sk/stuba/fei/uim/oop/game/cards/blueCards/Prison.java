package sk.stuba.fei.uim.oop.game.cards.blueCards;

import sk.stuba.fei.uim.oop.game.cards.Card;
import sk.stuba.fei.uim.oop.game.player.Player;

import java.util.List;
import java.util.Objects;

public class Prison extends BlueCard {
    private static final int PROBABILITY_ONE_IN = 4;

    public Prison(Player targetPlayer, List<Card> deck) {
        super(Prison.PROBABILITY_ONE_IN, targetPlayer, deck);
    }

    @Override
    public boolean applyEffect(List<Card> toRemove) {
        toRemove.add(this);  // this.player.getCardsOnTable().remove(this);  ConcurrentModificationException
        this.deck.add(this);
        if (!this.hasEffect()) {
            System.out.println(this.player.getName() + " did not escape from the prison.");
            return false;
        } else {
            System.out.println(this.player.getName() + " escaped from the prison.");
        }
        return true;
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
