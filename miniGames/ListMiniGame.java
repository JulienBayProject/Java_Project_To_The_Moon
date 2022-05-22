package miniGames;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import miniGames.rushSpatial.RushSpatial;

public class ListMiniGame {

	
	private List<MiniGameAbstractSP> minigames;
	
	public ListMiniGame() {
		minigames = new ArrayList<MiniGameAbstractSP>();
		
		minigames.add(new RushSpatial());
	}
	
	public MiniGameAbstractSP getRandomGame() {
		return minigames.get(new Random().nextInt(minigames.size()));
	}
}
