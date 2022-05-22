package model.deck;

import java.util.ArrayList;
import java.util.List;

public class Card {

	private static int NB_MAX_QUESTION = 6;
	private List<Question> questions;
	
	/**
	 * Contructeur d'une Card, composé d'une liste de question
	 */
	public Card() {
		questions = new ArrayList<Question>();
	}
	
	/**
	 * Constructeur d'une Card, composé d'une liste de question recue en argument
	 * @param q
	 */
	private Card(List<Question> q) {
		questions = new ArrayList<>();
		for (Question question : q) {
			questions.add(question.clone());
		}
	}
	
	/**
	 * Ajout de la question recue si celle ci n'est pas presente ou 
	 * @param q Question 
	 */
	public void addQuestion(Question q) {
		if(!questions.contains(q) && questions.size() <= NB_MAX_QUESTION) {
			questions.add(q);
		}
	}
	
	/**
	 * Suppression de la question recue en argument
	 * @param q Question
	 */
	public void removeQuestion(Question q) {
		questions.remove(q);
	}
	
	/**
	 * Renvoit la question correspondant a la catégorie recue en argument
	 * @param cat Catégorie souhaitée
	 * @return Question
	 */
	public Question getQuestionByCategory(Category cat) {
		for ( Question q : questions) {
			if (q.getCategory().equals(cat))
				return q;
		}
		return null;
	}
	
	/**
	 * Redefinission du equals
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Card other = (Card) obj;
		
		for ( Question q : questions) {
			for (Question qO : other.getQuestions()) {
				if ( q.equals(qO))
					return true;
			}
		}
		return false;
	}

	/**
	 * Renvoit un clone de la card
	 */
	public Card clone() {
		return new Card(questions);
	}
	
	/**
	 * Renvoit une copie de la liste de question 
	 * @return
	 */
	public List<Question> getQuestions() {
		List<Question> clone = new ArrayList<Question>();
		for (Question q : questions) {
			clone.add(q);	
		}
		return questions;
	}
	
}
