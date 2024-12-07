import java.awt.*;

public class Guard {
    private Point position;
    private Direction direction;

    public Guard(Point pos, Direction dir) {
        this.position = pos;
        this.direction = dir;
    }

    public Guard() {
        this.position = new Point(-1, -1);
        this.direction = Direction.UP;
    }

    public Point getPosition() {
        return position;
    }

    public int getX() {
        return position.x;
    }

    public int getY() {
        return position.y;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public String toString() {
        return "Guard{" +
                "position=" + "(" + position.x + ", " + position.y + ")" +
                "\ndirection=" + direction +
                '}';
    }
}
