package thedrake;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import thedrake.src.GameResult;

import java.io.IOException;

public class TheDrakeController {
    @FXML
    protected void onEndButtonPressed() {
        System.exit(0);
    }

    @FXML
    protected void onPlayerVsPlayerPressed(ActionEvent event) throws IOException
    {
        GameResult.setResult(GameResult.IN_PLAY);
    }

}