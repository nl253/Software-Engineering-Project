package uk.ac.kent.controllers;

import java.util.logging.Logger;
import javafx.stage.Stage;
import uk.ac.kent.Database;

@SuppressWarnings({"ClassHasNoToStringMethod", "AbstractClassWithoutAbstractMethods"})
abstract class BaseController {

    private static final Logger log = Logger.getAnonymousLogger();

    private Stage stage;
    @SuppressWarnings("PackageVisibleField")
    final Database database;

    BaseController(final Stage stage, final Database database) {
        this.stage = stage;
        this.database = database;
    }

    final Stage getStage() { return stage; }

    final void setStage(final Stage stage) { this.stage = stage; }
}
