package sk.stuba.fei.uim.oop.game.player;

import sk.stuba.fei.uim.oop.game.BangLite;
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
    private final List<BlueCard> cardsOnTable;

    private static final int START_LIVES_COUNT = 4;

    public Player(String name) {
        this.name = name;
        this.lives = Player.START_LIVES_COUNT;
        this.cardsInHand = new ArrayList<>();
        this.cardsOnTable = new ArrayList<>();
    }

    public boolean dealWithBang(BangLite bangLite) {
        //barrel
        for (BlueCard card : this.cardsOnTable) {
            if (card instanceof Barrel) {
                if (card.hasEffect()) {
                    return this.isAlive();
                }
            }
        }

        //missed
        for (Card card : this.cardsInHand) {
            if (card instanceof Missed) {
                card.play(bangLite);
                return this.isAlive();
            }
        }

        //live
        this.removeLive();
        return this.isAlive();
    }

    public boolean dealWithIndians(List<Card> deck) {
        boolean bangFound = false;
        for (Card card : this.cardsInHand) {
            if (card instanceof Bang) {
                this.cardsInHand.remove(card);
                deck.add(card);
                bangFound = true;
                break;
            }
        }
        if (!bangFound) {
            this.removeLive();
        }
        return this.isAlive();
    }

    public void drawCards(int numberOfCards, List<Card> deck) {
        for (int i = 0; i < numberOfCards; i++) {
            this.cardsInHand.add(deck.remove(0));
        }
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

    public List<BlueCard> getCardsOnTable() {
        return this.cardsOnTable;
    }
}
