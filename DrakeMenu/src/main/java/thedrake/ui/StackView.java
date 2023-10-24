package thedrake.ui;

import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import thedrake.src.*;

import java.util.List;

public class StackView extends Pane
{
    private Tile tile;

    private final TileBackgrounds backgrounds = new TileBackgrounds();

    private Border selectBorder = new Border(
            new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3)));

    private TileViewContext context;

    private PlayingSide side;

    StackView(TileViewContext context, PlayingSide side)
    {
        this.context = context;
        this.side    = side;
        setPrefSize(200,200);
        setOnMouseClicked(e -> onClick());
        Update();
    }

    public void select()
    {
        setBorder(selectBorder);
        context.stackViewSelected(this);
    }
    private void onClick()
    {
        select();
    }

    public void unselect()
    {
        setBorder(null);
    }

    public PlayingSide side()
    {
        return side;
    }

    public void Update()
    {
        List<Troop> stack = context.getState().army(side).stack();
        if(!stack.isEmpty())
        {
            tile = new TroopTile(stack.get(0), side, TroopFace.AVERS);
            setBackground(backgrounds.get(tile));
        }
        else
            setBackground(null);
    }
}
