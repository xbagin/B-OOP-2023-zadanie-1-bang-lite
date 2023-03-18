package sk.stuba.fei.uim.oop.game.cards.brownCards;

import sk.stuba.fei.uim.oop.game.BangLite;

import java.util.Objects;

public class Indians extends BrownCard {
    public Indians(BangLite bangLite) {
        super(bangLite);
    }

    @Override
    public void play() {
        super.play();
        this.game.getPlayers().forEach(player -> {
            if (!Objects.equals(player, this.game.getCurrentPlayer())) {
                if (!player.dealWithIndians(this.game.getDeck())) {
                    this.game.getCurrentPlayer().drawCards(BangLite.CARDS_TO_DRAW_WHEN_KILL_COUNT, this.game.getDeck());
                }
            }
        });
    }
}
