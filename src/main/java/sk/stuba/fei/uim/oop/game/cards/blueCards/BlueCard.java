package sk.stuba.fei.uim.oop.game.cards.blueCards;

import sk.stuba.fei.uim.oop.game.cards.Card;
import sk.stuba.fei.uim.oop.game.player.Player;

import java.util.Random;

public abstract class BlueCard extends Card {
    protected final int probabilityOneIn;
    protected final Player player;
    private final Random chance;

    public BlueCard(int probabilityOneIn, Player player) {
        this.probabilityOneIn = probabilityOneIn;
        this.player = player;
        this.chance = new Random();
    }

    public boolean hasEffect() {
        return this.chance.nextInt(this.probabilityOneIn) == 0;
    }

    @Override
    public void play() {
        this.player.getCardsInHand().remove(this);
    }
}
