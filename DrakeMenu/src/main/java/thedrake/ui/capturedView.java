package thedrake.ui;

import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import thedrake.src.GameState;
import thedrake.src.PlayingSide;
import thedrake.src.Troop;


public class capturedView extends HBox
{
    private PlayingSide side;

    capturedView()
    {
        setMinHeight(100);
    }
    public void StateChanged(GameState state)
    {
        getChildren().clear();

        for(Troop capturedTroop : state.army(side).captured())
        {
           CapturedTileView newView = new CapturedTileView(capturedTroop, side);
           newView.setPadding(new Insets( 10, 10, 0, 0));
           getChildren().add(newView);
        }
    }
    public void SetSide(PlayingSide side)
    {
        this.side = side;
    }
}
