import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MiniGame extends Application {
        static final int SC_WIDTH = 1280;
        static final int SC_HEIGHT = 640;
        static final String title = "Beat Em Off";
        final SoundManager soundManager = new SoundManager();
        final Group root = new Group();
        GameInfo gameInfo = new GameInfo(1);
        final TitleScreen titleScreen = new TitleScreen(title, SC_WIDTH, SC_HEIGHT, gameInfo, soundManager, this);
        Stage stage;

        @Override
        public void start(Stage stg) {
            stage = stg;
            root.setFocusTraversable(true);

            soundManager.playTitleMusic();

            stage.setTitle(title);
            stage.setResizable(false);
            stage.setWidth(SC_WIDTH);
            stage.setHeight(SC_HEIGHT);

            Image image = new Image("resources/img/background2.png");
            ImageView bgImg = new ImageView(image);
            root.getChildren().add(bgImg);

            Node tsRoot = titleScreen.getRoot();
            root.getChildren().add(tsRoot);

            stage.setScene(new Scene(root));
            stage.show();
        }

        public void startGame(int level, GameInfo gameInfo) {
                soundManager.playBackgroundMusic();
                gameInfo.setLevel(level);
                root.getChildren().add(new Level(SC_WIDTH, SC_HEIGHT, gameInfo, soundManager, this).getRoot());
        }

        public Font getGameFont(int fontSize) {
            return Font.loadFont(getClass().getClassLoader().
                    getResourceAsStream( "resources/fonts/TitleFont.ttf"), fontSize);
        }

}
