import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

public class Enemy extends Sprite {
    static final int boundingBorderOffsetLeftX = 0;
    static final int boundingBorderOffsetRightX = 0;

    public Enemy(int speed, int x, int y, DIRECTION dir, Pane parent) {
        super("resources\\img\\enemy.png", speed, x, y, boundingBorderOffsetLeftX, boundingBorderOffsetRightX, dir, parent);

        if (dir == DIRECTION.RIGHT) {
            imgView.setScaleX(-1);
        }
    }
}