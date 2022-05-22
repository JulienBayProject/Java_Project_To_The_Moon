package model.game;

import java.util.ArrayList;
import java.util.List;

import exception.PlayerAlreadyPresentException;
import model.player.Player;

public class SetupGame{

	private List<Player> players;
	public static final int NB_PLAYER_MAX = 6;
	/**
	 * Constructeur qui crÃ©e la liste des joueur
	 */
	public SetupGame() {
		
		players = new ArrayList<Player>();
	}
	
	/**
	 * Ajoute un joueur a la liste
	 * @param p - Player
	 * @throws PlayerAlreadyPresentException - Joueur dÃ©jÃ  prÃ©sent dans la liste
	 */
	public boolean addPlayer(Player p) throws PlayerAlreadyPresentException {
		if ( !players.contains(p)) {
			players.add(p);
			return true;
		}
		else {
			throw new  PlayerAlreadyPresentException();
		}
	}
	
	
	public void removeAllPlayer(List<Player> l) {
		players.removeAll(l);
	}
	
	/**
	 * renvois un clone de la liste de joueur
	 * @return
	 */
	public List<Player> getClonePlayers() {
		List <Player>playerCopy = new ArrayList<Player>();
		for (Player player : players) {
			playerCopy.add(player);
		}
		return playerCopy;
	}
	
	/**
	 * Renvois le nombre de joueur prÃ©sent dans la liste joueur
	 * @return int
	 */
	public int getNumberPlayers() {
		return players.size();
	}
	
	/**
	 * lancement de la partie avec la liste des joueurs
	 * @return DuringGame
	 */
	public DuringGame startGame() {
		return new DuringGame(getClonePlayers());
	}
	
	/**
	 * Renvoit le joueur situé a l'index passé en argument
	 * @param index int
	 * @return Player
	 */
	public Player getPlayerIndex( int index ) {
		return players.get(index);
	}

	
}
