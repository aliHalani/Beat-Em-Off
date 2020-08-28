import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.scene.Parent;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import javax.crypto.ExemptionMechanism;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Level implements GameScreen {
    final MiniGame parent;
    ArrayList<Enemy> enemies = new ArrayList<Enemy>();
    final int fireballSpeed = 2;
    final int GAME_LANE_OFFSET = 300;
    Pane root = new Pane();
    GameInfo gameInfo;
    final SoundManager soundManager;

    public Level(int SC_WIDTH, int SC_HEIGHT, GameInfo gameInfo, SoundManager soundManager, MiniGame pnode) {
        this.gameInfo = gameInfo;
        this.soundManager = soundManager;
        parent = pnode;
        root.setPrefWidth(SC_WIDTH);
        root.setPrefHeight(SC_HEIGHT);
        root.setFocusTraversable(true);
        root.setOpacity(0);

        Player player = new Player(SC_WIDTH / 2, GAME_LANE_OFFSET, root);
        player.Draw();

        root.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.RIGHT) {
                soundManager.shoot();
                player.Shoot(Sprite.DIRECTION.RIGHT, enemies);
            } else if (e.getCode() == KeyCode.LEFT) {
                soundManager.shoot();
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

        // Level label
        Text levelLabel = new Text(1100, 50, "");
        levelLabel.textProperty().bind(gameInfo.level.asString("Level %d"));
        levelLabel.setFont(Font.font("Verdana", 32));
        levelLabel.setStyle("-fx-font-weight: bold");
        levelLabel.setFill(Color.WHITE);
        root.getChildren().add(levelLabel);

        // Heart container
        gameInfo.heartContainer.setTranslateX(SC_WIDTH / 2 - (47 * 3) / 2);
        gameInfo.heartContainer.setTranslateY(GAME_LANE_OFFSET - 30);
        root.getChildren().add(gameInfo.heartContainer);

        // Beginning transition
        FadeTransition ft = new FadeTransition(Duration.seconds(1), root);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();

        AnimationTimer animationTimer = new AnimationTimer() {
            long lastEnemyCreatedTime = System.nanoTime();
            int enemiesToCreate = gameInfo.enemiesLeft.get();
            double timeToCreateNextEnemy = gameInfo.enemyCreationSpeed;

            @Override
            public void handle(long l) {

                // moves enemies and check for player-enemy collision
                for (Enemy enemy : enemies) {
                    if (enemy.beingPushedBack) {
                        continue;
                    }

                    enemy.moveToCenter();

                    // check if player hit
                    if (player.collision(enemy)) {
                        soundManager.playerHit();
                        enemy.pushBack();

                        // check if game lost
                        if (gameInfo.decreaseLife() == 0) {
                            soundManager.stopBackgroundMusic();
                            soundManager.playLoseMusic();
                            root.setOnKeyPressed(null);
                            root.setOnKeyPressed(e -> {
                                if (e.getCode() == KeyCode.ENTER) {
                                    soundManager.stopLoseMusic();
                                    root.setOnKeyPressed(null);
                                    parent.root.getChildren().remove(root);
                                    parent.startGame(1, new GameInfo(1));
                                } else if (e.getCode() == KeyCode.E) {
                                    System.exit(0);
                                }
                            });
                            root.getChildren().add(new EndLevelScreen(gameInfo, SC_WIDTH, SC_HEIGHT,"You lose", "Press ENTER to restart", parent).getRoot());
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
                            soundManager.killEnemy();
                            root.getChildren().remove(enemy.imgView);
                            root.getChildren().remove(enemy.boundingBox);
                            enemyIt.remove();
                            root.getChildren().remove(fireball.imgView);
                            it.remove();
                            gameInfo.score.set(gameInfo.score.get() + 1);
                            gameInfo.enemiesLeft.set(gameInfo.enemiesLeft.get() - 1);
                            break;
                        }
                    }
                }

                // check if beat level
                if (gameInfo.enemiesLeft.get() == 0) {
                    soundManager.stopBackgroundMusic();
                    soundManager.winLevel();
                    root.setOnKeyPressed(null);
                    String screenTitle;
                    String control1Title;

                    // check if won game
                    if (gameInfo.level.get() == 3) {
                        soundManager.playVictoryMusic();
                        screenTitle = "Congratulations, you won";
                        control1Title = "Press ENTER to restart";
                        root.setOnKeyPressed(e -> {
                            if (e.getCode() == KeyCode.ENTER) {
                                soundManager.stopVictoryMusic();
                                root.setOnKeyPressed(null);
                                parent.root.getChildren().remove(root);
                                parent.startGame(1, new GameInfo(1));
                            } else if (e.getCode() == KeyCode.E) {
                                System.exit(0);
                            }
                        });
                        root.getChildren().add(new EndLevelScreen(gameInfo, SC_WIDTH, SC_HEIGHT, Color.valueOf("#FFD700"), Color.valueOf("#FFA500"), screenTitle, control1Title, parent).getRoot());
                    // show level completion screen
                    } else {
                        soundManager.playIntermediateMusic();
                        screenTitle = String.format("Level %s complete", gameInfo.level.get() == 1 ? "One" : "Two");
                        control1Title = "Press ENTER to continue";
                        root.setOnKeyPressed(e -> {
                            if (e.getCode() == KeyCode.ENTER) {
                                soundManager.stopIntermediateMusic();
                                root.setOnKeyPressed(null);
                                parent.root.getChildren().remove(root);
                                parent.startGame(gameInfo.level.get() + 1, gameInfo);
                            } else if (e.getCode() == KeyCode.E) {
                                System.exit(0);
                            }
                        });
                        root.getChildren().add(new EndLevelScreen(gameInfo, SC_WIDTH, SC_HEIGHT, screenTitle, control1Title, parent).getRoot());
                    }


                    stop();
                }

                // create new enemy
                if ((l - lastEnemyCreatedTime) > timeToCreateNextEnemy * 1000 * 1000 * 1000 & enemiesToCreate > 0) {
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
                    timeToCreateNextEnemy = gameInfo.enemyCreationSpeed + (gameInfo.enemyCreationSpeed * 0.3)
                                                        * ((new Random().nextDouble() * 2) - 1);
                }

            }

        };
        animationTimer.start();

    }

    public Parent getRoot() {
        return root;
    }
}

