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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import jdk.jshell.spi.ExecutionEnv;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Level implements GameScreen {
    final MiniGame parent;
    ArrayList<Enemy> enemies = new ArrayList<Enemy>();
    final double enemyCreationSpeed = 1.2;
    final int fireballSpeed = 2;
    final int GAME_LANE_OFFSET = 300;
    Pane root = new Pane();
    GameInfo gameInfo;

    public Level(int SC_WIDTH, int SC_HEIGHT, GameInfo gameInfo, MiniGame pnode) {
        this.gameInfo = gameInfo;
        parent = pnode;
        root.setPrefWidth(SC_WIDTH);
        root.setPrefHeight(SC_HEIGHT);
        root.setFocusTraversable(true);

        Player player = new Player(SC_WIDTH / 2, GAME_LANE_OFFSET, root);
        player.Draw();

        root.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.RIGHT) {
                player.Shoot(Sprite.DIRECTION.RIGHT, enemies);
            } else if (e.getCode() == KeyCode.LEFT) {
                player.Shoot(Sprite.DIRECTION.LEFT, enemies);
            }
        });

        // Score label
        Text scoreLabel = new Text(30, 50, "");
        scoreLabel.textProperty().bind(gameInfo.score.asString("Score: %d"));
        scoreLabel.setFont(Font.font("Verdana", 32));
        scoreLabel.setStyle("-fx-font-weight: bold");
        scoreLabel.setFill(Color.WHITE);
        root.getChildren().add(scoreLabel);

        // Enemies left label
        Text enemyLabel = new Text(30, 90, "");
        enemyLabel.textProperty().bind(gameInfo.enemiesLeft.asString("Enemies Left: %d"));
        enemyLabel.setFont(Font.font("Verdana", 32));
        enemyLabel.setStyle("-fx-font-weight: bold");
        enemyLabel.setFill(Color.WHITE);
        root.getChildren().add(enemyLabel);

        // Heart container
        gameInfo.heartContainer.setTranslateX(SC_WIDTH / 2 - (gameInfo.heartContainer.getBoundsInParent().getWidth() * 3) / 2);
        gameInfo.heartContainer.setTranslateY(GAME_LANE_OFFSET - 30);
        root.getChildren().add(gameInfo.heartContainer);

        AnimationTimer animationTimer = new AnimationTimer() {
            long lastEnemyCreatedTime = System.nanoTime();
            int enemiesToCreate = gameInfo.enemiesLeft.get();

            @Override
            public void handle(long l) {

                // moves enemies and check for player-enemy collision
                for (Enemy enemy : enemies) {
                    if (enemy.beingPushedBack) {
                        continue;
                    }

                    enemy.moveToCenter();

                    if (player.collision(enemy)) {
                        enemy.pushBack();

                        if (gameInfo.decreaseLife() == 0) {
                            root.getChildren().add(new LoseGameScreen(gameInfo, SC_WIDTH, SC_HEIGHT, parent).getRoot());
                            stop();
                        }
                    }
                }

                // handle fireball logic
                for (Iterator<Fireball> it = player.fireballs.iterator(); it.hasNext();) {
                    Fireball fireball = it.next();

                    // check if fireball timed out
                    if (l - fireball.createdTime > gameInfo.fireballLength * 1000 * 1000 * 1000) {
                        root.getChildren().remove(fireball.imgView);
                        it.remove();
                        gameInfo.score.set(gameInfo.score.get() - 1);
                        continue;
                    }

                    // move fireball
                    fireball.imgView.setTranslateX(fireball.imgView.getTranslateX() +
                            fireballSpeed * (fireball.direction == Sprite.DIRECTION.RIGHT ? 1 : -1));

                    // check for fireball-enemy collision
                    for (Iterator<Enemy> enemyIt = enemies.iterator(); enemyIt.hasNext();) {
                        Enemy enemy = enemyIt.next();

                        // hit enemy
                        if (enemy.collision(fireball.imgView.getBoundsInParent()) & !enemy.beingPushedBack) {
                            System.out.println("Fireball Collision");
                            root.getChildren().remove(enemy.imgView);
                            root.getChildren().remove(enemy.boundingBox);
                            enemyIt.remove();
                            root.getChildren().remove(fireball.imgView);
                            it.remove();
                            gameInfo.score.set(gameInfo.score.get() + 1);
                            gameInfo.enemiesLeft.set(gameInfo.enemiesLeft.get() - 1);
                        }

                        // check if beat level
                        if (gameInfo.enemiesLeft.get() == 0) {
                            System.out.println("Level won");
                        }


                    }
                }

                // create new enemy
                if ((l - lastEnemyCreatedTime) > enemyCreationSpeed * 1000 * 1000 * 1000 & enemiesToCreate > 0) {
                    Sprite.DIRECTION dir = Sprite.DIRECTION.values()[new Random().nextInt(Sprite.DIRECTION.values().length)];
                    int enemyX;
                    if (dir == Sprite.DIRECTION.LEFT) {
                        enemyX = SC_WIDTH + 300;
                    } else {
                        enemyX = -300;
                    }

                    Enemy enemy = new Enemy(gameInfo.enemySpeed, enemyX, GAME_LANE_OFFSET + 20, dir, root);
                    enemies.add(enemy);
                    enemiesToCreate -= 1;
                    lastEnemyCreatedTime = l;
                }



            }

        };
//        animationTimer.start();
        root.getChildren().add(new LoseGameScreen(gameInfo, SC_WIDTH, SC_HEIGHT, parent).getRoot());



    }

    public Parent getRoot() {
        return root;
    }
}

