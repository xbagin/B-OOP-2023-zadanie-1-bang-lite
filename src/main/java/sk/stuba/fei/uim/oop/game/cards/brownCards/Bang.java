package sk.stuba.fei.uim.oop.game.cards.brownCards;

import sk.stuba.fei.uim.oop.game.BangLite;
import sk.stuba.fei.uim.oop.game.cards.Card;
import sk.stuba.fei.uim.oop.game.player.Player;

import java.util.List;

public class Bang extends BrownCard {
    private final Player targetPlayer;

    public Bang(Player currentPlayer, List<Card> deck, Player targetPlayer) {
        super(currentPlayer, deck);
        this.targetPlayer = targetPlayer;
    }

    @Override
    public void play() {
        super.play();
        if (this.targetPlayer.dealWithBang()) {
            return;
        }
        this.player.drawCards(BangLite.CARDS_TO_DRAW_WHEN_KILL_COUNT, this.deck);
    }

    @Override
    public boolean requireTargetPlayer() {
        return true;
    }
}
