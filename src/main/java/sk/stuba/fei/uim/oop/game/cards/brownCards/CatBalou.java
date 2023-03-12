package sk.stuba.fei.uim.oop.game.cards.brownCards;

import sk.stuba.fei.uim.oop.game.BangLite;
import sk.stuba.fei.uim.oop.game.cards.Card;

import java.util.List;
import java.util.Random;

public class CatBalou extends BrownCard {
    private final Random cardNumber;

    public CatBalou() {
        this.cardNumber = new Random();
    }

    @Override
    public void play(BangLite bangLite) {
        super.play(bangLite);
        List<Card> playerDeck = bangLite.getTargetPlayerDeck();
        bangLite.getDeck().add(
                playerDeck.remove(this.cardNumber.nextInt(playerDeck.size()))
        );
    }

    @Override
    public boolean isPlayable(BangLite bangLite) {
        if (bangLite.getTargetPlayerDeck() == null) {
            return !bangLite.getTargetPlayer().getCardsOnTable().isEmpty() || !bangLite.getTargetPlayer().getCardsInHand().isEmpty();
        } else {
            return !bangLite.getTargetPlayerDeck().isEmpty();
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
