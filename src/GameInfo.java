import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class GameInfo {
    SimpleIntegerProperty level = new SimpleIntegerProperty();
    SimpleIntegerProperty lives = new SimpleIntegerProperty(3);
    SimpleIntegerProperty score = new SimpleIntegerProperty(0);
    SimpleIntegerProperty enemiesLeft = new SimpleIntegerProperty();
    double fireballLength;
    double enemySpeed;
    HBox heartContainer = new HBox();

    public GameInfo() {
        levelOne();

        for (int i = 0; i < 3; i++) {
            ImageView heart = new ImageView(new Image("resources\\img\\fullheart.png"));
            heart.setPreserveRatio(true);
            heart.setFitHeight(40);
            heartContainer.getChildren().add(heart);
        }
    }

    public void levelOne() {
        level.set(1);
        enemiesLeft.set(20);
        fireballLength = 2;
        enemySpeed = 2;
    }

    public void levelTwo() {
        level.set(2);
        enemiesLeft.set(35);
        fireballLength = 1.6;
        enemySpeed = 3;
    }

    public void levelThree() {
        level.set(3);
        enemiesLeft.set(50);
        fireballLength = 1.2;
        enemySpeed = 3.5;
    }

    public int decreaseLife() {
        if (lives.get() < 1) {
            return 0;
        }
        lives.set(lives.get() - 1);
        System.out.printf("Lives left are %d", lives.get());
        ((ImageView) heartContainer.getChildren().get(lives.get())).setImage(new Image("resources\\img\\emptyheart.png"));
        return lives.get();
    }

}
