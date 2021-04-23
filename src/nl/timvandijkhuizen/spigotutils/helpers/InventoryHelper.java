package nl.timvandijkhuizen.spigotutils.helpers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

public class InventoryHelper {

	public static String serialize(Inventory inventory) throws Exception {
        if(inventory == null) {
            throw new IllegalArgumentException("Inventory can not be null");
        }
	    
        // Write the size of the inventory
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

        try {
            dataOutput.writeInt(inventory.getSize());
            
            for (int i = 0; i < inventory.getSize(); i++) {
                dataOutput.writeObject(inventory.getItem(i));
            }
        } finally {
            dataOutput.close();
        }
    
        return Base64Coder.encodeLines(outputStream.toByteArray());
    }
	
	public static void deserialize(Inventory inventory, String data) throws Exception {
        if(inventory == null) {
            throw new IllegalArgumentException("Inventory can not be null");
        }
        
        if(data == null) {
            throw new IllegalArgumentException("Data can not be null");
        }
	    
        // Read the serialized inventory
        ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
        BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
        
        try {
            int size = dataInput.readInt();
            
            for (int i = 0; i < size; i++) {
                inventory.setItem(i, (ItemStack) dataInput.readObject());
            }
        } finally {
            dataInput.close();
        }
    }
	
}
