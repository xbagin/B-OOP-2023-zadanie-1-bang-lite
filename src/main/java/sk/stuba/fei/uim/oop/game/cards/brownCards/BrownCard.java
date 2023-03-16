package sk.stuba.fei.uim.oop.game.cards.brownCards;

import sk.stuba.fei.uim.oop.game.cards.Card;
import sk.stuba.fei.uim.oop.game.player.Player;

import java.util.List;

public abstract class BrownCard extends Card {
    protected final Player currentPlayer;
    protected final List<Card> deck;

    public BrownCard(Player currentPlayer, List<Card> deck) {
        this.currentPlayer = currentPlayer;
        this.deck = deck;
    }

    @Override
    public void play() {
        this.currentPlayer.getCardsInHand().remove(this);
        this.deck.add(this);
    }
}
