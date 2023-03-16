package sk.stuba.fei.uim.oop.game.cards.brownCards;

import sk.stuba.fei.uim.oop.game.cards.Card;
import sk.stuba.fei.uim.oop.game.player.Player;

import java.util.List;

public class Missed extends BrownCard {
    private final Player targetPlayer;

    public Missed(Player currentPlayer, List<Card> deck, Player targetPlayer) {
        super(currentPlayer, deck);
        this.targetPlayer = targetPlayer;
    }

    @Override
    public void play() {
        if (this.targetPlayer != null) {
            this.targetPlayer.getCardsInHand().remove(this);
            this.deck.add(this);
        } else {  // current player stupidly waste the card
            super.play();
        }
    }
}
