import javafx.scene.media.AudioClip;

public class SoundManager {
    private AudioClip killEnemy = new AudioClip(this.getClass().getResource("resources/sound/kill_sound.mp3").toExternalForm());
    private AudioClip loseGame = new AudioClip(this.getClass().getResource("resources/sound/fail.wav").toExternalForm());
    private AudioClip winLevel = new AudioClip(this.getClass().getResource("resources/sound/win.mp3").toExternalForm());
    private AudioClip playetHit = new AudioClip(this.getClass().getResource("resources/sound/player_hit.mp3").toExternalForm());
    private AudioClip shoot = new AudioClip(this.getClass().getResource("resources/sound/firecast.mp3").toExternalForm());
    private AudioClip backgroundMusic = new AudioClip(this.getClass().getResource("resources/sound/background.mp3").toExternalForm());
    public AudioClip titleMusic = new AudioClip(this.getClass().getResource("resources/sound/title_background.mp3").toExternalForm());
    private AudioClip victoryMusic = new AudioClip(this.getClass().getResource("resources/sound/victory_music.mp3").toExternalForm());
    private AudioClip intermediateMusic = new AudioClip(this.getClass().getResource("resources/sound/intermediate_music.mp3").toExternalForm());
    private AudioClip loseMusic = new AudioClip(this.getClass().getResource("resources/sound/lose_music.mp3").toExternalForm());


    public SoundManager() {
        shoot.setVolume(0.6);
        killEnemy.setVolume(2);
        backgroundMusic.setCycleCount(AudioClip.INDEFINITE);
        titleMusic.setCycleCount(AudioClip.INDEFINITE);
        victoryMusic.setCycleCount(AudioClip.INDEFINITE);
        intermediateMusic.setCycleCount(AudioClip.INDEFINITE);
        loseMusic.setCycleCount(AudioClip.INDEFINITE);
    }

    public void killEnemy() {
        killEnemy.play();
    }

    public void loseGame() {
        loseGame.play();
    }

    public void winLevel() {
        winLevel.play();
    }

    public void playerHit() {
        playetHit.play();
    }

    public void shoot() {
        shoot.play();
    }

    public void playBackgroundMusic() {
        backgroundMusic.play();
    }

    public void stopBackgroundMusic() {
        backgroundMusic.stop();
    }

    public void playTitleMusic() {
        titleMusic.play();
    }

    public void stopTitleMusic() {
        titleMusic.stop();
    }

    public void playVictoryMusic() {
        victoryMusic.play();
    }

    public void stopVictoryMusic() {
        victoryMusic.stop();
    }

    public void playIntermediateMusic() {
        intermediateMusic.play();
    }

    public void stopIntermediateMusic() {
        intermediateMusic.stop();
    }

    public void playLoseMusic() {
        loseMusic.play();
    }

    public void stopLoseMusic() {
        loseMusic.stop();
    }

}

