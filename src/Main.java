import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static boolean[][] visited;

    public static void main(String[] args) throws IOException {
        Scanner scan = new Scanner(new File("test.txt"));

        Guard guard = new Guard();

        ArrayList<Point> obstacles = new ArrayList<>();

        int y = 0;
        int x = 0;

        for (; scan.hasNext(); y++) {
            String line = scan.nextLine();
            x = 0;
            for(char c : line.toCharArray()) {
                if (c == '#') {
                    // obstacle
                    obstacles.add(new Point(x, y));
                } else if(c == '^') {
                    // guard's position
                    guard.setPosition(new Point(x, y));
                }
                x++;
            }

        }

        visited = new boolean[y][x];
        visited[guard.getY()][guard.getX()] = true;

        Point newPos = moveNext(guard, obstacles);

        //System.out.println(obstacles);

        while(!newPos.equals(guard.getPosition())) {
//            System.out.println(guard);
            newPos = moveNext(guard, obstacles);
        }
        // now add the last few from the guard (direction) until the edge of the map

        switch(guard.getDirection()) {
            case UP:
                for (int y2 = guard.getY(); y2 > 0; y2--) {
                    visited[y2][guard.getX()] = true;
                }
                break;
            case DOWN:
                for (int y2 = guard.getY(); y2 < y; y2++) {
                    visited[y2][guard.getX()] = true;
                }
                break;
            case RIGHT:
                for (int x2 = guard.getX(); x2 < x; x2++) {
                    visited[guard.getY()][x2] = true;
                }
                break;
            case LEFT:
                for (int x2 = guard.getX(); x2 > 0; x2--) {
                    visited[guard.getY()][x2] = true;
                }
                break;
        }

        int result = 0;

        System.out.println(guard);
        System.out.println(Arrays.deepToString(visited));
        for(boolean[] v : visited) {
            for(boolean j : v) {
                System.out.print((j) ? "X" : ".");
                result += (j) ? 1 : 0;
            }
            System.out.println();
        }
        System.out.println(result);
    }

    public static Point moveNext(Guard guard, ArrayList<Point> obstacles) {
        Point bestGuess = guard.getPosition();

        for(Point obstacle : obstacles) {
            switch (guard.getDirection()) {
                case RIGHT:
                    if(guard.getY() == obstacle.y && guard.getX() < obstacle.x) {
                        // valid obstacle to move to?
                        if(obstacle.x > bestGuess.x) bestGuess = obstacle;
                    }
                    break;
                case LEFT:
                    if(guard.getY() == obstacle.y && guard.getX() > obstacle.x) {
                        // valid obstacle to move to?
                        if(obstacle.x < bestGuess.x) bestGuess = obstacle;
                    }
                    break;
                case UP:
                    if(guard.getX() == obstacle.x && guard.getY() > obstacle.y) {
                        // valid obstacle to move to?
                        if(obstacle.y < bestGuess.y) bestGuess = obstacle;
                    }
                    break;
                case DOWN:
                    if(guard.getX() == obstacle.x && guard.getY() < obstacle.y) {
                        // valid obstacle to move to?
                        if(obstacle.y > bestGuess.y) bestGuess = obstacle;
                    }
                    break;
            }
        }

        if(bestGuess.equals(guard.getPosition())) {
            return bestGuess;
        }



        int newX = bestGuess.x;
        int newY = bestGuess.y;
        switch(guard.getDirection()) {
            case UP:
                newY = bestGuess.y + 1;
                break;
            case DOWN:
                newY = bestGuess.y - 1;
                break;
            case RIGHT:
                newX = bestGuess.x - 1;
                break;
            case LEFT:
                newX = bestGuess.x + 1;
                break;
        }

        // set visited
        if(Math.abs(guard.getX() - bestGuess.x) > 0) {
            //System.out.println("X");
            if(bestGuess.x > guard.getX()) {
                for (int i = guard.getX(); i < bestGuess.x; i ++) {
                   // System.out.println("X - loop");
                    visited[guard.getY()][i] = true;
                }
            } else {
                for (int i = guard.getX(); i > bestGuess.x; i --) {
                    //System.out.println("X - loop");
                    visited[guard.getY()][i] = true;
                }
            }

        } else if(Math.abs(guard.getY() - bestGuess.y) > 0) {
         //   System.out.println("Y");
            if(bestGuess.y > guard.getY()) {
                for (int i = guard.getY(); i < bestGuess.y; i++) {
                    visited[i][guard.getX()] = true;
                }
            } else {
                for (int i = guard.getY(); i > bestGuess.y; i--) {
                    visited[i][guard.getX()] = true;
                }
            }

        }

        guard.setPosition(new Point(newX, newY));
//        System.out.println(guard);
        // must have found best guess, change dir
        int newDirOrdinal = guard.getDirection().ordinal() + 1;
        if(guard.getDirection().ordinal() + 1 >= Direction.values().length) newDirOrdinal = 0;
        guard.setDirection(Direction.values()[newDirOrdinal]);
        return bestGuess;
    }
}