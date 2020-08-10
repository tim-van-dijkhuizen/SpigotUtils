package nl.timvandijkhuizen.spigotutils.menu;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.Sound;

public class PagedMenu extends Menu {
	
	public static final MenuItemBuilder BACK_BUTTON = new MenuItemBuilder(Material.ARROW).setName("&b&lPrevious page");
	public static final MenuItemBuilder NEXT_BUTTON = new MenuItemBuilder(Material.ARROW).setName("&b&lNext page");
	public static final MenuItemBuilder CLOSE_BUTTON = new MenuItemBuilder(Material.BARRIER).setName("&c&lClose");
	
	private int rows;
	private int columns;
	private int rowOffset;
	private int columnOffset;
	
	private List<MenuItemBuilder> pagedItems = new ArrayList<>();
	
	public PagedMenu(String title, int rows, int columns, int rowOffset, int columnOffset) {
		super(title, findMenuSize(rows, columns, rowOffset, columnOffset));
		
		this.rows = rows;
		this.columns = columns;
		this.rowOffset = rowOffset;
		this.columnOffset = columnOffset;
		
		if((rowOffset + rows + rowOffset + 1) > 6 || (columnOffset + columns) > 9) {
			throw new RuntimeException("Invalid menu sizes");
		}
	}
	
	public PagedMenu(String title, int rows, int columns) {
		this(title, rows, columns, 0, 0);
	}
	
	public PagedMenu addPagedButton(MenuItemBuilder item) {
		pagedItems.add(item);
		return this;
	}
	
	void setPage(int page) {
		int rowStart = rowOffset;
		int columnStart = columnOffset;
		int itemIndex = page * (rows * columns);
		
		// Ignore if page is empty
		if(page < 0 || itemIndex >= pagedItems.size()) {
			return;
		}
		
		for(int row = rowStart; row < (rowStart + rows); row++) {
			for(int column = columnStart; column < (columnStart + columns); column++) {
				removeButton(row, column);
				
				if(itemIndex < pagedItems.size()) {
					setButton(pagedItems.get(itemIndex++), row, column);
				}
			}	
		}
		
		// Add pagination
		setButton(BACK_BUTTON.setClickListener(whoClicked -> {
			whoClicked.playSound(whoClicked.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
			setPage(page - 1);
		}), size.getSize() - 9 + 2);
		
		setButton(NEXT_BUTTON.setClickListener(whoClicked -> {
			whoClicked.playSound(whoClicked.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
			setPage(page + 1);
		}), size.getSize() - 9 + 6);
	}
	
	private static MenuSize findMenuSize(int rows, int columns, int rowOffset, int columnOffset) {
		int max = (rowOffset + rows + rowOffset + 1) * 9;
		
		for(MenuSize size : MenuSize.values()) {
			if(size.getSize() >= max) return size;
		}
		
		return MenuSize.XXL;
	}
	
}
