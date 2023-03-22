package sk.stuba.fei.uim.oop.game.cards.brownCards;

import sk.stuba.fei.uim.oop.game.GameValues;
import sk.stuba.fei.uim.oop.game.player.Player;

import java.util.Random;

public class CatBalou extends BrownCard {
    private final Random cardNumber;

    public CatBalou(GameValues bangLite) {
        super(bangLite);
        this.cardNumber = new Random();
    }

    @Override
    public void play() {
        super.play();
        this.game.getDiscardPile().add(
                this.game.getTargetPlayerDeck().remove(
                        this.cardNumber.nextInt(this.game.getTargetPlayerDeck().size())
                )
        );
    }

    @Override
    public boolean isPlayable() {
        if (this.game.getTargetPlayerDeck() == null) {
            Player target = this.game.getTargetPlayer();
            return !target.getCardsOnTable().isEmpty() || !target.getCardsInHand().isEmpty();
        } else {
            return !this.game.getTargetPlayerDeck().isEmpty();
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
