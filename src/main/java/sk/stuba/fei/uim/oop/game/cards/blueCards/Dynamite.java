package sk.stuba.fei.uim.oop.game.cards.blueCards;

import sk.stuba.fei.uim.oop.game.BangLite;
import sk.stuba.fei.uim.oop.game.cards.Card;
import sk.stuba.fei.uim.oop.game.player.Player;

import java.util.List;

public class Dynamite extends BlueCard {
    private static final int PROBABILITY_ONE_IN = 8;
    public static final int LIVES_TO_REMOVE_COUNT = 3;

    public Dynamite(BangLite bangLite) {
        super(Dynamite.PROBABILITY_ONE_IN, bangLite);
    }

    @Override
    public boolean applyEffect(List<Card> toRemove) {
        toRemove.add(this);  // this.player.getCardsOnTable().remove(this);  ConcurrentModificationException
        if (this.hasEffect()) {
            this.game.getDeck().add(this);
            for (int i = 0; i < Dynamite.LIVES_TO_REMOVE_COUNT; i++) {
                this.game.getCurrentPlayer().removeLive();
            }
            System.out.println(this.getClass().getSimpleName() + "exploded!");
            if (!this.game.getCurrentPlayer().isAlive()) {
                System.out.println(this.game.getCurrentPlayer().getName() + " (you) lost!");
                return false;
            }
        } else {
            this.getPreviousPlayer().getCardsOnTable().add(this);
        }
        return true;
    }

    private Player getPreviousPlayer() {
        List<Player> players = this.game.getPlayers();
        return players.get(
                (players.indexOf(this.game.getCurrentPlayer()) + players.size() - 1) % players.size()
        );
    }

    @Override
    public void play() {
        super.play();
        this.game.getCurrentPlayer().getCardsOnTable().add(this);
    }
}
