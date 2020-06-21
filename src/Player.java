public class Player extends Sprite {
    public Player() {
        super("resources\\img\\idle.png");
        boundingBorderOffsetLeftX = 48;
        boundingBorderOffsetRightX = 50;
    }

    public void Shoot(DIRECTION dir) {
        if (currentDirection == dir) {
            return;
        } else {
            imgView.setScaleX(imgView.getScaleX() * -1);
            currentDirection = dir;
        }
    }
}
