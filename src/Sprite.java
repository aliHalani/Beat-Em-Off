import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class Sprite {
    Image img;
    ImageView imgView;
    DIRECTION currentDirection = DIRECTION.LEFT;
    int boundingBorderOffsetLeftX = 0;
    int boundingBorderOffsetRightX = 0;
    int boundingBorderOffsetTopY = 0;
    int boundingBorderOffsetBottomY = 0;

    public enum DIRECTION {
        LEFT,
        RIGHT
    }

    public Sprite(String imgPath) {
        img = new Image(imgPath);
        imgView = new ImageView(img);
//        DropShadow ds = new DropShadow(20, Color.AQUA);
//        imgView.setEffect(ds);
        imgView.setStyle(("-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: blue;"));
    }

    void moveToCenter(int speed) {
        Double currentPos = imgView.getTranslateX();
        if (currentDirection == DIRECTION.LEFT) {
            speed = -speed;
        }
        imgView.setTranslateX(currentPos + speed);
    }

    public Rectangle getBoundingBox() {
        Rectangle rect = new  Rectangle(imgView.getBoundsInParent().getMinX() + boundingBorderOffsetLeftX,
                            imgView.getBoundsInParent().getMinY() + boundingBorderOffsetTopY,
                            imgView.getLayoutBounds().getWidth() - boundingBorderOffsetRightX,
                            imgView.getLayoutBounds().getHeight() - boundingBorderOffsetTopY);
        rect.setFill(Color.RED);

        return rect;
    }

    public Node getSprite() {
        return imgView;
    }
}
