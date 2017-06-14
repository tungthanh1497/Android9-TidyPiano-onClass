package techkids.com.android9_tidypiano_onclass.notePlayers;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import java.util.HashMap;

/**
 * Created by tungthanh.1497 on 06/14/2017.
 */

public class NotePlayer {
    public static SoundPool soundPool = new SoundPool(12, AudioManager.STREAM_MUSIC, 1);
    public static HashMap<String, Integer> noteMap = new HashMap<>();


    public static void loadSounds(Context context) {
        loadSound("c", "sound_1", context);
        loadSound("cs", "sound_2", context);
        loadSound("d", "sound_3", context);
        loadSound("ds", "sound_4", context);
        loadSound("e", "sound_5", context);
        loadSound("f", "sound_6", context);
        loadSound("fs", "sound_7", context);
        loadSound("g", "sound_8", context);
        loadSound("gs", "sound_9", context);
        loadSound("a", "sound_10", context);
        loadSound("as", "sound_11", context);
        loadSound("b", "sound_12", context);
    }

    public static int loadSound(String note, String fileName, Context context) {
        int id = context.getResources().getIdentifier(fileName, "raw", context.getPackageName());
        int soundID = soundPool.load(context, id, 1);
        noteMap.put(note, soundID);
        return id;
    }

    public static void playSound(String note) {
        int soundID = noteMap.get(note);
        soundPool.play(soundID, 1, 1, 1, 0, 1);
    }
}
