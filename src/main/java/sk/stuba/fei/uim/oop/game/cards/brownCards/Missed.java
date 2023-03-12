package sk.stuba.fei.uim.oop.game.cards.brownCards;

import sk.stuba.fei.uim.oop.game.BangLite;

public class Missed extends BrownCard {
    @Override
    public void play(BangLite bangLite) {
        if (bangLite.getTargetPlayer() != null) {
            bangLite.getTargetPlayer().getCardsInHand().remove(this);
            bangLite.getDeck().add(this);
        } else {  // current player stupidly waste the card
            super.play(bangLite);
        }
    }
}
