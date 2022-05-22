package model.player;

public class TransactionWalletPo extends TransactionWalletAbstract{

	private Member member;
	
	
	public TransactionWalletPo(Member member) {
		this.member = member;
	}

	@Override
	public int getWalletPlayer() {
		return member.getWalletPlayerPO();
	}

	@Override
	public void addMoney(int m) {
		member.setWalletPlayerPO(member.getWalletPlayerPO() + m);
	}

	@Override
	public void removeMoney(int m) {
		member.setWalletPlayerPO(member.getWalletPlayerPO() - m);
	}

	@Override
	public boolean purchasePossible(int m) {
		if ( (member.getWalletPlayerPO() - m) >= 0 )
			return true;
		else 
			return false;
	}

}
