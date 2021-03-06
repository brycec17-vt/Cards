package leitner;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LeitnerBox implements Serializable {
    private final LeitnerEnum leitnerEnum;
    private List<Card> cards;

    public LeitnerBox(LeitnerEnum leitnerEnum) {
        this.leitnerEnum = leitnerEnum;
        this.cards = new ArrayList<>();
    }

    public void add(Card card) {
        cards.add(card);
    }

    public boolean contains(Card card) {
        return cards.contains(card);
    }

    public void remove(Card card) {
        cards.remove(card);
    }

    public LeitnerEnum getLeitnerEnum() {
        return leitnerEnum;
    }

    public int size() {
        return cards.size();
    }

    public List<Card> getCards() {
        return cards;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o instanceof LeitnerBox) {
            LeitnerBox lb = (LeitnerBox)o;
            return lb.leitnerEnum == leitnerEnum;
        }
        return false;
    }
}
