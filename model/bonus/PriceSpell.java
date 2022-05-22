package model.bonus;

public enum PriceSpell {
	FIFTY_FIFTY(60),
	CHOOSE_CAT(100);
	
	private int price;
	
	PriceSpell(int i){
		this.price = i;
	}
	
	public int getPrice() {
		return price;
	}

}
