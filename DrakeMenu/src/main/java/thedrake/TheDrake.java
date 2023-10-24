package thedrake;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import thedrake.src.GameResult;
import thedrake.src.PlayingSide;
import thedrake.ui.GameScreen;

import java.io.IOException;

public class TheDrake extends Application {
    public static void main(String[] args) {
        launch();
    }


    @Override
    public void start(Stage stage) throws IOException {

        GameResult.gameSreen = new GameScreen(GameResult.createSampleGameState());
        GameResult.GameScene = new Scene(GameResult.gameSreen, 800, 800);

        Scene MainMenu  = new Scene (FXMLLoader.load(TheDrake.class.getResource("hello-view.fxml")), 800, 800);
        Scene OrangeWon = new Scene (FXMLLoader.load(TheDrake.class.getResource("OrangeWon.fxml")), 800, 800);
        Scene BlueWon   = new Scene (FXMLLoader.load(TheDrake.class.getResource("BlueWon.fxml")), 800, 800);
        Scene Draw      = new Scene (FXMLLoader.load(TheDrake.class.getResource("Draw.fxml")), 800, 800);

        stage.setTitle("The Drake");
        stage.setScene(MainMenu);
        stage.show();

        new AnimationTimer()
        {
            @Override
            public void handle(long l)
            {
                if(GameResult.changed)
                {
                    switch (GameResult.result)
                    {
                        case IN_PLAY -> stage.setScene(GameResult.GameScene);
                        case MENU -> stage.setScene(MainMenu);
                        case VICTORY -> {if(GameResult.gameSreen.getWinner() == PlayingSide.BLUE) stage.setScene(BlueWon); else stage.setScene(OrangeWon);}
                        case DRAW -> stage.setScene(Draw);
                    }
                    stage.show();
                    GameResult.changed = false;
                }
            }
        }.start();
    }

}
