package thedrake.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import thedrake.src.*;

import java.util.List;

public class BoardView extends GridPane implements TileViewContext {
    private GameState gameState;
    private ValidMoves validMoves;
    private TileView selectedTileView;
    private StackView BlueStack;
    private StackView OrangeStack;
    private final capturedView CapturedBlue;
    private final capturedView CapturedOrange;


    public BoardView(GameState gameState) {
        this.gameState = gameState;
        this.validMoves = new ValidMoves(gameState);

        PositionFactory positionFactory = gameState.board().positionFactory();
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                BoardPos boardPos = positionFactory.pos(x, 3 - y);
                add(new TileView(boardPos, gameState.tileAt(boardPos), this), x, y);
            }
        }

        setHgap(5);
        setVgap(5);
        setPadding(new Insets(15));
        setAlignment(Pos.CENTER);

        BlueStack = new StackView(this, PlayingSide.BLUE);
        BlueStack.setMaxSize(100,100);

        OrangeStack = new StackView(this, PlayingSide.ORANGE);
        OrangeStack.setMaxSize(100,100);

        CapturedBlue = new capturedView();
        CapturedBlue.SetSide(PlayingSide.BLUE);

        CapturedOrange = new capturedView();
        CapturedOrange.SetSide(PlayingSide.ORANGE);
    }

    @Override
    public void tileViewSelected(TileView tileView) {
        if (selectedTileView != null && selectedTileView != tileView)
        {
            selectedTileView.unselect();
            BlueStack.unselect();
            OrangeStack.unselect();
        }

        selectedTileView = tileView;
        clearMoves();
        showMoves(validMoves.boardMoves(tileView.position()));
    }

    @Override
    public void executeMove(Move move) {
        if(selectedTileView != null)
        {
            selectedTileView.unselect();
            selectedTileView = null;
        }
        clearMoves();
        gameState = move.execute(gameState);
        validMoves = new ValidMoves(gameState);
        updateTiles();

        CapturedBlue.StateChanged(gameState);
        CapturedOrange.StateChanged(gameState);
        GameResult.setResult(gameState.result());
    }

    @Override
    public void stackViewSelected(StackView stackView)
    {
        if(selectedTileView != null)
            selectedTileView.unselect();

        if(stackView.side() == PlayingSide.ORANGE)
        {
            OrangeStack = stackView;
            BlueStack.unselect();
        }
        else
        {
            BlueStack = stackView;
            OrangeStack.unselect();
        }
        clearMoves();
        if(gameState.sideOnTurn() == stackView.side())
           showMoves(validMoves.movesFromStack());
    }

    private void updateTiles() {
        for (Node node : getChildren()) {
            TileView tileView = (TileView) node;
            tileView.setTile(gameState.tileAt(tileView.position()));
            tileView.update();
        }
        BlueStack.Update();
        OrangeStack.Update();
    }

    private void clearMoves() {
        for (Node node : getChildren()) {
            TileView tileView = (TileView) node;
            tileView.clearMove();
        }
    }

    private void showMoves(List<Move> moveList) {
        for (Move move : moveList)
            tileViewAt(move.target()).setMove(move);
    }

    private TileView tileViewAt(BoardPos target) {
        int index = (3 - target.j()) * 4 + target.i();
        return (TileView) getChildren().get(index);
    }

    public GameState getState()
    {
        return gameState;
    }

    public StackView getBlueStack()
    {
        return BlueStack;
    }

    public StackView getOrangeStack()
    {
        return OrangeStack;
    }

    public capturedView getCapturedBlue()
    {
        return CapturedBlue;
    }

    public capturedView getCapturedOrange()
    {
        return CapturedOrange;
    }
}
