package sk.stuba.fei.uim.oop.game;

import sk.stuba.fei.uim.oop.game.cards.Card;
import sk.stuba.fei.uim.oop.game.player.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameValues {
    private final List<Card> deck;
    private final List<Card> discardPile;
    private final List<Player> players;
    private Player currentPlayer;
    private Player targetPlayer;
    private List<Card> targetPlayerDeck;
    private boolean playerCanPlay;

    public GameValues() {
        this.deck = new ArrayList<>();
        this.discardPile = new ArrayList<>();
        this.players = new ArrayList<>();
        this.currentPlayer = null;
        this.targetPlayer = null;
        this.targetPlayerDeck = null;
        this.playerCanPlay = false;
    }

    public List<Card> getDeck() {
        return this.deck;
    }

    public List<Card> getDiscardPile() {
        return this.discardPile;
    }

    public List<Player> getPlayers() {
        return this.players;
    }

    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    public Player getTargetPlayer() {
        return this.targetPlayer;
    }

    public List<Card> getTargetPlayerDeck() {
        return this.targetPlayerDeck;
    }

    public boolean getPlayerCanPlay() {
        return this.playerCanPlay;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void setTargetPlayer(Player targetPlayer) {
        this.targetPlayer = targetPlayer;
    }

    public void setTargetPlayerDeck(List<Card> targetPlayerDeck) {
        this.targetPlayerDeck = targetPlayerDeck;
    }

    public void setPlayerCanPlay(boolean playerCanPlay) {
        this.playerCanPlay = playerCanPlay;
    }

    public List<Card> drawCards(int numberOfCards) {
        if (this.deck.size() < numberOfCards) {
            Collections.shuffle(this.discardPile);
            this.deck.addAll(this.discardPile);
            this.discardPile.clear();
        }
        List<Card> cards = new ArrayList<>();
        for (int i = 0; i < numberOfCards; i++) {
            cards.add(deck.remove(0));
        }
        return cards;
    }
}
