package model.deck;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Question {

	private String author;
	private Category category;
	private String interrogation;
	private Map<String, Boolean> choices;

	/**
	 * Constructeur d'une question
	 * @param author
	 * @param category
	 * @param interrogation
	 */
	public Question(String author, Category category, String interrogation) {
		super();
		this.author = author;
		this.category = category;
		this.interrogation = interrogation;
		choices = new HashMap<String, Boolean>();
	}

	/**
	 * Renvoie la valeur du code de hachage pour cette carte
	 */
	@Override
	public int hashCode() {
		return Objects.hash(author, category, choices, interrogation);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Question other = (Question) obj;
		return category == other.category && interrogation.equals(other.interrogation);
	}

	/**
	 * Renvoit l'auteur de la question
	 * @return String
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * Renvoit la category lié a la question
	 * @return Category
	 */
	public Category getCategory() {
		return category;
	}

	/**
	 * renvoit l'interrogation lié à la question
	 * @return String
	 **/
	public String getInterrogation() {
		return interrogation;
	}


	/**
	 * renvoit une copie de la HaspMap contenant les choix de réponses
	 * @return Map<String, Boolean>
	 */
	public Map<String, Boolean> getChoices() {
		Map<String, Boolean> choicesClone = new HashMap<String, Boolean>();

		for (Map.Entry<String, Boolean> entry : choices.entrySet())
			choicesClone.put(entry.getKey(), entry.getValue());

		return choicesClone;
	}
	

	/**
	 * Ajoute un choix à la liste des choix
	 * @param key String 
	 * @param value boolean
	 */
	public void addChoice(String key, boolean value) {
		choices.put(key, value);
	}

	/**
	 * renvoit un clone de la question
	 */
	public Question clone() {
		return new Question(getAuthor(), getCategory(), getInterrogation());
	}

}

