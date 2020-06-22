import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class Sprite {
    int x,y = 0;
    Image img;
    ImageView imgView;
    DIRECTION currentDirection = DIRECTION.LEFT;
    int boundingBorderOffsetLeftX = 0;
    int boundingBorderOffsetRightX = 0;
    int boundingBorderOffsetTopY = 0;
    int boundingBorderOffsetBottomY = 0;
    final int speed;
    final GraphicsContext gc;

    public enum DIRECTION {
        LEFT,
        RIGHT
    }

    public Sprite(String imgPath, int speed, int x, int y, GraphicsContext gc) {
        img = new Image(imgPath);
        this.speed = speed;
        this.x = (int) (x - (img.getWidth() / 2));
        this.y = y;
        this.gc = gc;
    }

//    public Rectangle getBoundingBox() {
//        Rectangle rect = new  Rectangle(imgView.getBoundsInParent().getMinX() + boundingBorderOffsetLeftX,
//                            imgView.getBoundsInParent().getMinY() + boundingBorderOffsetTopY,
//                            imgView.getLayoutBounds().getWidth() - boundingBorderOffsetRightX,
//                            imgView.getLayoutBounds().getHeight() - boundingBorderOffsetTopY);
//        rect.setFill(Color.RED);
//
//        return rect;
//    }

    public Node getSprite() {
        return imgView;
    }

    public void Draw() {

        gc.drawImage(img, x, y);

//        gc.setStroke(Color.RED);
//        gc.setLineWidth(3);
//        gc.strokeRect(x,y,img.getWidth(), img.getHeight());
    }

    public void moveToCenter() {
        if (currentDirection == DIRECTION.LEFT) {
            x -= speed;
        } else {
            x += speed;
        }

        Draw();
    }

    public Image getImage() {
        return img;
    }
}
