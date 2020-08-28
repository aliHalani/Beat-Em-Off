import javafx.animation.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.util.Duration;


public class TitleScreen implements GameScreen {
    final int titlePadding = 30;
    final int titleYOffset = 30;
    final int titleBorderSize = 2;
    final int titleBorderWidth = 30;
    final int titleBorderHeight = 25;
    final int titleFontSize = 56;
    final int controlYOffset = 85;
    final int controlFontSize = 32;
    Color titleBackgroundColour = Color.INDIANRED;
    Paint titleBackgroundBorderColour = Color.DARKRED;
    static StackPane root;
    static Node titleBar;
    static Node controls;
    final MiniGame parent;
    final SoundManager soundManager;
    GameInfo gameInfo;

    public TitleScreen(String gameTitle, int SC_WIDTH, int SC_HEIGHT, GameInfo gameInfo, SoundManager soundManager, MiniGame pnode) {
        this.soundManager = soundManager;
        this.gameInfo = gameInfo;
        this.parent = pnode;

        // set window properties
        root = new StackPane();
        root.setPrefSize(SC_WIDTH, SC_HEIGHT);
        root.setFocusTraversable(true);

        // add title bar to title screen
        titleBar = titleBar(gameTitle);
        root.getChildren().add(titleBar);
        root.setAlignment(titleBar, Pos.TOP_CENTER);
        root.setMargin(titleBar, new Insets(titleYOffset, 0, 0, 0));

        // add controls to title screen
        controls = controls();
        root.getChildren().add(controls);
        root.setAlignment(controls, Pos.BOTTOM_CENTER);
        root.setMargin(controls, new Insets(0,0,  controlYOffset, 0));
    }

    private Node titleBar (String gameTitle) {
        Group titleWrapper = new Group();
        StackPane titlePane = new StackPane();

        Text title = new Text(gameTitle);
        title.setBoundsType(TextBoundsType.VISUAL);
        title.setFont(parent.getGameFont(titleFontSize));
        title.setFill(Color.WHITE);

        Rectangle titleBackground = new Rectangle(title.getLayoutBounds().getWidth() + titlePadding,
                                                 title.getLayoutBounds().getHeight() + titlePadding);
        titleBackground.setFill(titleBackgroundColour);
        titleBackground.setArcWidth(titleBorderWidth);
        titleBackground.setArcHeight(titleBorderHeight);
        titleBackground.setStroke(titleBackgroundBorderColour);
        titleBackground.setStrokeWidth(titleBorderSize);

        titlePane.getChildren().addAll(titleBackground, title);
        titleWrapper.getChildren().add(titlePane);

        return titleWrapper;
    }

    private Node controls() {
        Group controlsWrapper = new Group();
        VBox controlsPane = new VBox();
        controlsPane.setAlignment(Pos.CENTER);

        Text controlLabel = new Text("Controls");
        controlLabel.setFont(parent.getGameFont(controlFontSize));
        controlLabel.setFill(Color.WHITE);
        controlsPane.getChildren().add(controlLabel);

        Text instructionLabel = new Text("Use the LEFT or RIGHT arrow keys to shoot");
        instructionLabel.setFont(parent.getGameFont(18));
        instructionLabel.setFill(Color.WHITE);
        instructionLabel.setSelectionStart(8);
        instructionLabel.setSelectionEnd(22);
        instructionLabel.setSelectionFill(Color.RED);
        controlsPane.getChildren().add(instructionLabel);

        Text enterLabel = new Text("Press ENTER to start");
        enterLabel.setFont(parent.getGameFont(24));
        enterLabel.setFill(Color.WHITE);
        controlsPane.getChildren().add(enterLabel);
        controlsPane.setMargin(enterLabel, new Insets(50, 0, 0, 0));
        enterLabel.setSelectionStart(6);
        enterLabel.setSelectionEnd(11);
        enterLabel.setSelectionFill(Color.RED);

        Text exitLabel = new Text("Press E to exit");
        exitLabel.setFont(parent.getGameFont(24));
        exitLabel.setFill(Color.WHITE);
        exitLabel.setSelectionStart(6);
        exitLabel.setSelectionEnd(8);
        exitLabel.setSelectionFill(Color.RED);
        controlsPane.getChildren().add(exitLabel);

        controlsWrapper.getChildren().add(controlsPane);

        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(1.5), controlsWrapper);
        controlsWrapper.setCache(true);
        controlsWrapper.setCacheHint(CacheHint.SPEED);
        scaleTransition.setFromX(1);
        scaleTransition.setByY(1);
        scaleTransition.setToX(1.05);
        scaleTransition.setToY(1.05);
        scaleTransition.setAutoReverse(true);
        scaleTransition.setCycleCount(Animation.INDEFINITE);
        scaleTransition.play();

        return controlsWrapper;
    }

    public void startGameTransition(int level) {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1.5), root);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.setOnFinished((e) -> {
            parent.root.getChildren().remove(root);
            parent.startGame(level, gameInfo);
        });

        TranslateTransition titleTranslateTransition = new TranslateTransition(Duration.seconds(1.2), titleBar);
        titleTranslateTransition.setFromY(0);
        titleTranslateTransition.setToY(-170);

        TranslateTransition controlsTranslateTransition = new TranslateTransition(Duration.seconds(1.2), controls);
        controlsTranslateTransition.setFromY(0);
        controlsTranslateTransition.setToY(320);

        Timeline titleMusicFadeout = new Timeline(
                new KeyFrame(Duration.seconds(1.5), new KeyValue(soundManager.titleMusic.volumeProperty(), 0))
        );

        ParallelTransition startTransmission = new ParallelTransition(fadeTransition,
                titleTranslateTransition,
                controlsTranslateTransition,
                titleMusicFadeout);

        startTransmission.play();
    }

    public Parent getRoot() {
        root.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                soundManager.stopTitleMusic();
                root.setOnKeyPressed(null);
                startGameTransition(1);
            } else if (e.getCode() == KeyCode.DIGIT1 || e.getCode() == KeyCode.DIGIT2 || e.getCode() == KeyCode.DIGIT3) {
                soundManager.stopTitleMusic();
                root.setOnKeyPressed(null);
                startGameTransition(Character.getNumericValue(e.getCode().getCode()));
            }
            else if (e.getCode() == KeyCode.E) {
                System.exit(0);
            }
        });

        return root;
    }
}