package thedrake.ui;

import javafx.scene.layout.Pane;
import thedrake.src.PlayingSide;
import thedrake.src.Troop;
import thedrake.src.TroopFace;

public class CapturedTileView extends Pane
{
    CapturedTileView(Troop troop, PlayingSide side)
    {
        setPrefSize(100, 100);
        setBackground(new TileBackgrounds().getTroop(troop, side, TroopFace.AVERS));
        setOpacity(0.5);
    }
}
