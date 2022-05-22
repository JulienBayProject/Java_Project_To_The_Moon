package model.game;

import java.util.ArrayList;
import java.util.List;


public class Credits {
	
	private List<Credit> credits;

	/**
	 * Contructeur de la liste de crédit
	 */
	public Credits() {
		credits = new ArrayList<Credit>();
	}
	
	/**
	 * renvoit une copie de la liste de crédit
	 * @return
	 */
	public List<Credit> getCredits() {
		List<Credit> clone = new ArrayList<Credit>();
		for (Credit c : credits) {
			clone.add(c);
		}
		return clone;
	}

	/**
	 * 
	 * Utilisation d'une classe interne étant donné que seul la classe englobante travaillera avec
	 * De plus, au vu du peu de code, ceci ne nuit pas à la lisibilité du code
	 *
	 */
	public class Credit{
		private String author;
		private String website;
		private String description;
	
		/**
		 * Conctructeur d'un credit
		 * @param author String
		 * @param website String
		 * @param description String
		 */
		public Credit(String author, String website, String description) {
			this.author = author;
			this.website = website;
			this.description = description;
		}
		
		/**
		 * renvoit le nom de l'autheur
		 * @return String
		 */
		public String getAuthor() {
			return author;
		}

		/**
		 * renvoit le lien url de la source
		 * @return string
		 */
		public String getWebsite() {
			return website;
		}

		/**
		 * renvoit la description de l'utilisation de la ressource
		 * @return String
		 */
		public String getDescription() {
			return description;
		}
	}
	
}
