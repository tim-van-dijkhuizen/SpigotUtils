# ItemBuilder API
An API that lets you build items in an object oriented way.

### Features
* Type
* Name
* Amount
* Durability
* Unbreakable
* Enchantments
* Enchantment glow
* Hide attributes
* Lore
* Leather armor color
* Skull texture

### Example
```java
ItemBuilder builder = new ItemBuilder(XMaterial.APPLE);
        
builder.setName("Test");
builder.setAmount(10);
builder.setLore("Line 1", "Line 2", "Line 3");
        
player.getInventory().setItem(1, builder.build());
```