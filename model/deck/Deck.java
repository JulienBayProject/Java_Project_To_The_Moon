package model.deck;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Deck {

	private List<Card> cards;
	
	/**
	 * Constructeur d'un deck composé d'une liste de Card
	 */
	public Deck() {
		super();
		this.cards = new ArrayList<Card>();
	}	
	

	/**
	 * Constructeur d'un deck composé d'une liste de Card
	 */
	private Deck(List<Card> q) {
		cards = new ArrayList<>();
		for (Card card : q) {
			cards.add(card.clone());
		}
	}
	
	/**
	 * Méthode qui vérifie le format des questions dans chaque carte présente dans le deck
	 * @return true si tout est correct
	 */
	public boolean verifyDeck() {
		for (Card c : cards) {
			for (Question q : c.getQuestions()) {
				if (q.getAuthor() == null )
					return false;
				if (q.getCategory() == null)
					return false;
				for (Map.Entry<String, Boolean>choise : q.getChoices().entrySet()) {
					if (choise.getValue() == null || choise.getKey() == null)
						return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * Parcourt la liste recue en argument et ajoute la card si celle ci n'est pas présente ou non null
	 * @param decks list<Card>
	 */
	public void addListCard(List<Card> decks) {
		for (Card c : decks) {
			if(!cards.contains(c) && c!=null) 
				cards.add(c);
		}
	}

	/**
	 * Ajoute la carte recue en argument au deck si  celle ci n'est pas présente ou non null
	 * @param c Card
	 * @return true si la carte a été ajoutée
	 */
	public boolean addCard(Card c) {
		if(!cards.contains(c) && c!=null) {
			cards.add(c);
			return true;
		}
		return false;
	}
	
	/**
	 * Retire la carte passée en argument du deck
	 * @param c
	 */
	public void removeCard(Card c) {
		cards.remove(c);
	}
	
	/**
	 * Mélange les cartes du deck
	 */
	public void randomSortDeck() {
		Collections.shuffle( cards );
	}
	
	/**
	 * renvoit la carte presente a l'index donné en argument
	 * @param index - position de la carte 
	 * @return Card
	 */
	public Card getCard(int index) {
		return cards.get(index);
	}
	
	/**
	 * Clone le deck
	 */
	public Deck clone() {
		return new Deck(cards);
	}
	
	/**
	 * Renvoit le nombre de carte presente dans le deck
	 * @return
	 */
	public int deckSize() {
		return cards.size();
	}

	/**
	 * Renvoit une copie du deck
	 * @return
	 */
	public List<Card> getCards() {
		List<Card> clone = new ArrayList<Card>();
		for (Card c : cards) {
			clone.add(c);
		} 
		return clone;
	}
	
	
}
