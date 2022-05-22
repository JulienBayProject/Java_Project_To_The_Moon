package model.game;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import model.deck.Category;
import model.deck.CategorySpecial;
import model.deck.Deck;
import model.deck.Question;
import model.player.Player;
import model.player.TransactionWalletMana;
import persistence.Persistence;

public class DuringGame  {
	
	/**
	 * decks : liste de card avec lesquelles les joueurs vont jouer
	 * players : liste des joueurs en jeu
	 * playerActive : joueur qui doit jouer
	 * walletCoin : porte-monnaie du joueur pendant une partie
	 * successfullCategory : liste par joueur des categories rÃ©pondues correctement
	 * numberTurns : nombre de tour de jeu
	 * indexCard : index de la carte a jouer
	 */
	private Deck decks;
	private Player playerActive;
	private List<Player> players;
	private HashMap<Player, Integer> walletCoins;
	private int indexCard;
	
	private List<String> categoriesString;
	
//    Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
//    int height = (int)dimension.getHeight();
//    int width  = (int)dimension.getWidth();
	
	/**
	 * Constructeur 
	 * @param p - Liste des joueurs
	 */
	public DuringGame(List<Player> p) {
		// on commence par le tour 0 et card 0
	//	this.numberTurns = 0;
		this.indexCard = 0;
		
		// on recupere la liste des joueurs ensuite on la melange
		players = p;
		Collections.shuffle(players);
		
		// le joueur actif sera le 1er de la liste melangee
		this.playerActive = players.get(0);
		
		
		// creation et initialiation a 0 des portefeuille joueur
		this.walletCoins = new HashMap<Player, Integer>();
		initWalletCoin();
		
		// on recupere le deck et on melange les cards; et on écrit en console ceux rejeté
		decks =new Deck();
		File folder = new File("./decks_json");
		Deck transfert = new Deck();
		System.out.println("Deck rejected");
		for (File file : folder.listFiles()) {
			if (!file.isDirectory()) {
				transfert = Persistence.readingJson("./decks_json/"+file.getName(), Deck.class);
				
				if ( transfert.verifyDeck() && transfert.deckSize() !=0)
					decks.addListCard(transfert.getCards());
				else
					System.out.println("  "+file.getName());
				
			} 
		}
		decks.randomSortDeck();
		
		categoriesString = setCategoriesString();
	}
	
	/**
	 * crée une List avec les .name des enum Category et CategorSpecial
	 * @return List<String>
	 */
	public List<String> setCategoriesString() {
		if ( categoriesString == null ) {
			categoriesString = new ArrayList<String>();
		}
		for (CategorySpecial category : CategorySpecial.values()) {
			categoriesString.add(category.name());
		}
		for (Category category : Category.values()) {
			categoriesString.add(category.name());
		}
		return categoriesString;
	}
	
	public List<String> getCategoriesString(){
		return categoriesString;
	}
	
	/**
	 * retourne une categorie au hasard
	 * @return String
	 */
	public String getRandomCategory() {
		Random rand = new Random();
		return categoriesString.get(rand.nextInt(categoriesString.size()));
	}

	/**
	 * initialise les gold joueur a 0 en dï¿½but de partie
	 */
	public void initWalletCoin() {
		
		for (int i = 0 ; i < players.size() ; i++) {
			//players.get(i).getSuccessfulCategories() 
			players.get(i).setTransaction(new TransactionWalletMana(players.get(i)));
			walletCoins.put(players.get(i), 0);
		}
	}
	
	public void changeTransactionWallet() {
		for (Player p : players ) {
			p.setTransaction(new TransactionWalletMana(p));
		}
	}
	
	/**
	 * return le joueur suivant, si dernier de la liste recommence a zero
	 */
	public Player nextPlayer() {
		Player p;
		int index = players.indexOf(this.playerActive) + 1;
		if (index == players.size() )
			p = players.get(0);
		else
			p = players.get(index);
		
		return p;
	}
	
	/**
	 * return le joueur precedent, si 1er de la liste recommence a la fin
	 */
	public Player beforePlayer() {
		Player p;
		int index = players.indexOf(this.playerActive) - 1;
		if (index == -1 )
			p = players.get(players.size() - 1);
		else
			p = players.get(index);
		
		return p;
	}
	
	public void setPlayerActive() {
		this.playerActive = nextPlayer();
	}
	
	
	public boolean check() {
		for (Player p : players) {
			if(p.getSuccessfulCategories().size()==6)
				return true;
			else
				return false;
		}
		return false;
	}
	
	public Question getQuestion(Category cat) {
		//return getCard().getQuestionByCategory(cat);
		if ( this.indexCard + 1  == decks.deckSize())
			this.indexCard = 0;
		return decks.getCard(++indexCard).getQuestionByCategory(cat);
	}
	
 	
	/**
	 * renvois clone du deck
	 * @return Deck
	 */
	public Deck getDecks() {
		return decks.clone();
	}
	
	/**
	 * renvois clone de la liste joueur
	 * @return List<Player>
	 */
	public List<Player> getPlayers(){
		List<Player> clone = new ArrayList<Player>();
		for (Player p : players) {
			clone.add(p);
		}
		return clone;
	}
	
	/**
	 * Getter du joueur actif
	 * @return Player
	 */
	public Player getPlayerActive() {
		return this.playerActive;
	}
	

	
	
	
	
	
	
	
	
	
	
	
	
	

}
