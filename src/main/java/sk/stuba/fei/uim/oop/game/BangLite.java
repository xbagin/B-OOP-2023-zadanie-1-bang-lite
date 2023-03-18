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
import java.util.Objects;

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
            System.out.println("\nTurn of " + this.currentPlayer.getName() + ":");
            this.checkEffects();
            if (this.playerCanPlay) {
                this.printTable();
                this.drawCards();
                this.playCards();
                this.discardExcessCards();
            }
            this.removeDeathPlayers();
            this.currentPlayer = this.nextPlayer();
        }
        this.showWinner();
    }

    private void printTable() {
        System.out.println();
        this.players.forEach(player -> {
            System.out.println(player.getName() + (Objects.equals(player, this.currentPlayer) ? " (you):" : ":"));
            System.out.println("  Lives: " + player.getLives());
            System.out.print("  Cards on table: ");
            for (Card card : player.getCardsOnTable()) {
                System.out.print(card.getClass().getSimpleName() + " ");
            }
            System.out.println();
            System.out.println("  Cards in hand: " + player.getCardsInHand().size());
        });
    }

    private void printHand() {
        System.out.print("\nYour cards: ");
        for (int i = 0; i < this.currentPlayer.getCardsInHand().size(); i++) {
            System.out.print("[" + (i + 1) + "] " + this.currentPlayer.getCardsInHand().get(i).getClass().getSimpleName() + " ");
        }
        System.out.println();
    }

    private void printTargetPlayers(List<Player> targetPlayers) {
        System.out.print("\nTarget players: ");
        for (int i = 0; i < targetPlayers.size(); i++) {
            System.out.print("[" + (i + 1) + "] " + targetPlayers.get(i).getName() + " ");
        }
        System.out.println();
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
            System.out.println("\nThere is no winner!");
            return;
        }
        System.out.println("\nThe winner is: " + winner.getName() + "!");
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

    private void discardExcessCards() {
        while (this.currentPlayer.getCardsInHand().size() > this.currentPlayer.getLives()) {
            int position;
            do {
                this.printHand();
                position = KeyboardInput.readInt("Enter the card to discard");
            } while (!(position > 0 && position <= this.currentPlayer.getCardsInHand().size()));

            this.currentPlayer.getCardsInHand().remove(position - 1);
        }
    }

    private void playCards() {
        int position;  // 0 - play no card
        do {
            this.printHand();
            do {
                position = KeyboardInput.readInt("Enter the card to play or 0 to end the turn");
            } while (!(position >= 0 && position <= this.currentPlayer.getCardsInHand().size()));

            if (position > 0) {
                Card card = this.currentPlayer.getCardsInHand().get(position - 1);
                if (card.requireTargetPlayer()) {
                    List<Player> targetPlayers = new ArrayList<>(this.players);
                    targetPlayers.remove(this.currentPlayer);
                    targetPlayers.removeIf(player -> !player.isAlive());
                    if (targetPlayers.isEmpty()) {
                        System.out.println("This card can not be played.");
                        continue;
                    }
                    int decision;
                    do {
                        this.printTargetPlayers(targetPlayers);
                        decision = KeyboardInput.readInt("Enter the target player");
                    } while (!(decision >= 1 && decision <= targetPlayers.size()));
                    this.targetPlayer = targetPlayers.get(decision - 1);
                }
                if (card.requireTargetPlayerDeck()) {
                    if (card.isPlayable()) {
                        boolean inHand = !this.targetPlayer.getCardsInHand().isEmpty();
                        boolean onTable = !this.targetPlayer.getCardsOnTable().isEmpty();
                        if (inHand && !onTable) {
                            this.targetPlayerDeck = this.targetPlayer.getCardsInHand();
                        } else if (onTable && !inHand) {
                            this.targetPlayerDeck = this.targetPlayer.getCardsOnTable();
                        } else {
                            int decision;
                            do {
                                decision = KeyboardInput.readInt("Enter 1 for removing the card from the hand or 2 for removing the card from the table");
                                this.targetPlayerDeck = decision == 1 ? this.targetPlayer.getCardsInHand() : this.targetPlayer.getCardsOnTable();
                            } while (!(decision >= 1 && decision <= 2));
                        }
                    }
                }
                if (card.isPlayable()) {
                    card.play();
                } else {
                    System.out.println("This card can not be played" + (this.targetPlayer != null ? "on this player." : "."));
                }
                if (this.targetPlayer != null && !this.targetPlayer.isAlive()) {
                    System.out.println(this.targetPlayer.getName() + " lost!");
                }
            }
            this.targetPlayer = null;
            this.targetPlayerDeck = null;
            this.printTable();
        } while (position != 0);
    }

    private void drawCards() {
        for (int i = 0; i < BangLite.CARDS_TO_DRAW_AT_THE_START_OF_TURN_COUNT; i++) {
            this.currentPlayer.getCardsInHand().add(this.deck.remove(0));
        }
    }

    private void checkEffects() {
        this.playerCanPlay = true;
        List<Card> toRemove = new ArrayList<>();
        this.currentPlayer.getCardsOnTable().forEach(card -> this.playerCanPlay = this.playerCanPlay && ((BlueCard) card).applyEffect(toRemove));
        this.currentPlayer.getCardsOnTable().removeAll(toRemove);
    }

    private Player nextPlayer() {
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
        int playersCount;
        do {
            playersCount = KeyboardInput.readInt("Enter the number of players (2-4)");
        } while (!(playersCount >= BangLite.MINIMUM_PLAYERS_COUNT && playersCount <= BangLite.MAXIMUM_PLAYERS_COUNT));

        for (int i = 0; i < playersCount; i++) {
            this.players.add(new Player("Player" + (i + 1)));
        }

        this.players.forEach(player -> player.drawCards(BangLite.INITIAL_PLAYER_CARDS_COUNT, this.deck));
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
            this.deck.add(new Barrel(this.currentPlayer, this.deck));
        }
        for (int i = 0; i < BangLite.DYNAMITE_COUNT; i++) {
            this.deck.add(new Dynamite(this.currentPlayer, this.deck, this.players));
        }
        for (int i = 0; i < BangLite.PRISON_COUNT; i++) {
            this.deck.add(new Prison(this.targetPlayer, this.deck));
        }
    }

    private void addBrownCards() {
        for (int i = 0; i < BangLite.BANG_COUNT; i++) {
            this.deck.add(new Bang(this.currentPlayer, this.deck, this.targetPlayer));
        }
        for (int i = 0; i < BangLite.MISSED_COUNT; i++) {
            this.deck.add(new Missed(this.currentPlayer, this.deck, this.targetPlayer));
        }
        for (int i = 0; i < BangLite.BEER_COUNT; i++) {
            this.deck.add(new Beer(this.currentPlayer, this.deck));
        }
        for (int i = 0; i < BangLite.CAT_BALOU_COUNT; i++) {
            this.deck.add(new CatBalou(this.currentPlayer, this.deck, this.targetPlayer, this.targetPlayerDeck));
        }
        for (int i = 0; i < BangLite.STAGECOACH_COUNT; i++) {
            this.deck.add(new Stagecoach(this.currentPlayer, this.deck));
        }
        for (int i = 0; i < BangLite.INDIANS_COUNT; i++) {
            this.deck.add(new Indians(this.currentPlayer, this.deck, this.players));
        }
    }
}
