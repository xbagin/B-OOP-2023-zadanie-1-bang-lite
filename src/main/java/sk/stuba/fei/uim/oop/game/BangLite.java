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

public class BangLite {
    private final List<Card> deck;
    private final List<Player> players;

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

    public BangLite() {
        this.deck = new ArrayList<>();
        this.players = new ArrayList<>();
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
}
