package nl.timvandijkhuizen.spigotutils.menu.items;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import com.cryptomorin.xseries.XMaterial;

import nl.timvandijkhuizen.spigotutils.inventory.ItemBuilder;

public class MenuItemBuilder extends ItemBuilder {

    private MenuItemAction listeners;
    private boolean disabled;

    // Dynamic content
    private Supplier<Material> typeGenerator;
    private Supplier<String> nameGenerator;
    private Supplier<Integer> amountGenerator;
    private Supplier<List<String>> loreGenerator;
    private Supplier<Boolean> glowGenerator;
    private Supplier<Boolean> disabledGenerator;

    public MenuItemBuilder() {
        this(XMaterial.BARRIER, 1);
    }
    
    public MenuItemBuilder(Material material) {
        super(material, 1);
    }

    public MenuItemBuilder(Material material, int amount) {
        super(material, amount);
    }

    public MenuItemBuilder(XMaterial material) {
        super(material, 1);
    }

    public MenuItemBuilder(XMaterial material, int amount) {
        super(material.parseMaterial(true), amount);
    }

    public MenuItemBuilder(ItemStack itemStack) {
        super(itemStack);
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

    public MenuItemBuilder setType(Material material) {
        super.setType(material);
        return this;
    }

    public MenuItemBuilder setType(XMaterial material) {
        return setType(material.parseMaterial(true));
    }

    public MenuItemBuilder setName(String name) {
        super.setName(name);
        return this;
    }

    public MenuItemBuilder setAmount(int amount) {
        super.setAmount(amount);
        return this;
    }
    
    public MenuItemBuilder setDurability(short durability) {
        super.setDurability(durability);
        return this;
    }
    
    public MenuItemBuilder setUnbreakable(boolean unbreakable) {
        super.setUnbreakable(unbreakable);
        return this;
    }

    public MenuItemBuilder addEnchant(Enchantment enchantment, int level) {
        super.addEnchant(enchantment, level);
        return this;
    }
    
    public MenuItemBuilder removeEnchantment(Enchantment enchantment) {
        super.removeEnchantment(enchantment);
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
    
    public MenuItemBuilder hideAttributes() {
        super.hideAttributes();
        return this;
    }

    public MenuItemBuilder setLore(String... lore) {
        return setLore(Arrays.asList(lore));
    }

    public MenuItemBuilder setLore(List<String> lore) {
        super.setLore(lore);
        return this;
    }
    
    public MenuItemBuilder setLore(int position, String line) {
        List<String> lore = getLore();
        
        lore.set(position, line);
        
        return setLore(lore);
    }

    public MenuItemBuilder addLore(String... lines) {
        return addLore(Arrays.asList(lines));
    }

    public MenuItemBuilder addLore(List<String> lines) {
        List<String> lore = getLore();

        for (String line : lines) {
            lore.add(line);
        }

        return setLore(lore);
    }

    public MenuItemBuilder removeLore() {
        setLore();
        return this;
    }
    
    public MenuItemBuilder removeLore(int index) {
        List<String> lore = getLore();
        
        lore.remove(index);

        return setLore(lore);
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
        if (typeGenerator != null) {
            setType(typeGenerator.get());
        }

        if (nameGenerator != null) {
            setName(nameGenerator.get());
        }

        if (amountGenerator != null) {
            setAmount(amountGenerator.get());
        }

        if (loreGenerator != null) {
            setLore(loreGenerator.get());
        }

        if (glowGenerator != null) {
            if (glowGenerator.get()) {
                addEnchantGlow();
            } else {
                removeEnchantGlow();
            }
        }

        if (disabledGenerator != null) {
            setDisabled(disabledGenerator.get());
        }

        return super.toItemStack();
    }

    public Supplier<Material> getTypeGenerator() {
        return typeGenerator;
    }

    public void setTypeGenerator(Supplier<Material> typeGenerator) {
        this.typeGenerator = typeGenerator;
    }

    public Supplier<String> getNameGenerator() {
        return nameGenerator;
    }

    public void setNameGenerator(Supplier<String> nameGenerator) {
        this.nameGenerator = nameGenerator;
    }

    public Supplier<Integer> getAmountGenerator() {
        return amountGenerator;
    }

    public void setAmountGenerator(Supplier<Integer> amountGenerator) {
        this.amountGenerator = amountGenerator;
    }

    public Supplier<List<String>> getLoreGenerator() {
        return loreGenerator;
    }

    public void setLoreGenerator(Supplier<List<String>> loreGenerator) {
        this.loreGenerator = loreGenerator;
    }

    public Supplier<Boolean> getGlowGenerator() {
        return glowGenerator;
    }

    public void setGlowGenerator(Supplier<Boolean> glowGenerator) {
        this.glowGenerator = glowGenerator;
    }

    public Supplier<Boolean> getDisabledGenerator() {
        return disabledGenerator;
    }

    public void setDisabledGenerator(Supplier<Boolean> disabledGenerator) {
        this.disabledGenerator = disabledGenerator;
    }

}
