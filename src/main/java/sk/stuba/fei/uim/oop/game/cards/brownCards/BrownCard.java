package sk.stuba.fei.uim.oop.game.cards.brownCards;

import sk.stuba.fei.uim.oop.game.GameValues;
import sk.stuba.fei.uim.oop.game.cards.Card;

public abstract class BrownCard extends Card {
    public BrownCard(GameValues bangLite) {
        super(bangLite);
    }

    @Override
    public void play() {
        this.game.getCurrentPlayer().getCardsInHand().remove(this);
        this.game.getDiscardPile().add(this);
    }
}
