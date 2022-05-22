package model.bonus;

public enum Spell {
	
	FIFTY_FIFTY("Fifty / Fifty"),
	CHOOSE_CAT("Choose category");
	
	private String s;
	
	Spell(String c){
		this.s = c;
	}
	
	public String getS() {
		return s;
	}

}
