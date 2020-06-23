import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

public class Player extends Sprite {
    static final int boundingBorderOffsetLeftX = 35;
    static final int boundingBorderOffsetRightX = 80;

    public Player(int x, int y, Pane parent) {
        super("resources\\img\\idle.png", 0, x, y, boundingBorderOffsetLeftX, boundingBorderOffsetRightX, DIRECTION.LEFT, parent);
    }

    public void Shoot(DIRECTION dir) {
        if (currentDirection == dir) {
            return;
        } else {
            imgView.setScaleX(imgView.getScaleX() * -1);
            boundingBox.setScaleX(imgView.getScaleX() * -1);
            currentDirection = dir;
        }
    }
}
