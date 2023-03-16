package sk.stuba.fei.uim.oop.game.cards.brownCards;

import sk.stuba.fei.uim.oop.game.cards.Card;
import sk.stuba.fei.uim.oop.game.player.Player;

import java.util.List;
import java.util.Random;

public class CatBalou extends BrownCard {
    private final Player targetPlayer;
    private final List<Card> targetPlayerDeck;
    private final Random cardNumber;

    public CatBalou(Player currentPlayer, List<Card> deck, Player targetPlayer, List<Card> targetPlayerDeck) {
        super(currentPlayer, deck);
        this.targetPlayer = targetPlayer;
        this.targetPlayerDeck = targetPlayerDeck;
        this.cardNumber = new Random();
    }

    @Override
    public void play() {
        super.play();
        this.deck.add(
                this.targetPlayerDeck.remove(this.cardNumber.nextInt(this.targetPlayerDeck.size()))
        );
    }

    @Override
    public boolean isPlayable() {
        if (this.targetPlayerDeck == null) {
            return !this.targetPlayer.getCardsOnTable().isEmpty() || !this.targetPlayer.getCardsInHand().isEmpty();
        } else {
            return !this.targetPlayerDeck.isEmpty();
        }
    }

    @Override
    public boolean requireTargetPlayer() {
        return true;
    }

    @Override
    public boolean requireTargetPlayerDeck() {
        return true;
    }
}
