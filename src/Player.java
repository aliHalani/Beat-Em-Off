import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Player extends Sprite {
    static final int boundingBorderOffsetLeftX = 35;
    static final int boundingBorderOffsetRightX = 45;
    static final int mouthYOffset = 155;
    static final double firingAnimationLength = 0.7 * 1000;
    ArrayList<Fireball> fireballs = new ArrayList<Fireball>();
    BorderPane imgViewFire = new BorderPane(new ImageView(new Image("resources\\img\\fire.png")));
    AtomicInteger currentlyFiring = new AtomicInteger(0);

    public Player(int x, int y, Pane parent) {
        super("resources\\img\\idle.png", 0, x, y, boundingBorderOffsetLeftX, boundingBorderOffsetRightX, DIRECTION.LEFT, parent);
        imgViewFire.setOpacity(0);
        parent.getChildren().add(imgViewFire);

//        fireAnimationThread =
//                () -> {
//                    System.out.println("Running");
//                    imgViewFire.setScaleX(imgView.getScaleX());
//                    imgView.setOpacity(0);
//                    imgViewFire.setOpacity(1);
//                    try {
//                        slee
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    imgView.setOpacity(1);
//                    imgViewFire.setOpacity(0);
//                };

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

        currentlyFiring.incrementAndGet();
        imgViewFire.setScaleX(imgView.getScaleX());
        imgViewFire.setTranslateX(imgView.getTranslateX());
        imgViewFire.setTranslateY(imgView.getTranslateY());
        imgView.setOpacity(0);
        imgViewFire.setOpacity(1);

        new java.util.Timer().schedule(new TimerTask(){
            @Override
            public void run() {
                if (currentlyFiring.decrementAndGet() == 0) {
                    imgView.setOpacity(1);
                    imgViewFire.setOpacity(0);
                }
            }
        }, (int) firingAnimationLength);




    }
}
