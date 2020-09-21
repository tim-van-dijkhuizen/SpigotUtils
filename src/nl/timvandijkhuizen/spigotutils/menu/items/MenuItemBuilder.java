package nl.timvandijkhuizen.spigotutils.menu.items;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import nl.timvandijkhuizen.spigotutils.inventory.ItemBuilder;

public class MenuItemBuilder extends ItemBuilder {

    private MenuItemAction listeners;
    private boolean disabled;
    
    // Dynamic content
    private Supplier<String> nameGenerator;
    private Supplier<Integer> amountGenerator;
    private Supplier<List<String>> loreGenerator;

    public MenuItemBuilder(ItemStack itemStack) {
        super(itemStack);
    }

    public MenuItemBuilder(Material material) {
        super(material);
    }

    public MenuItemBuilder(Material material, int amount) {
        super(material, amount);
    }

    public MenuItemBuilder setClickListener(MenuItemAction listener) {
        this.listeners = listener;
        return this;
    }

    public MenuItemAction getClickListener() {
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
    
    public MenuItemBuilder removeEnchantGlow() {
        super.removeEnchantGlow();
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

    public MenuItemBuilder setSkullOwner(UUID uuid) {
        super.setSkullOwner(uuid);
        return this;
    }
    
    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }
    
    @Override
    public ItemStack toItemStack() {
        if(nameGenerator != null) {
            setName(nameGenerator.get());
        }
        
        if(amountGenerator != null) {
            setAmount(amountGenerator.get());
        }
        
        if(loreGenerator != null) {
            setLore(loreGenerator.get());
        }
        
        return super.toItemStack();
    }
    
    public void setName(Supplier<String> nameGenerator) {
        this.nameGenerator = nameGenerator;
    }
    
    public void setAmount(Supplier<Integer> amountGenerator) {
        this.amountGenerator = amountGenerator;
    }
    
    public void setLore(Supplier<List<String>> loreGenerator) {
        this.loreGenerator = loreGenerator;
    }

}
