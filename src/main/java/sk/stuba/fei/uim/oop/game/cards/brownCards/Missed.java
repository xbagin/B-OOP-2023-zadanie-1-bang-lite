package sk.stuba.fei.uim.oop.game.cards.brownCards;

import sk.stuba.fei.uim.oop.game.BangLite;

public class Missed extends BrownCard {
    public Missed(BangLite bangLite) {
        super(bangLite);
    }

    @Override
    public void play() {
        if (this.game.getTargetPlayer() != null) {
            this.game.getTargetPlayer().getCardsInHand().remove(this);
            this.game.getDeck().add(this);
        } else {  // current player stupidly waste the card
            super.play();
        }
    }
}
