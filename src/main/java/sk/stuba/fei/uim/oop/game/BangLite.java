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
    private final GameValues values;

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
        this.values = new GameValues();
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
            System.out.println("\nTurn of " + this.values.getCurrentPlayer().getName() + ":");
            this.checkEffects();
            if (this.values.getPlayerCanPlay()) {
                this.printTable();
                this.drawCards();
                this.playCards();
                this.discardExcessCards();
            }
            this.removeDeathPlayers();
            this.values.setCurrentPlayer(this.nextPlayer());
        }
        this.showWinner();
    }

    private void printTable() {
        System.out.println();
        this.values.getPlayers().forEach(player -> {
            System.out.println(player.getName() + (Objects.equals(player, this.values.getCurrentPlayer()) ? " (you):" : ":"));
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
        for (int i = 0; i < this.values.getCurrentPlayer().getCardsInHand().size(); i++) {
            System.out.print("[" + (i + 1) + "] " + this.values.getCurrentPlayer().getCardsInHand().get(i).getClass().getSimpleName() + " ");
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
        for (Player player : this.values.getPlayers()) {
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
        this.values.getPlayers().removeAll(this.collectCardOfDeathPlayers());
    }

    private List<Player> collectCardOfDeathPlayers() {
        List<Player> deathPlayers = new ArrayList<>();
        this.values.getPlayers().forEach(player -> {
            if (!player.isAlive()) {
                if (!player.getCardsOnTable().isEmpty()) {
                    this.values.getDiscardPile().addAll(player.getCardsInHand());
                    player.getCardsInHand().clear();
                }
                if (!player.getCardsOnTable().isEmpty()) {
                    this.values.getDiscardPile().addAll(player.getCardsOnTable());
                    player.getCardsOnTable().clear();
                }
                deathPlayers.add(player);
            }
        });
        return deathPlayers;
    }

    private void discardExcessCards() {
        Player player = this.values.getCurrentPlayer();
        while (player.getCardsInHand().size() > player.getLives()) {
            int position;
            do {
                this.printHand();
                position = KeyboardInput.readInt("Enter the card to discard");
            } while (!(position > 0 && position <= player.getCardsInHand().size()));

            player.getCardsInHand().remove(position - 1);
        }
    }

    private void playCards() {
        int position;  // 0 - play no card
        do {
            this.printHand();
            Player currentPlayer = this.values.getCurrentPlayer();
            do {
                position = KeyboardInput.readInt("Enter the card to play or 0 to end the turn");
            } while (!(position >= 0 && position <= currentPlayer.getCardsInHand().size()));

            if (position > 0) {
                Card card = currentPlayer.getCardsInHand().get(position - 1);
                if (card.requireTargetPlayer()) {
                    List<Player> targetPlayers = new ArrayList<>(this.values.getPlayers());
                    targetPlayers.remove(currentPlayer);
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
                    this.values.setTargetPlayer(targetPlayers.get(decision - 1));
                }
                if (card.requireTargetPlayerDeck()) {
                    if (card.isPlayable()) {
                        Player targetPlayer = this.values.getTargetPlayer();
                        boolean inHand = !targetPlayer.getCardsInHand().isEmpty();
                        boolean onTable = !targetPlayer.getCardsOnTable().isEmpty();
                        if (inHand && !onTable) {
                            this.values.setTargetPlayerDeck(targetPlayer.getCardsInHand());
                        } else if (onTable && !inHand) {
                            this.values.setTargetPlayerDeck(targetPlayer.getCardsOnTable());
                        } else {
                            int decision;
                            do {
                                decision = KeyboardInput.readInt("Enter 1 for removing the card from the hand or 2 for removing the card from the table");
                                this.values.setTargetPlayerDeck(decision == 1 ? targetPlayer.getCardsInHand() : targetPlayer.getCardsOnTable());
                            } while (!(decision >= 1 && decision <= 2));
                        }
                    }
                }
                if (card.isPlayable()) {
                    card.play();
                } else {
                    System.out.println("This card can not be played" + (this.values.getTargetPlayer() != null ? " on this player." : "."));
                }
                if (this.values.getTargetPlayer() != null && !this.values.getTargetPlayer().isAlive()) {
                    System.out.println(this.values.getTargetPlayer().getName() + " lost!");
                }
            }
            this.values.setTargetPlayer(null);
            this.values.setTargetPlayerDeck(null);
            this.printTable();
        } while (position != 0);
    }

    private void drawCards() {
        this.values.getCurrentPlayer().getCardsInHand().addAll(this.values.drawCards(BangLite.CARDS_TO_DRAW_AT_THE_START_OF_TURN_COUNT));
    }

    private void checkEffects() {
        this.values.setPlayerCanPlay(true);
        List<Card> toRemove = new ArrayList<>();
        List<Card> cardsOnTable = this.values.getCurrentPlayer().getCardsOnTable();
        cardsOnTable.forEach(card -> {
            if (card instanceof Dynamite) {
                this.values.setPlayerCanPlay(this.values.getPlayerCanPlay() && ((BlueCard) card).applyEffect(toRemove));
            }
        });
        cardsOnTable.forEach(card -> {
            if (card instanceof Prison) {
                this.values.setPlayerCanPlay(this.values.getPlayerCanPlay() && ((BlueCard) card).applyEffect(toRemove));
            }
        });
        cardsOnTable.removeAll(toRemove);
    }

    private Player nextPlayer() {
        List<Player> players = this.values.getPlayers();
        return players.get(
                (players.indexOf(this.values.getCurrentPlayer()) + 1) % players.size()
        );
    }

    private int playersAlive() {
        int playersAlive = 0;
        for (Player player : this.values.getPlayers()) {
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
            this.values.getPlayers().add(new Player("Player" + (i + 1)));
        }

        this.values.getPlayers().forEach(player -> player.drawCards(BangLite.INITIAL_PLAYER_CARDS_COUNT, this.values));
        this.values.setCurrentPlayer(this.values.getPlayers().get(0));
    }

    private void shuffleDeck() {
        Collections.shuffle(this.values.getDeck());
    }

    private void createDeck() {
        this.addBlueCards();
        this.addBrownCards();
    }

    private void addBlueCards() {
        List<Card> deck = this.values.getDeck();
        for (int i = 0; i < BangLite.BARREL_COUNT; i++) {
            deck.add(new Barrel(this.values));
        }
        for (int i = 0; i < BangLite.DYNAMITE_COUNT; i++) {
            deck.add(new Dynamite(this.values));
        }
        for (int i = 0; i < BangLite.PRISON_COUNT; i++) {
            deck.add(new Prison(this.values));
        }
    }

    private void addBrownCards() {
        List<Card> deck = this.values.getDeck();
        for (int i = 0; i < BangLite.BANG_COUNT; i++) {
            deck.add(new Bang(this.values));
        }
        for (int i = 0; i < BangLite.MISSED_COUNT; i++) {
            deck.add(new Missed(this.values));
        }
        for (int i = 0; i < BangLite.BEER_COUNT; i++) {
            deck.add(new Beer(this.values));
        }
        for (int i = 0; i < BangLite.CAT_BALOU_COUNT; i++) {
            deck.add(new CatBalou(this.values));
        }
        for (int i = 0; i < BangLite.STAGECOACH_COUNT; i++) {
            deck.add(new Stagecoach(this.values));
        }
        for (int i = 0; i < BangLite.INDIANS_COUNT; i++) {
            deck.add(new Indians(this.values));
        }
    }
}
