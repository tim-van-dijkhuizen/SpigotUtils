package nl.timvandijkhuizen.spigotutils.config;

import org.bukkit.entity.Player;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

public interface ConfigObject {

    void serialize(ByteArrayDataOutput output);
    void deserialize(ByteArrayDataInput input);
    
    String getInputName();
    void createNew(Player player, Runnable callback);
    
}
