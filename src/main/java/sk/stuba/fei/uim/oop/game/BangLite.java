package sk.stuba.fei.uim.oop.game;

import sk.stuba.fei.uim.oop.game.cards.Card;
import sk.stuba.fei.uim.oop.game.cards.blueCards.Barrel;
import sk.stuba.fei.uim.oop.game.cards.blueCards.Dynamite;
import sk.stuba.fei.uim.oop.game.cards.blueCards.Prison;
import sk.stuba.fei.uim.oop.game.cards.brownCards.*;
import sk.stuba.fei.uim.oop.game.player.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class BangLite {
    private final List<Card> deck;
    private final List<Player> players;
    private final Random chance;
    private Player currentPlayer;
    private Player targetPlayer;
    private List<Card> targetPlayerDeck;

    public static final int CARDS_TO_DRAW_WHEN_KILL_COUNT = 2;
    private static final int BARREL_COUNT = 2;
    private static final int DYNAMITE_COUNT = 1;
    private static final int PRISON_COUNT = 3;
    private static final int BANG_COUNT = 30;
    private static final int MISSED_COUNT = 15;
    private static final int BEER_COUNT = 8;
    private static final int CAT_BALOU_COUNT = 6;
    private static final int STAGECOACH_COUNT = 4;
    private static final int INDIANS_COUNT = 2;
    private static final int MINIMUM_PLAYERS_COUNT = 2;
    private static final int MAXIMUM_PLAYERS_COUNT = 4;
    private static final int INITIAL_PLAYER_CARDS_COUNT = 4;

    public BangLite() {
        this.deck = new ArrayList<>();
        this.players = new ArrayList<>();
        this.chance = new Random();
        this.currentPlayer = null;
        this.targetPlayer = null;
        this.targetPlayerDeck = null;
        this.preparation();
        this.game();
    }

    private void preparation() {
        this.createDeck();
        this.shuffleDeck();
        this.createPlayers();
    }

    private void game() {
        while (this.playersAlive() > 1) {
            ;
            this.removeDeathPlayers();
        }
        this.showWinner();
    }

    private void showWinner() {
        Player winner = null;
        for (Player player : this.players) {
            if (player.isAlive()) {
                winner = player;
                break;
            }
        }
        if (winner == null) {
            System.out.println("There is no winner!");
            return;
        }
        System.out.println("The winner is: " + winner.getName());
    }

    private void removeDeathPlayers() {
        this.players.removeAll(this.collectCardOfDeathPlayers());
    }

    private List<Player> collectCardOfDeathPlayers() {
        List<Player> deathPlayers = new ArrayList<>();
        for (Player player : this.players) {
            if (!player.isAlive()) {
                if (!player.getCardsOnTable().isEmpty()) {
                    this.deck.addAll(player.getCardsInHand());
                    player.getCardsInHand().clear();
                }
                if (!player.getCardsOnTable().isEmpty()) {
                    this.deck.addAll(player.getCardsOnTable());
                    player.getCardsOnTable().clear();
                }
                deathPlayers.add(player);
            }
        }
        return deathPlayers;
    }

    private int playersAlive() {
        int playersAlive = 0;
        for (Player player : this.players) {
            if (player.isAlive()) {
                playersAlive++;
            }
        }
        return playersAlive;
    }

    private void createPlayers() {

    }

    private void shuffleDeck() {
        Collections.shuffle(this.deck);
    }

    private void createDeck() {
        this.addBlueCards();
        this.addBrownCards();
    }

    private void addBlueCards() {
        for (int i = 0; i < BangLite.BARREL_COUNT; i++) {
            this.deck.add(new Barrel());
        }
        for (int i = 0; i < BangLite.DYNAMITE_COUNT; i++) {
            this.deck.add(new Dynamite());
        }
        for (int i = 0; i < BangLite.PRISON_COUNT; i++) {
            this.deck.add(new Prison());
        }
    }

    private void addBrownCards() {
        for (int i = 0; i < BangLite.BANG_COUNT; i++) {
            this.deck.add(new Bang());
        }
        for (int i = 0; i < BangLite.MISSED_COUNT; i++) {
            this.deck.add(new Missed());
        }
        for (int i = 0; i < BangLite.BEER_COUNT; i++) {
            this.deck.add(new Beer());
        }
        for (int i = 0; i < BangLite.CAT_BALOU_COUNT; i++) {
            this.deck.add(new CatBalou());
        }
        for (int i = 0; i < BangLite.STAGECOACH_COUNT; i++) {
            this.deck.add(new Stagecoach());
        }
        for (int i = 0; i < BangLite.INDIANS_COUNT; i++) {
            this.deck.add(new Indians());
        }
    }


    public List<Card> getDeck() {
        return this.deck;
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

    public Random getChance() {
        return this.chance;
    }
}
