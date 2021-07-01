package nl.timvandijkhuizen.spigotutils.helpers;

import org.bukkit.Material;

import com.cryptomorin.xseries.XMaterial;

public class InventoryHelper {

    public static Material parseMaterial(XMaterial material) {
        Material output = material.parseMaterial();
        
        if(output != null) {
            return output;
        }
        
        return Material.BARRIER;
    }
    
}
