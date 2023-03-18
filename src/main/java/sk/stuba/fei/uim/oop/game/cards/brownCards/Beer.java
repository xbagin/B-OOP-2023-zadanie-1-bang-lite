package sk.stuba.fei.uim.oop.game.cards.brownCards;

import sk.stuba.fei.uim.oop.game.cards.Card;
import sk.stuba.fei.uim.oop.game.player.Player;

import java.util.List;

public class Beer extends BrownCard {
    public Beer(Player currentPlayer, List<Card> deck) {
        super(currentPlayer, deck);
    }

    @Override
    public void play() {
        super.play();
        this.player.addLive();
    }
}
