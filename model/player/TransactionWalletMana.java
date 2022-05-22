package model.player;

public class TransactionWalletMana extends TransactionWalletAbstract{

	private Player player;
	
	public TransactionWalletMana(Player p) {
		this.player=p;
	}
	@Override
	public int getWalletPlayer() {
		return player.getWalletPlayer();
	}

	@Override
	public void addMoney(int m) {
		player.setWalletPlayer(player.getWalletPlayer() + m);
	}

	@Override
	public void removeMoney(int m) {
		player.setWalletPlayer(player.getWalletPlayer() - m);
	}

	@Override
	public boolean purchasePossible(int m) {
		if ( (player.getWalletPlayer() - m) >= 0 )
			return true;
		else 
			return false;
	}
}
