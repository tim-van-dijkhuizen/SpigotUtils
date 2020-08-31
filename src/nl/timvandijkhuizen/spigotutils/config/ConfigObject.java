package nl.timvandijkhuizen.spigotutils.config;

import org.bukkit.entity.Player;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

public interface ConfigObject {

    void serialize(ByteArrayDataOutput output);
    void deserialize(ByteArrayDataInput input);
    
    String getItemName();
    String[] getItemLore();
    
    void getInput(Player player, Runnable callback);
    
}
