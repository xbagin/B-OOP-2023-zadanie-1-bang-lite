package sk.stuba.fei.uim.oop.game.cards.brownCards;

import sk.stuba.fei.uim.oop.game.cards.Card;
import sk.stuba.fei.uim.oop.game.player.Player;

import java.util.List;

public abstract class BrownCard extends Card {

    public BrownCard(Player currentPlayer, List<Card> deck) {
        super(currentPlayer, deck);
    }

    @Override
    public void play() {
        this.player.getCardsInHand().remove(this);
        this.deck.add(this);
    }
}
