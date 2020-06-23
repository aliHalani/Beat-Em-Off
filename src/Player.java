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
    static final int fireballSpeed = 2;
    static final int fireballLength = 2;
//    ArrayList<ImageView> fireballs = new ArrayList<ImageView>();

    public Player(int x, int y, Pane parent) {
        super("resources\\img\\idle.png", 0, x, y, boundingBorderOffsetLeftX, boundingBorderOffsetRightX, DIRECTION.LEFT, parent);
    }

    public void Shoot(DIRECTION dir, ArrayList<Enemy> enemies) {
        if (currentDirection != dir) {
            switchDirections(dir);
        }

        ImageView fireball = new ImageView(new Image("resources\\img\\fireball2.gif"));

        if (currentDirection == DIRECTION.RIGHT) {
            fireball.setTranslateX(x + img.getWidth() - boundingBorderOffsetRightX);
        } else {
            fireball.setTranslateX(x);
        }
        fireball.setTranslateY(y + mouthYOffset);

        fireball.setScaleX(imgView.getScaleX() * -1);
//        fireballs.add(fireball);
        parent.getChildren().add(fireball);

        AnimationTimer fireballTimer = new AnimationTimer() {
            final long now = System.nanoTime();
            final int direction = (currentDirection == DIRECTION.RIGHT) ? 1 : -1;
            @Override
            public void handle(long l) {
                if (l - now < fireballLength * 1000 * 1000 * 1000) {
                    fireball.setTranslateX(fireball.getTranslateX() + fireballSpeed * direction);
                } else {
                    parent.getChildren().remove(fireball);
                }

                System.out.println(fireball.getBoundsInParent());

                for (Enemy enemy : enemies) {
                    if (enemy.collision(fireball.getBoundsInParent())) {
                        System.out.println("Collision");
                        enemies.remove(enemy);
                        parent.getChildren().remove(enemy);
                        parent.getChildren().remove(fireball);
                        stop();
                    }
                }
            }
        };

        fireballTimer.start();

    }
}
