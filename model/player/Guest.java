package model.player;

import exception.NickNameLongerException;

	public class Guest extends Player {
		
	private static int number = 0;
	/**
	 * cree une instance d'un joueur invite
	 * @throws NickNameLongerException
	 */
	public Guest() throws NickNameLongerException {
		super("Guest_"+ ++number);
	}
}
