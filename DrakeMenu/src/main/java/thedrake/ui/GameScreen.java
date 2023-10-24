package thedrake.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import thedrake.src.GameState;
import thedrake.src.PlayingSide;

public class GameScreen extends BorderPane
{
    BoardView boardView;

    public GameScreen(GameState gameState)
    {
        boardView = new BoardView(gameState);
        boardView.setPadding(new Insets(10, 10, 10, 200));
        this.setLeft(boardView);

        VBox stacks = new VBox();
        stacks.getChildren().addAll(boardView.getOrangeStack(), boardView.getBlueStack());
        stacks.setAlignment(Pos.CENTER);
        stacks.setSpacing(100);
        stacks.setPadding(new Insets(10, 10, 10, 10));

        this.setRight(stacks);
        this.setBottom(boardView.getCapturedBlue());
        this.setTop(boardView.getCapturedOrange());
        setStyle("-fx-background-color: #3B2F0B;");
    }

    public PlayingSide getWinner()
    {
        return boardView.getState().armyNotOnTurn().side();
    }
}
