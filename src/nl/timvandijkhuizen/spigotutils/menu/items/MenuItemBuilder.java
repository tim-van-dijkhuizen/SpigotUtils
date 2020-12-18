package nl.timvandijkhuizen.spigotutils.menu.items;

import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import com.cryptomorin.xseries.XMaterial;

import nl.timvandijkhuizen.spigotutils.inventory.ItemBuilder;
import nl.timvandijkhuizen.spigotutils.menu.MenuClickListener;

public class MenuItemBuilder extends ItemBuilder {

    private MenuClickListener listener;
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

    /**
     * {@inheritDoc}
     */
    public MenuItemBuilder setType(Material material) {
        super.setType(material);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    public MenuItemBuilder setType(XMaterial material) {
        super.setType(material);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    public MenuItemBuilder setName(String name) {
        super.setName(name);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    public MenuItemBuilder setAmount(int amount) {
        super.setAmount(amount);
        return this;
    }
    
    /**
     * {@inheritDoc}
     */
    public MenuItemBuilder setDurability(short durability) {
        super.setDurability(durability);
        return this;
    }
   
    /**
     * {@inheritDoc}
     */
    public MenuItemBuilder setUnbreakable(boolean unbreakable) {
        super.setUnbreakable(unbreakable);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    public MenuItemBuilder addEnchant(Enchantment enchantment, int level) {
        super.addEnchant(enchantment, level);
        return this;
    }
    
    /**
     * {@inheritDoc}
     */
    public MenuItemBuilder removeEnchantment(Enchantment enchantment) {
        super.removeEnchantment(enchantment);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    public MenuItemBuilder addEnchantGlow() {
        super.addEnchantGlow();
        return this;
    }

    /**
     * {@inheritDoc}
     */
    public MenuItemBuilder removeEnchantGlow() {
        super.removeEnchantGlow();
        return this;
    }
    
    /**
     * {@inheritDoc}
     */
    public MenuItemBuilder hideAttributes() {
        super.hideAttributes();
        return this;
    }

    /**
     * {@inheritDoc}
     */
    public MenuItemBuilder setLore(String... lore) {
        super.setLore(lore);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    public MenuItemBuilder setLore(List<String> lore) {
        super.setLore(lore);
        return this;
    }
    
    /**
     * {@inheritDoc}
     */
    public MenuItemBuilder setLore(int position, String line) {
        super.setLore(position, line);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    public MenuItemBuilder addLore(String... lines) {
        super.addLore(lines);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    public MenuItemBuilder addLore(List<String> lines) {
        super.addLore(lines);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    public MenuItemBuilder removeLore() {
        super.removeLore();
        return this;
    }
    
    /**
     * {@inheritDoc}
     */
    public MenuItemBuilder removeLore(int index) {
        super.removeLore(index);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    public MenuItemBuilder setLeatherArmorColor(Color color) {
        super.setLeatherArmorColor(color);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    public MenuItemBuilder setSkullOwner(UUID uuid) {
        super.setSkullOwner(uuid);
        return this;
    }

    /**
     * Returns the click listener.
     * 
     * @return
     */
    public MenuClickListener getClickListener() {
        return listener;
    }
    
    /**
     * Sets the click listener.
     * 
     * @param listener
     * @return
     */
    public MenuItemBuilder setClickListener(MenuClickListener listener) {
        this.listener = listener;
        return this;
    }
    
    /**
     * Returns whether this button is disabled.
     * 
     * @return
     */
    public boolean isDisabled() {
        return disabled;
    }

    /**
     * Sets whether this button is disabled.
     * When disabled the click listener will not be called.
     * 
     * @param disabled
     */
    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }
    
    /**
     * Returns the type generator.
     * 
     * @return
     */
    public Supplier<Material> getTypeGenerator() {
        return typeGenerator;
    }

    /**
     * Sets the type generator.
     * 
     * The type of this item is set the value supplied by
     * the callback and is updated each time the menu is drawn.
     * 
     * @param typeGenerator
     */
    public void setTypeGenerator(Supplier<Material> typeGenerator) {
        this.typeGenerator = typeGenerator;
    }

    /**
     * Returns the name generator.
     * 
     * @return
     */
    public Supplier<String> getNameGenerator() {
        return nameGenerator;
    }

    /**
     * Sets the name generator.
     * 
     * The name of this item is set the value supplied by
     * the callback and is updated each time the menu is drawn.
     * 
     * @param nameGenerator
     */
    public void setNameGenerator(Supplier<String> nameGenerator) {
        this.nameGenerator = nameGenerator;
    }

    /**
     * Returns the amount generator.
     * 
     * @return
     */
    public Supplier<Integer> getAmountGenerator() {
        return amountGenerator;
    }

    /**
     * Sets the amount generator.
     * 
     * The amount of this item is set the value supplied by
     * the callback and is updated each time the menu is drawn.
     * 
     * @param amountGenerator
     */
    public void setAmountGenerator(Supplier<Integer> amountGenerator) {
        this.amountGenerator = amountGenerator;
    }

    /**
     * Returns the lore generator.
     * 
     * @return
     */
    public Supplier<List<String>> getLoreGenerator() {
        return loreGenerator;
    }

    /**
     * Sets the lore generator.
     * 
     * The lore of this item is set the value supplied by
     * the callback and is updated each time the menu is drawn.
     * 
     * @param loreGenerator
     */
    public void setLoreGenerator(Supplier<List<String>> loreGenerator) {
        this.loreGenerator = loreGenerator;
    }

    /**
     * Returns the glow generator.
     * 
     * @return
     */
    public Supplier<Boolean> getGlowGenerator() {
        return glowGenerator;
    }

    /**
     * Sets the glow generator.
     * 
     * The glow of this item is set the value supplied by
     * the callback and is updated each time the menu is drawn.
     * 
     * @param glowGenerator
     */
    public void setGlowGenerator(Supplier<Boolean> glowGenerator) {
        this.glowGenerator = glowGenerator;
    }

    /**
     * Returns the disabled generator.
     * 
     * @return
     */
    public Supplier<Boolean> getDisabledGenerator() {
        return disabledGenerator;
    }

    /**
     * Sets the disabled generator.
     * 
     * The disabled state of this item is set the value supplied by
     * the callback and is updated each time the menu is drawn.
     * 
     * @param disabledGenerator
     */
    public void setDisabledGenerator(Supplier<Boolean> disabledGenerator) {
        this.disabledGenerator = disabledGenerator;
    }
    
    /**
     * {@inheritDoc}
     */
    public MenuItemBuilder clone() {
        return new MenuItemBuilder(itemStack.clone());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ItemStack build() {
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

        return super.build();
    }

}
