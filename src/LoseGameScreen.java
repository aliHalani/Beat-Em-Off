import javafx.animation.Animation;
import javafx.animation.ScaleTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.CacheHint;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.util.Duration;

public class LoseGameScreen implements GameScreen {
    final String gameTitle = "You lose";
    final int titlePadding = 30;
    final int titleYOffset = 30;
    final int titleBorderSize = 2;
    final int titleBorderWidth = 30;
    final int titleBorderHeight = 25;
    final int titleFontSize = 56;
    final int scoreYOffset = 160;
    final Color titleBackgroundColour = Color.INDIANRED;
    final Paint titleBackgroundBorderColour = Color.DARKRED;
    Pane root = new Pane();
    MiniGame parent;

    LoseGameScreen(GameInfo gameInfo, int SC_WIDTH, int SC_HEIGHT, MiniGame parent) {
        this.parent = parent;

        root.setPrefWidth(SC_WIDTH);
        root.setPrefHeight(SC_HEIGHT);
        root.setFocusTraversable(true);

        Rectangle backgroundRect = new Rectangle(SC_WIDTH, SC_HEIGHT, Color.BLACK);
        backgroundRect.setOpacity(0.65);
        root.getChildren().add(backgroundRect);

        StackPane contentRoot = new StackPane();
        contentRoot.setPrefWidth(SC_WIDTH);
        contentRoot.setPrefHeight(SC_HEIGHT);
        root.getChildren().add(contentRoot);

        gameInfo.score.set(35);

        Node titleWrapper = titleBar("You lose");
        contentRoot.getChildren().add(titleWrapper);
        contentRoot.setAlignment(titleWrapper, Pos.TOP_CENTER);
        contentRoot.setMargin(titleWrapper, new Insets(titleYOffset, 0, 0, 0));

        Node scoreContent = scoreDisplay(gameInfo);
        contentRoot.getChildren().add(scoreContent);
        contentRoot.setAlignment(scoreContent, Pos.CENTER);
        contentRoot.setMargin((scoreContent), new Insets(0, 0, scoreYOffset, 0));

    }

    private Node titleBar(String contentTitle) {
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

        return new Group(titlePane);
    }

    private Node scoreDisplay(GameInfo gameInfo) {
        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);

        Text scoreLabel = new Text("Score");
        scoreLabel.setFont(parent.getGameFont(36));
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

    public Parent getRoot() {
        return root;
    }

}
