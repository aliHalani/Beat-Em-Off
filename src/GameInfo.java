import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class GameInfo {
    SimpleIntegerProperty level = new SimpleIntegerProperty();
    SimpleIntegerProperty lives = new SimpleIntegerProperty();
    SimpleIntegerProperty score = new SimpleIntegerProperty(0);
    SimpleIntegerProperty enemiesLeft = new SimpleIntegerProperty();
    double fireballLength;
    double enemySpeed;
    double enemyCreationSpeed;
    HBox heartContainer;

    public GameInfo(int level) {
        setLevel(level);
    }

    public void setLevel(int level) {
        switch (level) {
            case 1:
                levelOne();
                break;
            case 2:
                levelTwo();
                break;
            case 3:
                levelThree();
                break;
        }
    }

    private void levelOne() {
        level.set(1);
        restoreLives();
        enemiesLeft.set(20);
        fireballLength = 2;
        enemySpeed = 2;
        enemyCreationSpeed = 1.2;
    }

    private void levelTwo() {
        level.set(2);
        restoreLives();
        enemiesLeft.set(35);
        fireballLength = 1.5;
        enemySpeed = 3;
        enemyCreationSpeed = 1;
    }

    private void levelThree() {
        level.set(3);
        restoreLives();
        enemiesLeft.set(60);
        fireballLength = 1;
        enemySpeed = 3.7;
        enemyCreationSpeed = 0.6;
    }

    public int decreaseLife() {
        if (lives.get() < 1) {
            return 0;
        }
        lives.set(lives.get() - 1);

        ((ImageView) heartContainer.getChildren().get(lives.get())).setImage(new Image("resources/img/emptyheart.png"));
        return lives.get();
    }

    private void restoreLives() {
        lives.set(3);
        HBox heartContainer = new HBox();

        for (int i = 0; i < 3; i++) {
            ImageView heart = new ImageView(new Image("resources/img/fullheart.png"));
            heart.setPreserveRatio(true);
            heart.setFitHeight(40);
            heartContainer.getChildren().add(heart);
        }

        this.heartContainer = heartContainer;
    }


}
