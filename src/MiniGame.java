import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MiniGame extends Application {
        static final int SC_WIDTH = 1280;
        static final int SC_HEIGHT = 640;
        static final String title = "Beat Em Off";
        final Group root = new Group();
        GameInfo gameInfo = new GameInfo();
        final TitleScreen titleScreen = new TitleScreen(title, SC_WIDTH, SC_HEIGHT, this);
        final Level level = new Level(SC_WIDTH, SC_HEIGHT, gameInfo,this);
        Stage stage;


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
            Node mgsRoot = level.getRoot();
            root.getChildren().add(mgsRoot);

            stage.setScene(new Scene(root));
            stage.show();
        }

        public EventHandler<ActionEvent> startGame() {
            return event -> {
                System.out.println("Start game");
                root.getChildren().remove(titleScreen.getRoot());
                root.getChildren().add(level.getRoot());
            };
        }

        public Font getGameFont(int fontSize) {
            return Font.loadFont(getClass().getClassLoader().
                    getResourceAsStream( "resources/fonts/TitleFont.ttf"), fontSize);
        }

}
