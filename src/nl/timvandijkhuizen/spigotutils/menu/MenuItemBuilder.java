package nl.timvandijkhuizen.spigotutils.menu;

import java.util.List;
import java.util.Map;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import nl.timvandijkhuizen.spigotutils.inventory.ItemBuilder;

public class MenuItemBuilder extends ItemBuilder {

    private MenuAction listeners;
    private boolean disabled;

    public MenuItemBuilder(ItemStack itemStack) {
        super(itemStack);
    }

    public MenuItemBuilder(Material material) {
        super(material);
    }

    public MenuItemBuilder(Material material, int amount) {
        super(material, amount);
    }

    public MenuItemBuilder setClickListener(MenuAction listener) {
        this.listeners = listener;
        return this;
    }

    public MenuAction getClickListener() {
        return listeners;
    }

    public MenuItemBuilder clone() {
        return new MenuItemBuilder(itemStack.clone());
    }

    public MenuItemBuilder setDurability(short durability) {
        super.setDurability(durability);
        return this;
    }

    public MenuItemBuilder setType(Material material) {
        super.setType(material);
        return this;
    }

    public MenuItemBuilder setName(String name) {
        super.setName(name);
        return this;
    }

    public MenuItemBuilder setAmount(int amount) {
        super.setAmount(amount);
        return this;
    }

    public MenuItemBuilder removeLore() {
        super.removeLore();
        return this;
    }

    public MenuItemBuilder hideAttributes() {
        super.hideAttributes();
        return this;
    }

    public MenuItemBuilder addUnsafeEnchantment(Enchantment enchantment, int level) {
        super.addUnsafeEnchantment(enchantment, level);
        return this;
    }

    public MenuItemBuilder removeEnchantment(Enchantment enchantment) {
        super.removeEnchantment(enchantment);
        return this;
    }

    public MenuItemBuilder addEnchant(Enchantment enchantment, int level) {
        super.addEnchant(enchantment, level);
        return this;
    }

    public MenuItemBuilder addEnchantGlow() {
        super.addEnchantGlow();
        return this;
    }

    public MenuItemBuilder addEnchantments(Map<Enchantment, Integer> enchantments) {
        super.addEnchantments(enchantments);
        return this;
    }

    public MenuItemBuilder setInfinityDurability() {
        super.setInfinityDurability();
        return this;
    }

    public MenuItemBuilder setLore(String... lore) {
        super.setLore(lore);
        return this;
    }

    public MenuItemBuilder setLore(List<String> lore) {
        super.setLore(lore);
        return this;
    }

    public MenuItemBuilder addLore(List<String> line) {
        super.addLore(line);
        return this;
    }

    public MenuItemBuilder setUnbreakable(boolean value) {
        super.setUnbreakable(value);
        return this;
    }

    public MenuItemBuilder addLore(String... lines) {
        super.addLore(lines);
        return this;
    }

    public MenuItemBuilder removeLore(String line) {
        super.removeLore(line);
        return this;
    }

    public MenuItemBuilder removeLore(int index) {
        super.removeLore(index);
        return this;
    }

    public MenuItemBuilder addLore(String line) {
        super.addLore(line);
        return this;
    }

    public MenuItemBuilder setLore(String line, int pos) {
        super.setLore(line, pos);
        return this;
    }

    public MenuItemBuilder setLeatherArmorColor(Color color) {
        super.setLeatherArmorColor(color);
        return this;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

}
