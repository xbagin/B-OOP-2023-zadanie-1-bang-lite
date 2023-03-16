package sk.stuba.fei.uim.oop.game.cards.blueCards;

import sk.stuba.fei.uim.oop.game.player.Player;

public class Dynamite extends BlueCard {
    private static final int PROBABILITY_ONE_IN = 8;
    public static final int LIVES_TO_REMOVE_COUNT = 3;

    public Dynamite(Player currentPlayer) {
        super(Dynamite.PROBABILITY_ONE_IN, currentPlayer);
    }

    @Override
    public void play() {
        super.play();
        this.player.getCardsOnTable().add(this);
    }
}
