package sk.stuba.fei.uim.oop.game.cards.blueCards;

import sk.stuba.fei.uim.oop.game.cards.Card;
import sk.stuba.fei.uim.oop.game.player.Player;

import java.util.List;
import java.util.Random;

public abstract class BlueCard extends Card {
    protected final int probabilityOneIn;
    protected final Player player;
    private final Random chance;

    public BlueCard(int probabilityOneIn, Player player, List<Card> deck) {
        super(player, deck);
        this.probabilityOneIn = probabilityOneIn;
        this.player = player;
        this.chance = new Random();
    }

    public boolean hasEffect() {
        return this.chance.nextInt(this.probabilityOneIn) == 0;
    }

    public boolean applyEffect(List<Card> toRemove) {
        return true;
    }

    @Override
    public void play() {
        this.player.getCardsInHand().remove(this);
        this.player.getCardsOnTable().add(this);
    }
}
