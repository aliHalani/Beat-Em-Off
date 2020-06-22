import javafx.scene.canvas.GraphicsContext;

public class Player extends Sprite {
    public Player(int x, int y, GraphicsContext gc) {
        super("resources\\img\\idle.png", 0, x, y, gc);
    }

    public void Shoot(DIRECTION dir) {
        if (currentDirection == dir) {
            return;
        } else {
            imgView.setScaleX(imgView.getScaleX() * -1);
            currentDirection = dir;
        }
    }
}
