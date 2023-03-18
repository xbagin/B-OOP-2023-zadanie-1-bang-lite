package sk.stuba.fei.uim.oop.game.cards.brownCards;

import sk.stuba.fei.uim.oop.game.BangLite;
import sk.stuba.fei.uim.oop.game.cards.Card;

public abstract class BrownCard extends Card {
    public BrownCard(BangLite bangLite) {
        super(bangLite);
    }

    @Override
    public void play() {
        this.game.getCurrentPlayer().getCardsInHand().remove(this);
        this.game.getDeck().add(this);
    }
}
