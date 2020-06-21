import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextBoundsType;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MiniGame extends Application {
        static final int SC_WIDTH = 1280;
        static final int SC_HEIGHT = 640;
        static final String title = "Beat Em Off";
        static final Group root = new Group();
        final TitleScreen titleScreen = new TitleScreen(title, SC_WIDTH, SC_HEIGHT, this);
        final MainGameScreen mainGameScreen = new MainGameScreen(SC_WIDTH, SC_HEIGHT, this);
        static Stage stage;


        @Override
        public void start(Stage stg) {
            stage = stg;

            stage.setTitle(title);
            stage.setResizable(false);
            stage.setWidth(SC_WIDTH);
            stage.setHeight(SC_HEIGHT);

            Image image = new Image("resources\\img\\background2.png");
            ImageView bgImg = new ImageView(image);
            root.getChildren().add(bgImg);

//            Node tsRoot = titleScreen.getRoot();
//            root.getChildren().add(tsRoot);
            Node mgsRoot = mainGameScreen.getRoot();
            root.getChildren().add(mgsRoot);

            stage.setScene(new Scene(root));
            stage.show();
        }

        public EventHandler<ActionEvent> startGame() {
            return event -> {
                System.out.println("Start game");
                root.getChildren().remove(titleScreen.getRoot());
                root.getChildren().add(mainGameScreen.getRoot());
            };
        }

}
