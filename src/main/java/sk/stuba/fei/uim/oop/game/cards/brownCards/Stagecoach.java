package sk.stuba.fei.uim.oop.game.cards.brownCards;

import sk.stuba.fei.uim.oop.game.cards.Card;
import sk.stuba.fei.uim.oop.game.player.Player;

import java.util.List;

public class Stagecoach extends BrownCard {
    private static final int CARDS_TO_DRAW_COUNT = 2;

    public Stagecoach(Player currentPlayer, List<Card> deck) {
        super(currentPlayer, deck);
    }

    @Override
    public void play() {
        super.play();
        this.player.drawCards(Stagecoach.CARDS_TO_DRAW_COUNT, this.deck);
    }
}
