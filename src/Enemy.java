import javafx.scene.canvas.GraphicsContext;

import java.util.Random;

public class Enemy extends Sprite {
    public Enemy(int speed, int x, int y, GraphicsContext gc, DIRECTION dir) {
        super("resources\\img\\enemy.png", speed, x, y, gc);

//        if (dir == DIRECTION.LEFT) {
//            img
//        }
    }
}