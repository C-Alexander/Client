package works.maatwerk.generals.inputcontrollers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import works.maatwerk.generals.utils.logger.Tag;
import works.maatwerk.generals.music.MusicManager;

public class MusicController extends InputAdapter {
    private final MusicManager musicManager;

    public MusicController(MusicManager musicManager) {
        this.musicManager = musicManager;
    }

    @Override
    public boolean scrolled(int change) {
        super.scrolled(change);
        if (!Gdx.input.isKeyPressed(Input.Keys.ALT_LEFT))
            return false;
        adjustVolume(change);
        return true;
    }

    private void adjustVolume(int change) {
        float volumeChange = getVolumeChange(change);
        if (volumeChange < 0 && musicManager.getCurrentSong().getVolume() < 0) {
            volumeChange = 0;
        }
        float newVolume = Math.min(Math.max(musicManager.getCurrentSong().getVolume() + volumeChange, 0f), 1f);
        musicManager.getCurrentSong().setVolume(newVolume);
        Gdx.app.getPreferences(Tag.MUSIC).putFloat("Volume", newVolume);
        Gdx.app.debug(Tag.MUSIC, "Set volume to: " + newVolume);
    }

    private float getVolumeChange(int change) {
        float volumeChange = 0;
        if (change > 0) volumeChange = -0.1f;
        if (change < 0 && musicManager.getCurrentSong().getVolume() < 1f) volumeChange = +0.1f;
        return volumeChange;
    }
}