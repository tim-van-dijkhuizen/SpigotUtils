package nl.timvandijkhuizen.spigotutils.ui;

import com.cryptomorin.xseries.XSound;

public class PredefinedSound {

    private XSound sound;
    private float volume;
    private float pitch;

    public PredefinedSound(XSound sound, float volume, float pitch) {
        this.sound = sound;
        this.volume = volume;
        this.pitch = pitch;
    }

    public XSound getSound() {
        return sound;
    }

    public float getVolume() {
        return volume;
    }

    public float getPitch() {
        return pitch;
    }

}
