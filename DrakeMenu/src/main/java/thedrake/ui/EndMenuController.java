package thedrake.ui;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import thedrake.src.GameResult;

public class EndMenuController
{
    public void onNewGamePressed(ActionEvent event)
    {
        GameResult.gameSreen = new GameScreen(GameResult.createSampleGameState());
        GameResult.GameScene = new Scene(GameResult.gameSreen, 800, 800);
        GameResult.setResult(GameResult.IN_PLAY);
    }
    public void onEndPressed(ActionEvent event)
    {
        System.exit(0);
    }
}
