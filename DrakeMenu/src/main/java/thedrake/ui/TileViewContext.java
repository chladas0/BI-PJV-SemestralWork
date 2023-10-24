package thedrake.ui;

import thedrake.src.GameState;
import thedrake.src.Move;

public interface TileViewContext {

    void tileViewSelected(TileView tileView);

    void executeMove(Move move);

    void stackViewSelected(StackView stackView);

    GameState getState();
}
