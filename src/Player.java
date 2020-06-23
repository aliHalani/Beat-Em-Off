import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

import java.util.ArrayList;

public class Player extends Sprite {
    static final int boundingBorderOffsetLeftX = 35;
    static final int boundingBorderOffsetRightX = 45;
    static final int mouthYOffset = 140;
    ArrayList<Fireball> fireballs = new ArrayList<Fireball>();

    public Player(int x, int y, Pane parent) {
        super("resources\\img\\idle.png", 0, x, y, boundingBorderOffsetLeftX, boundingBorderOffsetRightX, DIRECTION.LEFT, parent);
    }

    public void Shoot(DIRECTION dir, ArrayList<Enemy> enemies) {
        if (currentDirection != dir) {
            switchDirections(dir);
        }

        Fireball fireball = new Fireball(System.nanoTime(), currentDirection,
                                        currentDirection == DIRECTION.RIGHT ? x + img.getWidth() - boundingBorderOffsetRightX : x,
                                        y + mouthYOffset);

        fireballs.add(fireball);
        parent.getChildren().add(fireball.imgView);



    }
}
