package model.player;

public abstract class TransactionWalletAbstract {
	
	/**
	 * prévu pour renvoyer la portefeuille dans l'etat concret
	 * @return int
	 */
	public abstract int getWalletPlayer();
	
	/**
	 * prévu pour ajouter la valeur de l'argument au portefeuille dans l'etat concret
	 */
	public abstract void addMoney(int m);
	
	/**
	 * prévu pour retirer la valeur de l'argument au portefeuille dans l'etat concret
	 */
	public abstract void removeMoney(int m);
	
	/**
	 * prévu pour vérifier la possibilité d'un achat en fonctont du porte de feuille de l'etat
	 * @param m int
	 * @return true si l'achat est possible
	 */
	public abstract boolean purchasePossible(int m);

}
