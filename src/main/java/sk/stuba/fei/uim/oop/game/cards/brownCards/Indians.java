package sk.stuba.fei.uim.oop.game.cards.brownCards;

import sk.stuba.fei.uim.oop.game.BangLite;
import sk.stuba.fei.uim.oop.game.cards.Card;
import sk.stuba.fei.uim.oop.game.player.Player;

import java.util.List;
import java.util.Objects;

public class Indians extends BrownCard {
    private final List<Player> players;

    public Indians(Player currentPlayer, List<Card> deck, List<Player> players) {
        super(currentPlayer, deck);
        this.players = players;
    }

    @Override
    public void play() {
        super.play();
        this.players.forEach(player -> {
            if (!Objects.equals(player, this.currentPlayer)) {
                if (!player.dealWithIndians(this.deck)) {
                    this.currentPlayer.drawCards(BangLite.CARDS_TO_DRAW_WHEN_KILL_COUNT, this.deck);
                }
            }
        });
    }
}
