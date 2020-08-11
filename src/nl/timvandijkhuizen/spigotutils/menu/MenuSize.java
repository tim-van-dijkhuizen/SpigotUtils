package nl.timvandijkhuizen.spigotutils.menu;

public enum MenuSize {

	XS(9),
	SM(18),
	MD(27),
	LG(36),
	XL(45),
	XXL(54);
	
	private int slots;
	
	MenuSize(int slots) {
		this.slots = slots;
	}
	
	public int getSlots() {
		return slots;
	}
	
}
