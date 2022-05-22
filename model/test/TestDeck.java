package model.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.deck.Card;
import model.deck.Category;
import model.deck.Deck;
import model.deck.Question;

class TestDeck {

    private Deck deck;
    private List<Card> cards;
    private Card card;
    private Question q;

    @SuppressWarnings("unchecked")
    @BeforeEach
    void setUp() throws Exception {
        deck = new Deck();
        Field field = deck.getClass().getDeclaredField("cards");
        field.setAccessible(true);
        cards = (List<Card>) field.get(deck);
        card = new Card();
        q = new Question("test", Category.COMPUTER, "test");
        q.addChoice("test1", false);
        q.addChoice("test2", false);
        q.addChoice("testORIGINEL", true);
    }

    @AfterEach
    void tearDown() throws Exception {
        deck = null;
        cards = null;
        card = null;
        q = null;
    }

    @Test
    void testAddCard() {
        assertTrue(deck.addCard(card),"la carte est bien ajouté");
        assertFalse(cards.isEmpty(),"la liste n'est pas vide");
        assertEquals(1, cards.size(),"la taille du deck doit être de 1");
        assertFalse(deck.addCard(card),"il ne peut pas être ajouté car il est déjà présent de le deck");
        assertFalse(deck.addCard(null),"il ne doit pas être ajouté car c'est null");
        assertEquals(1, cards.size(),"il doit être à 1 même après l'ajout d'un null");
    }


}