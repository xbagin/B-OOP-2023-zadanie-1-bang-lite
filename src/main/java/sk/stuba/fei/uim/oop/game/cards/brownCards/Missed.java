package sk.stuba.fei.uim.oop.game.cards.brownCards;

import sk.stuba.fei.uim.oop.game.GameValues;

public class Missed extends BrownCard {
    public Missed(GameValues bangLite) {
        super(bangLite);
    }

    @Override
    public void play() {
        if (this.game.getTargetPlayer() != null) {
            this.game.getTargetPlayer().getCardsInHand().remove(this);
            this.game.getDiscardPile().add(this);
        } else {  // current player stupidly waste the card
            super.play();
        }
    }
}
