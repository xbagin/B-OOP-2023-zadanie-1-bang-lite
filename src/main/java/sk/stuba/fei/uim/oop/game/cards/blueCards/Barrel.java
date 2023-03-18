package sk.stuba.fei.uim.oop.game.cards.blueCards;

import sk.stuba.fei.uim.oop.game.cards.Card;
import sk.stuba.fei.uim.oop.game.player.Player;

import java.util.List;
import java.util.Objects;

public class Barrel extends BlueCard {
    private static final int PROBABILITY_ONE_IN = 4;

    public Barrel(Player currentPlayer, List<Card> deck) {
        super(Barrel.PROBABILITY_ONE_IN, currentPlayer, deck);
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
