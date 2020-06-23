import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import jdk.jshell.spi.ExecutionEnv;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Random;

public class MainGameScreen implements GameScreen {
    final MiniGame parent;
    ArrayList<Enemy> enemies = new ArrayList<Enemy>();
    final int enemySpeed = 2;
    final long enemyCreationSpeed = 2;
    final int GAME_LANE_OFFSET = 300;
    Pane root = new Pane();

    public MainGameScreen(int SC_WIDTH, int SC_HEIGHT, MiniGame pnode) {
        parent = pnode;
        root.setPrefWidth(SC_WIDTH);
        root.setPrefHeight(SC_HEIGHT);

        Player player = new Player(SC_WIDTH / 2, GAME_LANE_OFFSET, root);
        player.Draw();

                AnimationTimer animationTimer = new AnimationTimer() {
            long lastEnemyCreatedTime = System.nanoTime();
            Boolean t = true;

            @Override
            public void handle(long l) {

                for (Enemy enemy : enemies) {
                    enemy.moveToCenter();
                    if (player.collision(enemy)) {
                        System.out.println("Collision");
                    }
                }

                if ((l - lastEnemyCreatedTime) > enemyCreationSpeed * 1000 * 1000 * 1000 & enemies.size() < 1) {
                    Sprite.DIRECTION dir = Sprite.DIRECTION.values()[new Random().nextInt(Sprite.DIRECTION.values().length)];
                    int enemyX;
                    if (dir == Sprite.DIRECTION.LEFT) {
                        enemyX = SC_WIDTH + 300;
                    } else {
                        enemyX = -300;
                    }

                    Enemy enemy = new Enemy(enemySpeed, enemyX, GAME_LANE_OFFSET, dir, root);
                    enemies.add(enemy);
                    lastEnemyCreatedTime = l;
                    System.out.println("Created new enemy");
                }



            }

        };
        animationTimer.start();




    }

//    public MainGameScreen(int SC_WIDTH, int SC_HEIGHT, MiniGame pnode) {
//        parent = pnode;
//        gameLaneWidth = SC_WIDTH + 600;
//
//        root = new StackPane();
//        root.setPrefSize(gameLaneWidth, SC_HEIGHT - 80);
//        root.setFocusTraversable(true);
//        root.setLayoutX((gameLaneWidth - SC_WIDTH) / -2);
//
//        Pane gameLane = new Pane();
//        gameLane.setPrefWidth(gameLaneWidth);
//        gameLane.setStyle(("-fx-padding: 10;" +
//                "-fx-border-style: solid inside;" +
//                "-fx-border-width: 2;" +
//                "-fx-border-insets: 5;" +
//                "-fx-border-radius: 5;" +
//                "-fx-border-color: blue;"));
//
//        Player player = new Player();
//        player.getSprite().setTranslateX(gameLaneWidth / 2 - player.getSprite().getLayoutBounds().getWidth() / 2);
//        gameLane.getChildren().add(player.getSprite());
//
//
//        root.setOnKeyPressed(e -> {
//            System.out.println("key");
//            if (e.getCode() == KeyCode.RIGHT) {
//                player.Shoot(Sprite.DIRECTION.RIGHT);
//            } else if (e.getCode() == KeyCode.LEFT) {
//                player.Shoot(Sprite.DIRECTION.LEFT);
//            }
//        });
//
////        gameLane.getChildren().add(player.getBoundingBox());
//        Group laneWrapper = new Group(gameLane);
//        root.getChildren().add(laneWrapper);
//        root.setAlignment(laneWrapper, Pos.BOTTOM_CENTER);
//
////        gameLane.getChildren().add(player.getBoundingBox());
//
//        AnimationTimer animationTimer = new AnimationTimer() {
//            long lastEnemyCreatedTime = System.nanoTime();
//            Boolean t = true;
//
//            @Override
//            public void handle(long l) {
////                if (t) {
////                    gameLane.getChildren().add(player.getBoundingBox());
////                    t = false;
////                }
//
//                for (Enemy enemy : enemies) {
//                    enemy.moveToCenter(enemySpeed);
//                    if (player.getBoundingBox().intersects(enemy.getBoundingBox().getLayoutBounds())) {
//                        System.out.println("Collision");
//                    }
////                    System.out.println(player.getSprite().getBoundsInParent().getMinX());
////                    System.out.println(player.getSprite().getBoundsInParent().getMaxX());
////                    System.out.println(player.getSprite().getBoundsInParent().getWidth());
////                System.out.println(enemy.getBoundingBox().getBoundsInParent());
//
//                }
//
////                System.out.println(player.getBoundingBox());
//
//                if ((l - lastEnemyCreatedTime) > enemyCreationSpeed * 1000 * 1000 * 1000 & enemies.size() < 5) {
//                    Enemy enemy = new Enemy("resources\\img\\enemy.png");
//                    enemies.add(enemy);
//                    gameLane.getChildren().add(enemy.getSprite());
//                    lastEnemyCreatedTime = l;
//                    System.out.println("Created new enemy");
//                }
//
//
//
//            }
//
//        };
//        animationTimer.start();
//    }

    public Parent getRoot() {
        return root;
    }
}

