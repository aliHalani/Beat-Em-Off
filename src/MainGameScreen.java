import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import jdk.jshell.spi.ExecutionEnv;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class MainGameScreen implements GameScreen {
    final MiniGame parent;
    ArrayList<Enemy> enemies = new ArrayList<Enemy>();
    int enemySpeed = 2;
    final long enemyCreationSpeed = 2;
    final int fireballSpeed = 2;
    final int fireballLength = 2;
    final int GAME_LANE_OFFSET = 300;
    Pane root = new Pane();

    public MainGameScreen(int SC_WIDTH, int SC_HEIGHT, MiniGame pnode) {
        parent = pnode;
        root.setPrefWidth(SC_WIDTH);
        root.setPrefHeight(SC_HEIGHT);
        root.setFocusTraversable(true);

        Player player = new Player(SC_WIDTH / 2, GAME_LANE_OFFSET, root);
        player.Draw();

        root.setOnKeyPressed(e -> {
            System.out.println("key");
            if (e.getCode() == KeyCode.RIGHT) {
                player.Shoot(Sprite.DIRECTION.RIGHT, enemies);
            } else if (e.getCode() == KeyCode.LEFT) {
                player.Shoot(Sprite.DIRECTION.LEFT, enemies);
            }
        });


//        AnimationTimer fireballTimer = new AnimationTimer() {
//            final long now = System.nanoTime();
//
//            @Override
//            public void handle(long l) {
//                if (l - now < fireballLength * 1000 * 1000 * 1000) {
//                    fireball.setTranslateX(fireball.getTranslateX() + fireballSpeed * direction);
//                } else {
//                    parent.getChildren().remove(fireball);
//                }
//
//                System.out.println(fireball.getBoundsInParent());
//
//
//            }
//        };


        AnimationTimer animationTimer = new AnimationTimer() {
            long lastEnemyCreatedTime = System.nanoTime();
            int i = 0;

            @Override
            public void handle(long l) {

                // check for player-enemy collision
                for (Enemy enemy : enemies) {
                    enemy.moveToCenter();
//                    System.out.println(enemy);
                    if (player.collision(enemy)) {
                        System.out.println("Player-enemy collision");
                    }
                }

                // handle fireball logic
                for (Iterator<Fireball> it = player.fireballs.iterator(); it.hasNext();) {
                    Fireball fireball = it.next();

                    // check if fireball timed out
                    if (l - fireball.createdTime > fireballLength * 1000 * 1000 * 1000) {
                        root.getChildren().remove(fireball.imgView);
                        it.remove();
                        continue;
                    }

                    // move fireball
                    fireball.imgView.setTranslateX(fireball.imgView.getTranslateX() +
                            fireballSpeed * (fireball.direction == Sprite.DIRECTION.RIGHT ? 1 : -1));

                    // check for fireball-enemy collision
                    for (Iterator<Enemy> enemyIt = enemies.iterator(); enemyIt.hasNext();) {
                        Enemy enemy = enemyIt.next();
                        if (enemy.collision(fireball.imgView.getBoundsInParent())) {
                            System.out.println("Fireball Collision");
                            root.getChildren().remove(enemy.imgView);
                            enemyIt.remove();
                            root.getChildren().remove(fireball.imgView);
                            it.remove();
                        }
                    }
                }

                // create new enemy
                if ((l - lastEnemyCreatedTime) > enemyCreationSpeed * 1000 * 1000 * 1000 & enemies.size() < 2) {
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
                    i += 1;
                }



            }

        };
        animationTimer.start();




    }

    public Parent getRoot() {
        return root;
    }
}

