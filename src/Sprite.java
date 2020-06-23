import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class Sprite {
    int x,y = 0;
    Image img;
    BorderPane imgView;
    DIRECTION currentDirection = DIRECTION.LEFT;
    int boundingBorderOffsetLeftX;
    int boundingBorderOffsetRightX;
    final int speed;
    Rectangle boundingBox;
    Pane parent;

    public enum DIRECTION {
        LEFT,
        RIGHT
    }

    public Sprite(String imgPath, int speed, int x, int y, int boundingBorderOffsetLeftX, int boundingBorderOffsetRightX, DIRECTION dir, Pane parent) {
        img = new Image(imgPath);
        imgView = new BorderPane( new ImageView(img));

        this.speed = speed;
        this.x = (int) (x - (img.getWidth() / 2));
        this.y = y;
        this.boundingBorderOffsetLeftX = boundingBorderOffsetLeftX;
        this.boundingBorderOffsetRightX = boundingBorderOffsetRightX;
        this.currentDirection = dir;
        this.parent = parent;

        this.boundingBox = new Rectangle(0 + boundingBorderOffsetLeftX, 0,
                                        this.img.getWidth() - boundingBorderOffsetRightX - boundingBorderOffsetLeftX,
                                            this.img.getHeight());
        this.boundingBox.setStyle(("-fx-fill: transparent; -fx-stroke: black; -fx-stroke-width: 1;"));

        imgView.setTranslateX(x);
        imgView.setTranslateY(y);
        this.boundingBox.setTranslateX(x);
        this.boundingBox.setTranslateY(y);

        parent.getChildren().addAll(imgView, this.boundingBox);
    }

    public Node getSprite() {
        return imgView;
    }

    public void Draw() {
        imgView.setTranslateX(x);
        imgView.setTranslateY(y);

        boundingBox.setTranslateX(x);
        boundingBox.setTranslateY(y);
    }

    protected void switchDirections(DIRECTION dir) {
        if (dir != currentDirection) {
            currentDirection = dir;

            imgView.setScaleX(imgView.getScaleX() * -1);

            boundingBox.setTranslateX(boundingBox.getTranslateX() +
                    ((boundingBorderOffsetRightX - boundingBorderOffsetLeftX) * -imgView.getScaleX()));
        }
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

    public Boolean collision(Sprite opp) {
        return boundingBox.getBoundsInParent().intersects(opp.boundingBox.getBoundsInParent());
    }

    public Boolean collision(Bounds opp) {
        return boundingBox.getBoundsInParent().intersects(opp);
    }
}
