package nl.timvandijkhuizen.spigotutils.config;

import org.bukkit.Material;

public class ConfigIcon {

    private Material material;
    private String name;
    
    public ConfigIcon(Material material, String name) {
        this.material = material;
        this.name = name;
    }
    
    public Material getMaterial() {
        return material;
    }
    
    public String getName() {
        return name;
    }
    
}
