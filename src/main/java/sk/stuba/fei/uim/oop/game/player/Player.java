package sk.stuba.fei.uim.oop.game.player;

import sk.stuba.fei.uim.oop.game.GameValues;
import sk.stuba.fei.uim.oop.game.cards.Card;
import sk.stuba.fei.uim.oop.game.cards.blueCards.Barrel;
import sk.stuba.fei.uim.oop.game.cards.blueCards.BlueCard;
import sk.stuba.fei.uim.oop.game.cards.brownCards.Bang;
import sk.stuba.fei.uim.oop.game.cards.brownCards.Missed;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private final String name;
    private int lives;
    private final List<Card> cardsInHand;
    private final List<Card> cardsOnTable;

    private static final int START_LIVES_COUNT = 4;

    public Player(String name) {
        this.name = name;
        this.lives = Player.START_LIVES_COUNT;
        this.cardsInHand = new ArrayList<>();
        this.cardsOnTable = new ArrayList<>();
    }

    public boolean dealWithBang() {
        //barrel
        for (Card card : this.cardsOnTable) {
            if (card instanceof Barrel) {
                if (((BlueCard) card).hasEffect()) {
                    this.informUsingCard(card);
                    return this.isAlive();
                }
            }
        }

        //missed
        for (Card card : this.cardsInHand) {
            if (card instanceof Missed) {
                card.play();
                this.informUsingCard(card);
                return this.isAlive();
            }
        }

        //life
        this.removeLive();
        this.informLoosingLife();
        return this.isAlive();
    }

    public boolean dealWithIndians(List<Card> pile) {
        boolean bangFound = false;
        List<Card> toRemove = new ArrayList<>();
        for (Card card : this.cardsInHand) {
            if (card instanceof Bang) {
                toRemove.add(card);  // this.cardsInHand.remove(card);  ConcurrentModificationException
                pile.add(card);
                bangFound = true;
                this.informUsingCard(card);
                break;
            }
        }
        this.cardsInHand.removeAll(toRemove);
        if (!bangFound) {
            this.removeLive();
            this.informLoosingLife();
        }
        return this.isAlive();
    }

    public void drawCards(int numberOfCards, GameValues game) {
        this.cardsInHand.addAll(game.drawCards(numberOfCards));
    }

    public int getLives() {
        return this.lives;
    }

    public void addLive() {
        this.lives++;
    }

    public void removeLive() {
        if (lives > 0) {
            this.lives--;
        }
    }

    public boolean isAlive() {
        return this.lives > 0;
    }

    public String getName() {
        return this.name;
    }

    public List<Card> getCardsInHand() {
        return this.cardsInHand;
    }

    public List<Card> getCardsOnTable() {
        return this.cardsOnTable;
    }

    private void informUsingCard(Card card) {
        System.out.println("> " + this.getName() + " used " + card.getClass().getSimpleName() + ".");
    }

    private void informLoosingLife() {
        System.out.println("> " + this.getName() + " lost a life.");
    }
}
