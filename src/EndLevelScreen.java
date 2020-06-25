import javafx.animation.Animation;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.CacheHint;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.util.Duration;

public class EndLevelScreen implements GameScreen {
    final int titlePadding = 30;
    final int titleYOffset = 30;
    final int titleBorderSize = 2;
    final int titleBorderWidth = 30;
    final int titleBorderHeight = 25;
    final int titleFontSize = 56;
    final int scoreYOffset = 160;
    final int controlFontSize = 36;
    final int controlYOffset = 130;
    final Color titleBackgroundColour;
    final Paint titleBackgroundBorderColour;
    final String mainTitle;
    final String controltext1;
    Pane root = new Pane();
    MiniGame parent;

    EndLevelScreen(GameInfo gameInfo, int SC_WIDTH, int SC_HEIGHT, Color titleBackgroundColour, Color titleBackgroundBorderColour, String title, String controlText1, MiniGame parent) {
        this.titleBackgroundColour = titleBackgroundColour;
        this.titleBackgroundBorderColour = titleBackgroundBorderColour;

        this.mainTitle = title;
        this.controltext1 = controlText1;
        this.parent = parent;

        root.setPrefWidth(SC_WIDTH);
        root.setPrefHeight(SC_HEIGHT);
        root.setFocusTraversable(true);
        root.setTranslateY(-SC_HEIGHT);

        Rectangle backgroundRect = new Rectangle(SC_WIDTH, SC_HEIGHT, Color.BLACK);
        backgroundRect.setOpacity(0.65);
        backgroundRect.setFocusTraversable(false);
        root.getChildren().add(backgroundRect);

        StackPane contentRoot = new StackPane();
        contentRoot.setPrefWidth(SC_WIDTH);
        contentRoot.setPrefHeight(SC_HEIGHT);
        root.getChildren().add(contentRoot);

        Node titleWrapper = titleBar(title);
        contentRoot.getChildren().add(titleWrapper);
        contentRoot.setAlignment(titleWrapper, Pos.TOP_CENTER);
        contentRoot.setMargin(titleWrapper, new Insets(titleYOffset, 0, 0, 0));

        Node scoreContent = scoreDisplay(gameInfo);
        contentRoot.getChildren().add(scoreContent);
        contentRoot.setAlignment(scoreContent, Pos.CENTER);
        contentRoot.setMargin((scoreContent), new Insets(0, 0, scoreYOffset, 0));

        Node controlContent = controls();
        contentRoot.getChildren().add(controlContent);
        contentRoot.setAlignment(controlContent, Pos.BOTTOM_CENTER);
        contentRoot.setMargin(controlContent, new Insets(0, 0, controlYOffset, 0));

        TranslateTransition tt = new TranslateTransition(Duration.seconds(1), root);
        tt.setFromY(-SC_HEIGHT);
        tt.setToY(0);
        tt.play();
    }

    public EndLevelScreen(GameInfo gameInfo, int SC_WIDTH, int SC_HEIGHT, String title, String controlText1, MiniGame parent) {
        this(gameInfo, SC_WIDTH, SC_HEIGHT, Color.INDIANRED, Color.DARKRED, title, controlText1, parent);
    }

    private Node titleBar(String contentTitle) {
        StackPane titlePane = new StackPane();

        Text title = new Text(this.mainTitle);
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

        return new Group(titlePane);
    }

    private Node scoreDisplay(GameInfo gameInfo) {
        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);

        Text scoreLabel = new Text("Score");
        scoreLabel.setFont(parent.getGameFont(controlFontSize));
        scoreLabel.setFill(Color.WHITE);
        root.getChildren().add(scoreLabel);

        Text score = new Text(gameInfo.score.getValue().toString());
        score.setFont(new Font("Verdana", 30));
        score.setFill(Color.WHITE);
        root.getChildren().add(score);

        return new Group(root);
    }

    private Node controls() {
        Group controlsWrapper = new Group();
        VBox controlsPane = new VBox();
        controlsPane.setAlignment(Pos.CENTER);

        Text instructionLabel = new Text(controltext1);
        instructionLabel.setFont(parent.getGameFont(controlFontSize - 6));
        instructionLabel.setFill(Color.WHITE);
        instructionLabel.setSelectionStart(6);
        instructionLabel.setSelectionEnd(12);
        instructionLabel.setSelectionFill(Color.RED);
        controlsPane.getChildren().add(instructionLabel);

        Text exitLabel = new Text("Press E to exit");
        exitLabel.setFont(parent.getGameFont(controlFontSize - 6));
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

    public Parent getRoot() {
        return root;
    }

}
