# Menu API
An API for creating advanced menu's in an object oriented way.

### Types
* Menu
* Paged menu
* Structured menu

### Examples
##### Menu
```java
Menu menu = new Menu("Test menu", MenuSize.MD);

// Add clickable item
MenuItemBuilder testButton = new MenuItemBuilder(XMaterial.APPLE);

testButton.setName("Test Button");
testButton.setLore("This is a test button");

testButton.setClickListener(event -> {
    event.getPlayer().sendMessage("Hello World!");
});

menu.setItem(testButton, 11);

// Open menu
menu.open(player);
```

##### Paged menu
```java
PagedMenu menu = new PagedMenu("Test menu");

// Add buttons
for (int i = 1; i <= 42; ++i) {
    MenuItemBuilder item = new MenuItemBuilder(XMaterial.APPLE);

    item.setName("Item " + i);

    menu.addPagedItem(item);
}

menu.open(player);
```

##### Structured menu
```java
int[] buttonSlots = new int[] { 10, 12, 14, 16, 28, 30, 32, 34 };
StructuredMenu menu = new StructuredMenu("Test menu", MenuSize.XXL, buttonSlots);

// Add buttons
for (int i = 1; i <= 42; ++i) {
    MenuItemBuilder item = new MenuItemBuilder(XMaterial.APPLE);

    item.setName("Item " + i);

    menu.addStructuredItem(item);
}

menu.open(player);
```