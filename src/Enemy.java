import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.util.Duration;

import java.util.ArrayList;

public class Enemy extends Sprite {
    static final int boundingBorderOffsetLeftX = 0;
    static final int boundingBorderOffsetRightX = 0;
    boolean beingPushedBack = false;

    public Enemy(double speed, int x, int y, DIRECTION dir, Pane parent) {
        super("resources\\img\\enemy.png", speed, x, y, boundingBorderOffsetLeftX, boundingBorderOffsetRightX, dir, parent);

        if (dir == DIRECTION.RIGHT) {
            imgView.setScaleX(-1);
        }
    }

    public void pushBack() {
        beingPushedBack = true;
        TranslateTransition imgViewTransition = new TranslateTransition(Duration.seconds(1), imgView);
        imgViewTransition.setFromX(x);
        imgViewTransition.setToX(x + 300 * imgView.getScaleX());

        TranslateTransition boundingBoxTransition = new TranslateTransition(Duration.seconds(1), boundingBox);
        boundingBoxTransition.setFromX(x);
        boundingBoxTransition.setToX(x + 300 * imgView.getScaleX());

        ParallelTransition pushbackTransition = new ParallelTransition(imgViewTransition, boundingBoxTransition);
        pushbackTransition.setOnFinished((e) -> {
            this.x += 300 * imgView.getScaleX();
            beingPushedBack = false;
        });

        pushbackTransition.play();
    }
}