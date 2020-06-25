import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Fireball {
    final long createdTime;
    final ImageView imgView;
    final Sprite.DIRECTION direction;

    Fireball(long createdTime, Sprite.DIRECTION dir, double x, double y) {
        this.createdTime = createdTime;
        this.imgView = new ImageView(new Image("resources/img/fireball2.gif"));
        this.direction = dir;

        if (dir == Sprite.DIRECTION.RIGHT) {
            imgView.setTranslateX(x);
        } else {
            imgView.setTranslateX(x);
        }

        imgView.setTranslateY(y);

        if (dir == Sprite.DIRECTION.LEFT) {
            imgView.setScaleX(-1);
        }

    }
}
