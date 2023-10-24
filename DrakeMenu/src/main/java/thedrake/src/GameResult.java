package thedrake.src;

import javafx.scene.Scene;
import thedrake.ui.GameScreen;

import java.io.PrintWriter;

public enum GameResult implements JSONSerializable {
    VICTORY, DRAW, IN_PLAY, MENU;

    public static Scene GameScene;

    public static GameScreen gameSreen;

    public static boolean changed = false;

    public static GameResult result = null;

    public static void setResult(GameResult newRes)
    {
        changed = true;
        result = newRes;
    }

    public static GameState createSampleGameState() {
        Board board = new Board(4);
        PositionFactory positionFactory = board.positionFactory();
        board = board.withTiles(new Board.TileAt(positionFactory.pos(1, 1), BoardTile.MOUNTAIN));
        return new StandardDrakeSetup().startState(board);
    }

    @Override
    public void toJSON(PrintWriter writer)
    {
        writer.print("\"" + this + "\"");
    }
}


