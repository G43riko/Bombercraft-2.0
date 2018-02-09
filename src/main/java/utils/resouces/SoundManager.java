package utils.resouces;

import java.util.HashMap;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import utils.AudioPlayer;

final class SoundManager {
    private final HashMap<String, AudioPlayer> sounds = new HashMap<>();

    @Nullable
    public AudioPlayer loadAudio(@NotNull String name) {
        AudioPlayer result = null;
        try {
            result = new AudioPlayer(name);
            sounds.put(name, result);
        }
        catch (Exception e) {
            throw new Error("chyba pri načítavaní zvuku: " + name);
        }
        return result;
    }

    @Nullable
    public AudioPlayer getAudio(@NotNull String name) {
        return sounds.get(name);
    }

    @Nullable
    public AudioPlayer checkAndGetAudio(@NotNull String name) {
        AudioPlayer result = sounds.get(name);
        if (result != null) {
            return result;
        }
        return loadAudio(name);
    }
}
