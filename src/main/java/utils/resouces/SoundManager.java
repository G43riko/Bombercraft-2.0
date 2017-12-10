package utils.resouces;

import java.util.HashMap;

import utils.AudioPlayer;

final class SoundManager {
    private final HashMap<String, AudioPlayer> sounds = new HashMap<>();

    public AudioPlayer loadAudio(String name) {
        AudioPlayer result = null;
        try {
            result = new AudioPlayer(name);
            sounds.put(name, result);
        }
        catch (Exception e) {
            System.out.println("chyba pri načítavaní zvuku: " + name);
        }
        return result;
    }

    public AudioPlayer getAudio(String name) {
        return sounds.get(name);
    }

    public AudioPlayer checkAndGetAudio(String name) {
        AudioPlayer result = sounds.get(name);
        if (result != null) {
            return result;
        }
        return loadAudio(name);
    }
}
