package works.maatwerk.generals.music;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Disposable;
import works.maatwerk.generals.utils.files.Paths;
import works.maatwerk.generals.utils.logger.Tag;
import java.util.ArrayList;
import java.util.List;

public class MusicManager implements Disposable {
    private final List<Music> songs;
    private final AssetManager assetManager;
    private final MusicListener musicListener;
    private Music currentSong;

    public MusicManager(AssetManager assetManager) {
        this.assetManager = assetManager;
        this.songs = new ArrayList<Music>();
        musicListener = new MusicListener(this);
    }

    public void playMusic(String filename) {
        Gdx.app.log(Tag.MUSIC, "Looking for: " + filename + " to play");
        for (Music music : songs) {
            if (assetManager.getAssetFileName(music).equals(filename)) {
                playSongFromStart(music, false);
            }
        }
        Gdx.app.log(Tag.MUSIC, "Finished/gave up looking for " + filename + " to play");
    }

    public void playRandomMusic() {
        Gdx.app.log(Tag.MUSIC, "Playing random song");
        if (songs.size() < 1) {
            initializeMusic();
            if (songs.size() < 1) {
                Gdx.app.error(Tag.MUSIC, "Could not find any music to play randomly.");
                return;
            }
        }
        currentSong = songs.get(MathUtils.random(songs.size() - 1));
        currentSong.setLooping(false);
        playSongFromStart(currentSong, true);
    }

    public void stopAllMusic() {
        Gdx.app.log(Tag.MUSIC, "Stopping ALL Music");
        for (Music music : songs) {
            music.stop();
        }
    }

    public void pauseAllMusic() {
        Gdx.app.log(Tag.MUSIC, "Pausing ALL Music");
        for (Music music : songs) {
            music.pause();
        }
    }

    public void stopCurrentMusic() {
        Gdx.app.log(Tag.MUSIC, "Stopping current song");
        currentSong.stop();
    }

    public void pauseCurrentMusic() {
        Gdx.app.log(Tag.MUSIC, "Pausing current song");
        currentSong.pause();
    }

    public void playCurrentMusic() {
        Gdx.app.log(Tag.MUSIC, "Playing current song");
        currentSong.play();
    }

    public void initializeMusic() {
        Gdx.app.log(Tag.MUSIC, "Initializing MusicManager");
        stopAllMusic();
        songs.clear();
        currentSong = null;
        for (FileHandle fileHandle : Gdx.files.internal(Paths.MUSIC_FOLDER).list(".mp3")) {
            songs.add(assetManager.get(fileHandle.path(), Music.class));
        }
        for (FileHandle fileHandle : Gdx.files.internal(Paths.MUSIC_FOLDER).list(".wav")) {
            songs.add(assetManager.get(fileHandle.path(), Music.class));
        }
        for (FileHandle fileHandle : Gdx.files.internal(Paths.MUSIC_FOLDER).list(".ogg")) {
            songs.add(assetManager.get(fileHandle.path(), Music.class));
        }
        // todo:: figure out a way to make this prettier
    }

    private void playSongFromStart(Music songToPlay, boolean playDifferentSongAfter) {
        Gdx.app.log(Tag.MUSIC, "Playing a song from the start");
        songToPlay.setPosition(0);
        songToPlay.setLooping(!playDifferentSongAfter);
        songToPlay.setVolume(Gdx.app.getPreferences(Tag.MUSIC).getFloat("Volume", 0.5f));
        songToPlay.play();
        if (playDifferentSongAfter) {
            songToPlay.setOnCompletionListener(musicListener);
        }
    }

    private void playNextSong() {
        Gdx.app.log(Tag.MUSIC, "Playing the next song");
        int currentSongIndex = songs.indexOf(currentSong);
        if (currentSongIndex + 1 < songs.size()) {
            playSongFromStart(songs.get(currentSongIndex + 1), true);
        } else {
            playSongFromStart(songs.get(0), true);
        }
    }

    public Music getCurrentSong() {
        return currentSong;
    }

    @Override
    public void dispose() {
        for (Music song : songs) {
            song.dispose();
            Gdx.app.getPreferences(Tag.MUSIC).flush();
        }
    }

    private class MusicListener implements Music.OnCompletionListener {
        private final MusicManager musicManager;
        
        MusicListener(MusicManager musicManager) {
            this.musicManager = musicManager;
        }

        @Override
        public void onCompletion(Music music) {
            musicManager.playNextSong();
        }
    }
}