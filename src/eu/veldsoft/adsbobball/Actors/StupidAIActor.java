package eu.veldsoft.adsbobball.Actors;

import android.graphics.PointF;
import eu.veldsoft.adsbobball.TouchDirection;
import eu.veldsoft.adsbobball.GameLogic.Ball;
import eu.veldsoft.adsbobball.GameLogic.GameEventStartBar;
import eu.veldsoft.adsbobball.GameLogic.GameManager;
import eu.veldsoft.adsbobball.GameLogic.GameState;
import eu.veldsoft.adsbobball.GameLogic.Grid;

import java.util.Random;

public class StupidAIActor extends Actor {
	private static final int ACTION_INTERVALL = 128;

	private int lastAction;

	public StupidAIActor(GameManager gameManager, int[] playerIds) {
		super(gameManager, playerIds);
		lastAction = 0;
	}

	@Override
	public float getExecFreq() {
		return 1.0f;
	}

	@Override
	public void reset() {
		lastAction = 0;
	}

	@Override
	public void run() {
		if (gameManager.getGameTime() <= lastAction + ACTION_INTERVALL) {
			return;
		}
		lastAction = gameManager.getGameTime();
		Random randomGenerator = new Random(lastAction);

		GameState gameState = gameManager.getCurrGameState();
		Grid grid = gameState.getGrid();

		for (int pid : playerIds) {
			PointF p;

			int tries = 20;
			TouchDirection dir = (randomGenerator.nextBoolean() ? TouchDirection.HORIZONTAL
					: TouchDirection.VERTICAL);
			do {
				tries--;
				float xPoint;
				float yPoint;
				xPoint = randomGenerator.nextFloat() * (grid.getWidth() * 0.5f)
						+ (grid.getWidth() * 0.25f);
				yPoint = randomGenerator.nextFloat()
						* (grid.getHeight() * 0.5f)
						+ (grid.getHeight() * 0.25f);
				p = new PointF(xPoint, yPoint);

				if (grid.getGridSq(xPoint, yPoint) != Grid.GRID_SQUARE_CLEAR) {
					p = null;
					continue;
				}
				for (Ball ball : gameState.getBalls()) {
					if (ball.collide(grid.getGridSquareFrameContainingPoint(p))) {
						p = null;
						break;
					}
				}
			} while (tries > 0);
			if (p != null)
				gameManager.addEvent(new GameEventStartBar(gameManager
						.getGameTime() + 1, p, dir, pid));
		}
	}
}
