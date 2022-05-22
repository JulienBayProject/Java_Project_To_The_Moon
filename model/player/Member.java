package model.player;

import com.google.gson.annotations.Expose;

import exception.NickNameLongerException;

public class Member extends Player{
	@Expose
	private int walletPlayerPO;
	@Expose
	private String password;
	
	final static int MONEY_GIFT_MEMBER = 500;
	
	/**
	 * Cree un nouveau membre avec un montant cadeau dans son portefeuille
	 * @param nickName String surnom
	 * @param psw String passWord
	 * @throws NickNameLongerException
	 */
	public Member(String nickName, String psw) throws NickNameLongerException {
		super(nickName);
		this.walletPlayerPO = MONEY_GIFT_MEMBER;
		this.password = psw;
		this.setTransaction(new TransactionWalletPo(this));
	}
	
	/**
	 * renvoit la valeur de l'attribut WalletPlayerPO
	 * @return int
	 */
	public int getWalletPlayerPO() {
		return walletPlayerPO;
	}
	
	/**
	 * donne la valeur passee en argument ) l'attribut walletPlayerPO
	 * @param po int
	 */
	public void setWalletPlayerPO(int po) {
		this.walletPlayerPO = po;
	}
	
	/**
	 * renvoit la valeur de l'attribut  password
	 * @return String
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * donne la valeur passee en argument a l'attribut
	 * @param password String
	 */
	public void setPassword(String password) {
		this.password = password;
	}


	/**
	 * renvoit une copie du membre
	 * @return Member
	 */
	public Member clone() {
		try {
			return new Member(this.getNickName(), this.getPassword());
		} catch (NickNameLongerException e) {
			e.printStackTrace();
		}
		return null;
	}

	
	
	
}
