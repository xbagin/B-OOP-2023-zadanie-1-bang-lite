package sk.stuba.fei.uim.oop.game.cards.brownCards;

import sk.stuba.fei.uim.oop.game.BangLite;
import sk.stuba.fei.uim.oop.game.cards.Card;

public abstract class BrownCard extends Card {
    @Override
    public void play(BangLite bangLite) {
        bangLite.getCurrentPlayer().getCardsInHand().remove(this);
        bangLite.getDeck().add(this);
    }
}
