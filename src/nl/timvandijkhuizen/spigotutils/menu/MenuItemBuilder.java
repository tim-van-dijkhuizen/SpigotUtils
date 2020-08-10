package nl.timvandijkhuizen.spigotutils.menu;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import nl.timvandijkhuizen.spigotutils.inventory.ItemBuilder;

public class MenuItemBuilder extends ItemBuilder {

	private Consumer<Player> onClick;
	
	public MenuItemBuilder(ItemStack itemStack) {
		super(itemStack);
	}
	
	public MenuItemBuilder(Material material) {
		super(material);
	}
	
	public MenuItemBuilder(Material material, int amount) {
		super(material, amount);
	}
	
	public MenuItemBuilder setClickListener(Consumer<Player> onClick) {
		this.onClick = onClick;
		return this;
	}
	
	public Consumer<Player> getClickListener() {
		return onClick;
	}
	
	public MenuItemBuilder clone() {
		return new MenuItemBuilder(itemStack);
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

	public MenuItemBuilder addEnchantGlow(Enchantment ench, int level) {
		super.addEnchantGlow(ench, level);
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


	public MenuItemBuilder addLoreLines(List<String> line) {
		super.addLoreLines(line);
		return this;
	}

	public MenuItemBuilder setUnbreakable(boolean value) {
		super.setUnbreakable(value);
		return this;
	}

	public MenuItemBuilder addLoreLines(String[] line) {
		super.addLoreLines(line);
		return this;
	}

	public MenuItemBuilder removeLoreLine(String line) {
		super.removeLoreLine(line);
		return this;
	}

	public MenuItemBuilder removeLoreLine(int index) {
		super.removeLoreLine(index);
		return this;
	}

	public MenuItemBuilder addLoreLine(String line) {
		super.addLoreLine(line);
		return this;
	}

	public MenuItemBuilder addLoreLine(String line, int pos) {
		super.addLoreLine(line, pos);
		return this;
	}

	public MenuItemBuilder setLeatherArmorColor(Color color) {
		super.setLeatherArmorColor(color);
		return this;
	}
	
}
