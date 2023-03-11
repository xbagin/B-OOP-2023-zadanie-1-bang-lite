package sk.stuba.fei.uim.oop.game;

import sk.stuba.fei.uim.oop.game.cards.Card;
import sk.stuba.fei.uim.oop.game.cards.blueCards.Barrel;
import sk.stuba.fei.uim.oop.game.cards.blueCards.BlueCard;
import sk.stuba.fei.uim.oop.game.cards.blueCards.Dynamite;
import sk.stuba.fei.uim.oop.game.cards.blueCards.Prison;
import sk.stuba.fei.uim.oop.game.cards.brownCards.*;
import sk.stuba.fei.uim.oop.game.player.Player;
import sk.stuba.fei.uim.oop.utility.KeyboardInput;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BangLite {
    private final List<Card> deck;
    private final List<Player> players;
    private Player currentPlayer;
    private Player targetPlayer;
    private List<Card> targetPlayerDeck;
    private boolean playerCanPlay;

    public static final int CARDS_TO_DRAW_WHEN_KILL_COUNT = 2;
    public static final int CARDS_TO_DRAW_AT_THE_START_OF_TURN_COUNT = 2;
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
        this.currentPlayer = null;
        this.targetPlayer = null;
        this.targetPlayerDeck = null;
        this.playerCanPlay = false;
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
            this.checkEffects();
            if (this.playerCanPlay) {
                this.drawCards();
                this.playCards();
                this.discardExcessCards();
                this.passDynamite();
            }
            ;
            this.removeDeathPlayers();
            this.currentPlayer = this.nextPlayer();
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
        this.players.forEach(player -> {
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
        });
        return deathPlayers;
    }

    private Player getPreviousPlayer() {
        return this.players.get(
                (this.players.indexOf(this.currentPlayer) + this.players.size() - 1) % this.players.size()
        );
    }

    private void passDynamite() {
        this.currentPlayer.getCardsOnTable().forEach(card -> {
            if (card instanceof Dynamite) {
                this.currentPlayer.getCardsOnTable().remove(card);
                this.getPreviousPlayer().getCardsOnTable().add(card);
            }
        });
    }

    private void discardExcessCards() {
        while (this.currentPlayer.getCardsInHand().size() > this.currentPlayer.getLives()) {
            int position;
            do {
                ;/**/
                position = KeyboardInput.readInt("");
            } while (!(position > 0 && position <= this.currentPlayer.getCardsInHand().size()));

            this.currentPlayer.getCardsInHand().remove(position - 1);
        }
    }

    private void playCards() {
        boolean played = false;
        do {
            int position;  // 0 - play no card
            do {
                ;/**/
                position = KeyboardInput.readInt("");
            } while (!(position >= 0 && position <= this.currentPlayer.getCardsInHand().size()));

            if (position == 0) {
                played = true;
            } else {
                Card card = this.currentPlayer.getCardsInHand().get(position - 1);
                if (card.requireTargetPlayer()) {
                    List<Player> targetPlayers = new ArrayList<>(this.players);
                    targetPlayers.remove(this.currentPlayer);
                    int decision;
                    do {
                        decision = KeyboardInput.readInt("");
                        ;/**/
                    } while (!(decision >= 1 && decision <= targetPlayers.size()));
                    this.targetPlayer = targetPlayers.get(decision - 1);
                }
                if (card.isPlayable(this)) {
                    if (card.requireTargetPlayerDeck()) {
                        int decision;
                        do {
                            decision = KeyboardInput.readInt("");
                            ;/**/
                        } while (!(decision >= 1 && decision <= 2));
                        boolean hand = decision == 1;
                        this.targetPlayerDeck = hand ? new ArrayList<>(this.targetPlayer.getCardsOnTable()) : this.targetPlayer.getCardsInHand();
                    }
                    card.play(this);
                    played = true;
                }
            }
        } while (!played);
    }

    private void drawCards() {
        for (int i = 0; i < BangLite.CARDS_TO_DRAW_AT_THE_START_OF_TURN_COUNT; i++) {
            this.currentPlayer.getCardsInHand().add(this.deck.remove(0));
        }
    }

    private void checkEffects() {
        this.currentPlayer.getCardsOnTable().forEach(card -> {
            if (card instanceof Dynamite) {
                if (card.hasEffect()) {
                    for (int i = 0; i < Dynamite.LIVES_TO_REMOVE_COUNT; i++) {
                        this.currentPlayer.removeLive();
                    }
                    this.deck.add(card);
                    if (this.currentPlayer.isAlive()) {
                        this.playerCanPlay = true;
                    }
                }
            } else if (card instanceof Prison) {
                if (card.hasEffect()) {
                    this.playerCanPlay = true;
                }
            }
        });
    }

    private Player nextPlayer() {
        this.playerCanPlay = true;
        return this.players.get(
                (this.players.indexOf(this.currentPlayer) + 1) % this.players.size()
        );
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
        ;/**/
        this.currentPlayer = this.players.get(0);
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
}
