package nl.timvandijkhuizen.spigotutils.ui;

import org.bukkit.Sound;

public class PredefinedSound {

    private Sound sound;
    private float volume;
    private float pitch;
    
    public PredefinedSound(Sound sound, float volume, float pitch) {
        this.sound = sound;
        this.volume = volume;
        this.pitch = pitch;
    }
    
    public Sound getSound() {
        return sound;
    }
    
    public float getVolume() {
        return volume;
    }
    
    public float getPitch() {
        return pitch;
    }
    
}
