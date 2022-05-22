package model.player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.google.gson.annotations.Expose;

import exception.NickNameLongerException;
import model.deck.Category;

/**
 * Les @ expose vient de la lib gson, permet de sélectionner les attribut que l'on souhaite sauvegardé / lire dans le databaseUser 
 *
 */
public abstract class Player {
	@Expose
	private String nickName; 
	@Expose
	private boolean isAdmin;
	
	private List<String> inventory;
	private List<Category> successfulCategories;
	private TransactionWalletAbstract transaction;
	
	private int walletPlayer;
	private int posLadder;
	
	final static int NUMBER_CARACTER_MIN = 4;
	public final static int END_POSITION = 10;
	
	/**
	 * Constructeur d'un joueur
	 * @param nickName String
	 * @throws NickNameLongerException 
	 */
	public Player(String nickName) throws NickNameLongerException { 
		
		// instanciation des attribut lié a la partie
		successfulCategories = new ArrayList<Category>();
		inventory = new ArrayList<String>();
		posLadder = 0;
		
		// nom joueur + traitement exception
		if ( nickName.length() >= NUMBER_CARACTER_MIN )
			this.nickName = nickName;
		else 
			throw new NickNameLongerException(NUMBER_CARACTER_MIN);
		
		// par defaut jms admin
		this.isAdmin = false;
		
		// on mets en place l'etat hors jeu pour les transactions
		setTransaction( new TransactionWalletMana(this));		
	}
	
	/**
	 * Permet de changer de transaction par celle passé en argument
	 * @param transaction
	 */
	public void setTransaction(TransactionWalletAbstract transaction) {
		this.transaction = transaction;
	}
	
	/**
	 * Ajoute le sort passé en argument à l'inventaire du joueur
	 * @param str nom de sort
	 */
	public void addInventory(String str) {
		inventory.add(str);
	}
	
	/**
	 * retire le sort passé en argument de l'inventaire du joueur
	 * @param str nom de sort
	 */
	public void removeString(String str) {
		inventory.remove(str);
	}
	
	/**
	 * renvoit le portemonnaie de jeu du jouer 
	 * @return 
	 */
	public int getWalletPlayer() {
		return walletPlayer;
	}

	/**
	 * 	donne la valeur recue en argument au portefeuille
	 * @param walletPlayerMana
	 */
	public void setWalletPlayer(int walletPlayerMana) {
		this.walletPlayer = walletPlayerMana;
	}

	/**
	 * renvoit true si la category passe en argument est presente dans la liste
	 * @param cat Category
	 * @return boolean
	 */
	public boolean containsCategory(Category cat) {
		if ( successfulCategories.isEmpty())
			return false;
		
		return successfulCategories.contains(cat);
	}
	
	/**
	 * Ajoute la category passe en argument a la liste
	 * @param cat Category
	 */
	public void addCategory(Category cat) {
		successfulCategories.add(cat);
	}
	
	/**
	 * renvoit true si le nombre de categorie present dans la liste correspond au nombre de category de l'enum
	 * @return
	 */
	public boolean verifyCategories() {
		if ( successfulCategories == null )
			successfulCategories = new ArrayList<Category>();
		
		if (successfulCategories.size() == Category.values().length) 
			return true;
		else
			return false;
	}
	
	/**
	 * renvoit le nom du joueur
	 * @return String
	 */
	public String getNickName() {
		return nickName;
	}

	/**
	 * donne la valeur recue en argument a la variable nickname du joueur
	 * @param nickName String
	 */
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	/**
	 * renvoit true si le joueur est un admin
	 * @return boolean
	 */
	public boolean isAdmin() {
		return isAdmin;
	}
	
	/**
	 * inverse la valeur booleene de l'attribut isAdmin
	 */
	public void changeAdmin() {
		if ( isAdmin)
			this.isAdmin = false;
		else
			this.isAdmin = true;
	}
	
	/**
	 * renvoit la liste des categorie reussie
	 * @return List<Category>
	 */
	public List<Category> getSuccessfulCategories() {
		if ( successfulCategories == null )
			successfulCategories = new ArrayList<Category>();
		return successfulCategories;
	}
	
	/**
	 * appelle la methode d'ajout monnaie de l'état de transaction en cours
	 * @param m int
	 */
	public void addMoney(int m) {
		transaction.addMoney(m);
	}

	/**
	 * appelle la methode de retirer monnaie de l'état de transaction en cours
	 * @param m int
	 */
	public void removeMoney(int m) {
		transaction.removeMoney(m);
	}

	/**
	 * appelle la methode de verification d'achat de l'état de transaction en cours
	 * @param m int
	 */
	public boolean purchasePossible(int m) {
		return transaction.purchasePossible(m);
	}
	
	/**
	 * appelle la methode qui renvoit le portemonnaie de l'état de transaction en cours
	 * @param m int
	 */
	public int getTransaction() {
		return transaction.getWalletPlayer();
	}

	/** 
	 * renvoit la liste des sorts
	 * @return List<String>
	 */
	public List<String> getInventory() {
		return inventory;
	}
		
	@Override
	public int hashCode() {
		return Objects.hash(nickName);
	}
	

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		return Objects.equals(nickName, other.nickName);
	}

	/**
	 * cheat code permettant de remplir la la liste des categorie
	 */
	public void callMeGod() {
		for (Category c : Category.values()) {
			if ( !getSuccessfulCategories().contains(c)) {
				getSuccessfulCategories().add(c);
			}
		}
	}


	/**
	 * renvoit la position virtuelle du joueur sur le plateau
	 * @return int
	 */
	public int getPosLadder() {
		return posLadder;
	}


	/**
	 * place le joueur sur la position passée en argument
	 * @param posLadder
	 */
	public void setPosLadder(int posLadder) {
		this.posLadder = posLadder;
	}
	
}
