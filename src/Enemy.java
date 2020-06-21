import java.util.Random;

public class Enemy extends Sprite {
    public Enemy(String imgPath) {
        super("resources\\img\\enemy.png");
        boundingBorderOffsetLeftX = 20;
//        currentDirection = DIRECTION.values()[new Random().nextInt(DIRECTION.values().length)];
        currentDirection = DIRECTION.LEFT;

        if (currentDirection == DIRECTION.LEFT) {
            imgView.setTranslateX(1600);
        } else {
            imgView.setScaleX(-1);
        }
    }
}