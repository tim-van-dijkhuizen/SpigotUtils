package nl.timvandijkhuizen.spigotutils.inventory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import com.cryptomorin.xseries.SkullUtils;
import com.cryptomorin.xseries.XMaterial;

public class ItemBuilder {

    protected ItemStack itemStack;

    public ItemBuilder(Material material) {
        this(material, 1);
    }

    public ItemBuilder(Material material, int amount) {
        itemStack = new ItemStack(material, amount);
    }

    public ItemBuilder(XMaterial material) {
        this(material, 1);
    }

    public ItemBuilder(XMaterial material, int amount) {
        this(material.parseMaterial(true), amount);
    }

    public ItemBuilder(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    /**
     * Returns the type.
     * 
     * @return
     */
    public Material getType() {
        return itemStack.getType();
    }

    /**
     * Sets the type.
     * 
     * @param material
     * @return
     */
    public ItemBuilder setType(Material material) {
        itemStack.setType(material);
        return this;
    }

    /**
     * Sets the type (version safe).
     * 
     * @param material
     * @return
     */
    public ItemBuilder setType(XMaterial material) {
        return setType(material.parseMaterial(true));
    }

    /**
     * Returns the name.
     * 
     * @return
     */
    public String getName() {
        ItemMeta meta = itemStack.getItemMeta();

        if (meta != null) {
            return meta.getDisplayName();
        }

        return null;
    }
    
    /**
     * Sets the name.
     * 
     * @param name
     * @return
     */
    public ItemBuilder setName(String name) {
        ItemMeta meta = itemStack.getItemMeta();

        if (meta != null) {
            meta.setDisplayName(name);
            itemStack.setItemMeta(meta);
        }

        return this;
    }

    /**
     * Sets the amount.
     * 
     * @param amount
     * @return
     */
    public ItemBuilder setAmount(int amount) {
        itemStack.setAmount(amount);
        return this;
    }
    
    /**
     * Sets the durability.
     * 
     * @param durability
     * @return
     */
    public ItemBuilder setDurability(short durability) {
        ItemMeta meta = itemStack.getItemMeta();

        if (meta != null && meta instanceof Damageable) {
            ((Damageable) meta).setDamage(durability);
            itemStack.setItemMeta(meta);
        }

        return this;
    }
    
    /**
     * Sets whether this item is unbreakable.
     * 
     * @param unbreakable
     * @return
     */
    public ItemBuilder setUnbreakable(boolean unbreakable) {
        ItemMeta meta = itemStack.getItemMeta();

        if (meta != null) {
            meta.setUnbreakable(unbreakable);
            itemStack.setItemMeta(meta);
        }

        return this;
    }

    /**
     * Adds an enchantment with the specified level.
     * 
     * @param enchantment
     * @param level
     * @return
     */
    public ItemBuilder addEnchant(Enchantment enchantment, int level) {
        ItemMeta meta = itemStack.getItemMeta();

        if (meta != null) {
            meta.addEnchant(enchantment, level, true);
            itemStack.setItemMeta(meta);
        }

        return this;
    }
    
    /**
     * Removes the specified enchantment
     * 
     * @param enchantment
     * @return
     */
    public ItemBuilder removeEnchantment(Enchantment enchantment) {
        itemStack.removeEnchantment(enchantment);
        return this;
    }

    /**
     * Adds an enchantment glow.
     * 
     * @return
     */
    public ItemBuilder addEnchantGlow() {
        ItemMeta meta = itemStack.getItemMeta();

        if (meta != null) {
            meta.addEnchant(Enchantment.DURABILITY, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            itemStack.setItemMeta(meta);
        }

        return this;
    }

    /**
     * Removes the enchantment glow.
     * 
     * @return
     */
    public ItemBuilder removeEnchantGlow() {
        ItemMeta meta = itemStack.getItemMeta();

        if (meta != null) {
            meta.removeEnchant(Enchantment.DURABILITY);
            meta.removeItemFlags(ItemFlag.HIDE_ENCHANTS);
            itemStack.setItemMeta(meta);
        }

        return this;
    }
    
    /**
     * Hides all item attributes.
     * 
     * @return
     */
    public ItemBuilder hideAttributes() {
        ItemMeta meta = itemStack.getItemMeta();

        if (meta != null) {
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            meta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
            meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
            itemStack.setItemMeta(meta);
        }

        return this;
    }

    /**
     * Returns the lore.
     * 
     * @return
     */
    public List<String> getLore() {
        ItemMeta meta = itemStack.getItemMeta();

        if (meta != null && meta.hasLore()) {
            return meta.getLore();
        }

        return new ArrayList<>();
    }

    /**
     * Sets the lore.
     * 
     * @param lore
     * @return
     */
    public ItemBuilder setLore(String... lore) {
        return setLore(Arrays.asList(lore));
    }

    /**
     * Sets the lore.
     * 
     * @param lore
     * @return
     */
    public ItemBuilder setLore(List<String> lore) {
        ItemMeta meta = itemStack.getItemMeta();

        if (meta != null) {
            meta.setLore(lore);
            itemStack.setItemMeta(meta);
        }

        return this;
    }
    
    /**
     * Sets the specified lore line.
     * 
     * @param position
     * @param line
     * @return
     */
    public ItemBuilder setLore(int position, String line) {
        List<String> lore = getLore();
        
        lore.set(position, line);
        
        return setLore(lore);
    }

    /**
     * Adds lines to the lore.
     * 
     * @param lines
     * @return
     */
    public ItemBuilder addLore(String... lines) {
        return addLore(Arrays.asList(lines));
    }

    /**
     * Adds lines to the lore.
     * 
     * @param lines
     * @return
     */
    public ItemBuilder addLore(List<String> lines) {
        List<String> lore = getLore();

        for (String line : lines) {
            lore.add(line);
        }

        return setLore(lore);
    }

    /**
     * Removes the entire lore.
     * 
     * @return
     */
    public ItemBuilder removeLore() {
        setLore();
        return this;
    }
    
    /**
     * Removes the specified lore line.
     * 
     * @param index
     * @return
     */
    public ItemBuilder removeLore(int index) {
        List<String> lore = getLore();
        
        lore.remove(index);

        return setLore(lore);
    }

    /**
     * Sets the color of leather armor.
     * 
     * @param color
     * @return
     */
    public ItemBuilder setLeatherArmorColor(Color color) {
        ItemMeta meta = itemStack.getItemMeta();

        if (meta != null && meta instanceof LeatherArmorMeta) {
            LeatherArmorMeta leatherMeta = (LeatherArmorMeta) meta;
            leatherMeta.setColor(color);
            itemStack.setItemMeta(leatherMeta);
        }

        return this;
    }

    /**
     * Sets the texture of a skull.
     * 
     * @param uuid
     * @return
     */
    public ItemBuilder setSkullOwner(UUID uuid) {
        ItemMeta meta = itemStack.getItemMeta();

        if (meta != null && meta instanceof SkullMeta) {
            SkullMeta skullMeta = (SkullMeta) meta;
            skullMeta = SkullUtils.applySkin(skullMeta, uuid);
            itemStack.setItemMeta(skullMeta);
        }

        return this;
    }

    public ItemBuilder clone() {
        return new ItemBuilder(itemStack.clone());
    }

    public ItemStack build() {
        return itemStack;
    }

}
