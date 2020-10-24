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

    public ItemBuilder clone() {
        return new ItemBuilder(itemStack.clone());
    }

    public ItemStack toItemStack() {
        return itemStack;
    }

    public Material getType() {
        return itemStack.getType();
    }

    public ItemBuilder setType(Material material) {
        itemStack.setType(material);
        return this;
    }

    public ItemBuilder setType(XMaterial material) {
        return setType(material.parseMaterial(true));
    }

    public ItemBuilder setName(String name) {
        ItemMeta meta = itemStack.getItemMeta();

        if (meta != null) {
            meta.setDisplayName(name);
            itemStack.setItemMeta(meta);
        }

        return this;
    }

    public ItemBuilder setAmount(int amount) {
        itemStack.setAmount(amount);
        return this;
    }
    
    public ItemBuilder setDurability(short durability) {
        ItemMeta meta = itemStack.getItemMeta();

        if (meta != null && meta instanceof Damageable) {
            ((Damageable) meta).setDamage(durability);
            itemStack.setItemMeta(meta);
        }

        return this;
    }
    
    public ItemBuilder setUnbreakable(boolean unbreakable) {
        ItemMeta meta = itemStack.getItemMeta();

        if (meta != null) {
            meta.setUnbreakable(unbreakable);
            itemStack.setItemMeta(meta);
        }

        return this;
    }

    public ItemBuilder addEnchant(Enchantment enchantment, int level) {
        ItemMeta meta = itemStack.getItemMeta();

        if (meta != null) {
            meta.addEnchant(enchantment, level, true);
            itemStack.setItemMeta(meta);
        }

        return this;
    }
    
    public ItemBuilder removeEnchantment(Enchantment enchantment) {
        itemStack.removeEnchantment(enchantment);
        return this;
    }

    public ItemBuilder addEnchantGlow() {
        ItemMeta meta = itemStack.getItemMeta();

        if (meta != null) {
            meta.addEnchant(Enchantment.DURABILITY, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            itemStack.setItemMeta(meta);
        }

        return this;
    }

    public ItemBuilder removeEnchantGlow() {
        ItemMeta meta = itemStack.getItemMeta();

        if (meta != null) {
            meta.removeEnchant(Enchantment.DURABILITY);
            meta.removeItemFlags(ItemFlag.HIDE_ENCHANTS);
            itemStack.setItemMeta(meta);
        }

        return this;
    }
    
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

    public List<String> getLore() {
        ItemMeta meta = itemStack.getItemMeta();

        if (meta != null && meta.hasLore()) {
            return meta.getLore();
        }

        return new ArrayList<>();
    }

    public ItemBuilder setLore(String... lore) {
        return setLore(Arrays.asList(lore));
    }

    public ItemBuilder setLore(List<String> lore) {
        ItemMeta meta = itemStack.getItemMeta();

        if (meta != null) {
            meta.setLore(lore);
            itemStack.setItemMeta(meta);
        }

        return this;
    }
    
    public ItemBuilder setLore(int position, String line) {
        List<String> lore = getLore();
        
        lore.set(position, line);
        
        return setLore(lore);
    }

    public ItemBuilder addLore(String... lines) {
        return addLore(Arrays.asList(lines));
    }

    public ItemBuilder addLore(List<String> lines) {
        List<String> lore = getLore();

        for (String line : lines) {
            lore.add(line);
        }

        return setLore(lore);
    }

    public ItemBuilder removeLore() {
        setLore();
        return this;
    }
    
    public ItemBuilder removeLore(int index) {
        List<String> lore = getLore();
        
        lore.remove(index);

        return setLore(lore);
    }

    public ItemBuilder setLeatherArmorColor(Color color) {
        ItemMeta meta = itemStack.getItemMeta();

        if (meta != null && meta instanceof LeatherArmorMeta) {
            LeatherArmorMeta leatherMeta = (LeatherArmorMeta) meta;
            leatherMeta.setColor(color);
            itemStack.setItemMeta(leatherMeta);
        }

        return this;
    }

    public ItemBuilder setSkullOwner(UUID uuid) {
        ItemMeta meta = itemStack.getItemMeta();

        if (meta != null && meta instanceof SkullMeta) {
            SkullMeta skullMeta = (SkullMeta) meta;
            skullMeta = SkullUtils.applySkin(skullMeta, uuid);
            itemStack.setItemMeta(skullMeta);
        }

        return this;
    }

}
