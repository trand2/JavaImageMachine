import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.net.MalformedURLException;

/**
 * Main class for JIMachine.
 */
public class JIMachine extends Application {

    /**
     * New Borderpane.
     */
    private static BorderPane borderPane = new BorderPane();

    /**
     * original height to set size back to 100%.
     */
    private static double originalHeight = 0;

    /**
     * original width to set size back to 100%.
     */
    private static double originalWidth = 0;

    /**
     * Variable to find amount of button clicks.
     */
    private static int btnClicks = 0;

    /**
     * set x coordinate.
     */
    private final int setX = 350;

    /**
     * set y coordinate.
     */
    private final int setY = 350;

    @Override
    /**
     * start method.
     */
    public void start(final Stage primaryStage) {
        Button openBtn = new Button("Open");
        Button zoomInBtn = new Button("Zoom In");
        Button oneHundredBtn = new Button("100%");
        Button zoomOutBtn = new Button("Zoom Out");
        Button quitBtn = new Button("Quit");

        Pane pane = new Pane();

        ToolBar toolBar = new ToolBar();
        toolBar.setOrientation(Orientation.VERTICAL);
        borderPane.setLeft(toolBar);


        openBtn.setOnAction(event -> {
           FileChooser fileChooser = new FileChooser();
           ImageView imageView = null;
            File file = fileChooser.showOpenDialog(pane.getScene().getWindow());
            try {
                imageView = new ImageView(file.toURI()
                        .toURL().toExternalForm());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            if (file != null) {
                borderPane.getChildren().add(imageView);
                borderPane.setCenter(imageView);
                toolBar.toFront();
            }
        });

        zoomInBtn.setOnAction(event -> {
            if (btnClicks == 0) {
                originalWidth = borderPane.getCenter().getScaleX();
                originalHeight = borderPane.getCenter().getScaleY();
            }
            borderPane.getCenter().setTranslateX(setX);
            borderPane.getCenter().setTranslateY(setY);
            double width = borderPane.getCenter().getScaleX();
            double height = borderPane.getCenter().getScaleX();

            //Zoom in by 25%
            final double newWidth = width * 1.25;
            final double newHeight = height * 1.25;
            borderPane.getCenter().setScaleX(newWidth);
            borderPane.getCenter().setScaleY(newHeight);
            toolBar.toFront();
            btnClicks++;
        });

        oneHundredBtn.setOnAction(event -> {
            borderPane.getCenter().setScaleX(originalWidth);
            borderPane.getCenter().setScaleY(originalHeight);
            borderPane.getCenter().setTranslateX(setX);
            borderPane.getCenter().setTranslateY(setY);
        });

        zoomOutBtn.setOnAction(event -> {
            if (btnClicks == 0) {
                originalWidth = borderPane.getCenter().getScaleX();
                originalHeight = borderPane.getCenter().getScaleY();
            }
            borderPane.getCenter().setTranslateX(setX);
            borderPane.getCenter().setTranslateY(setY);

            double width = borderPane.getCenter().getScaleX();
            double height = borderPane.getCenter().getScaleX();

            //Zoom out by 25%
            final double newWidth = width * .75;
            final double newHeight = height * .75;

            borderPane.getCenter().setScaleX(newWidth);
            borderPane.getCenter().setScaleY(newHeight);
            toolBar.toFront();
            btnClicks++;
        });

        quitBtn.setOnAction(event -> {
            Platform.exit();
        });


        toolBar.getItems().addAll(
                openBtn, zoomInBtn, oneHundredBtn,
                        zoomOutBtn, quitBtn);
        pane.getChildren().addAll(borderPane);
        final int width = 1000;
        final int height = 1000;
        Scene scene = new Scene(pane, width, height);
        primaryStage.setTitle("Image Machine");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
