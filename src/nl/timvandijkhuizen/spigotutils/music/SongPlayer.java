package nl.timvandijkhuizen.spigotutils.music;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import org.bukkit.entity.Player;

import nl.timvandijkhuizen.spigotutils.SpigotUtils;

public class SongPlayer {

	private Song song;
	private boolean playing = true;
	private short tick = -1;
	private ArrayList<String> playerList = new ArrayList<String>();
	private boolean destroyed = false;
	private Thread playerThread;
	private byte volume = 100;
    protected Consumer<Boolean> endCallback;

    public SongPlayer(Song song) {
        this.song = song;
        createThread();
    }
    
    public SongPlayer(Song song, Consumer<Boolean> endCallback) {
        this.song = song;
        this.endCallback = endCallback; 
        createThread();
    }

    private void createThread() {
        playerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!destroyed) {
                    long startTime = System.currentTimeMillis();
                    
                    synchronized (SongPlayer.this) {
                        if (playing) {
                            tick++;
                            
                            if (tick > song.getLength()) {
                                destroy();
                                if(endCallback != null) endCallback.accept(false);
                                return;
                            }
                            
                            for (String s : playerList) {
                                Player p = SpigotUtils.getInstance().getServer().getPlayerExact(s);
                                
                                if (p == null) continue;
                                
                                playTick(p, tick);
                            }
                        }
                    }
                    
                    long duration = System.currentTimeMillis() - startTime;
                    float delayMillis = song.getDelay() * 50;
                    if (duration < delayMillis) {
                        try {
                            Thread.sleep((long) (delayMillis - duration));
                        } catch (InterruptedException e) { /* do nothing */ }
                    }
                }
            }
        });
        
        playerThread.setPriority(Thread.MAX_PRIORITY);
        playerThread.start();
    }
    
    public void playTick(Player p, int tick) {
        for (Layer l : song.getLayerHashMap().values()) {
            Note note = l.getNote(tick);
            if (note == null) {
                continue;
            }
            
            p.playSound(p.getLocation(), Instrument.getInstrument(note.getInstrument()), (l.getVolume() * (int) volume * 100) / 1000000f, NotePitch.getPitch(note.getKey() - 33));
        }
    }

    private void destroy() {
        synchronized (this) {
            destroyed = true;
            playing = false;
            setTick((short) -1);
            if(endCallback != null) endCallback.accept(true);
        }
    }

    /**
     * Public functions
     * These can be used to interact with the SongPlayer
    */
    public List<String> getPlayers() {
        return Collections.unmodifiableList(playerList);
    }

    public void addPlayer(Player p) {
        synchronized (this) {
			if (playerList.contains(p.getName())) return;
			playerList.add(p.getName());
        }
    }
    
    public void removePlayer(Player p) {
        synchronized (this) {
            playerList.remove(p.getName());
        }
    }
    
    public boolean isPlaying() {
        return playing;
    }

    public void play() {
        this.playing = true;
    }
    
    public void pause() {
        this.playing = false;
    }
    
    public void stop() {
        destroy();
    }

    public short getTick() {
        return tick;
    }

    public void setTick(short tick) {
        this.tick = tick;
    }

    public byte getVolume() {
        return volume;
    }

    public void setVolume(byte volume) {
        this.volume = volume;
    }

    public Song getSong() {
        return song;
    }

}
