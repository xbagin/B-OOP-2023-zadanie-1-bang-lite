package sk.stuba.fei.uim.oop.game.cards.brownCards;

import sk.stuba.fei.uim.oop.game.BangLite;

import java.util.Objects;

public class Indians extends BrownCard {
    @Override
    public void play(BangLite bangLite) {
        super.play(bangLite);
        bangLite.getPlayers().forEach(player -> {
            if (!Objects.equals(player, bangLite.getCurrentPlayer())) {
                if (!player.dealWithIndians(bangLite.getDeck())) {
                    bangLite.getCurrentPlayer().drawCards(BangLite.CARDS_TO_DRAW_WHEN_KILL_COUNT, bangLite.getDeck());
                }
            }
        });
    }
}
