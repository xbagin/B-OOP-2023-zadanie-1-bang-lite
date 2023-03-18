package sk.stuba.fei.uim.oop.game.cards;

import sk.stuba.fei.uim.oop.game.player.Player;

import java.util.List;

abstract public class Card implements Playable {
    protected final Player player;
    protected final List<Card> deck;

    protected Card(Player player, List<Card> deck) {
        this.player = player;
        this.deck = deck;
    }

    @Override
    public boolean isPlayable() {
        return true;
    }

    @Override
    public boolean requireTargetPlayer() {
        return false;
    }

    @Override
    public boolean requireTargetPlayerDeck() {
        return false;
    }
}
