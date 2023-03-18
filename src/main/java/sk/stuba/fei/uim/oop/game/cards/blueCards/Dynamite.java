package sk.stuba.fei.uim.oop.game.cards.blueCards;

import sk.stuba.fei.uim.oop.game.cards.Card;
import sk.stuba.fei.uim.oop.game.player.Player;

import java.util.List;

public class Dynamite extends BlueCard {
    private final List<Player> players;

    private static final int PROBABILITY_ONE_IN = 8;
    public static final int LIVES_TO_REMOVE_COUNT = 3;

    public Dynamite(Player currentPlayer, List<Card> deck, List<Player> players) {
        super(Dynamite.PROBABILITY_ONE_IN, currentPlayer, deck);
        this.players = players;
    }

    @Override
    public boolean applyEffect(List<Card> toRemove) {
        toRemove.add(this);  // this.player.getCardsOnTable().remove(this);  ConcurrentModificationException
        if (this.hasEffect()) {
            this.deck.add(this);
            for (int i = 0; i < Dynamite.LIVES_TO_REMOVE_COUNT; i++) {
                this.player.removeLive();
            }
            System.out.println(this.getClass().getSimpleName() + "exploded!");
            if (!this.player.isAlive()) {
                System.out.println(this.player.getName() + " (you) lost!");
                return false;
            }
        } else {
            this.getPreviousPlayer().getCardsOnTable().add(this);
        }
        return true;
    }

    private Player getPreviousPlayer() {
        return this.players.get(
                (this.players.indexOf(this.player) + this.players.size() - 1) % this.players.size()
        );
    }
}
