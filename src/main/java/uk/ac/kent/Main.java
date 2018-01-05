package uk.ac.kent;

import java.io.IOException;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.stage.Stage;
import uk.ac.kent.controllers.LoginController;

/**
 * The entry point into the application.
 *
 * @author Norbert
 */

@SuppressWarnings({"PublicMethodNotExposedInInterface", "ClassHasNoToStringMethod", "ConstantConditions", "ResultOfObjectAllocationIgnored"})
public final class Main extends Application {

    /** Logger for the class */
    private static final Logger log = Logger.getAnonymousLogger();

    /**
     * JavaFX will pass a {@link Stage} instance into this method.
     *
     * @param stage top level window
     */

    @Override
    public final void start(final Stage stage) throws IOException {
        new LoginController(stage).displayMainView();
    }

    /**
     * @param args command line args
     */

    public static void main(final String[] args) {
        Database.populate();
        System.exit(0);
        // launch(args);
    }
}
