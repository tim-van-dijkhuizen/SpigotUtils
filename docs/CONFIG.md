# Config API
An API that lets you manage your config in an object oriented way. This API allows you to register options that help you write fail-safe code.

### Features
* Value validation
* Default value
* Easy menu intergration
* Option meta

### Sources
* Yaml
* Json

### Types
* Boolean
* Currency
* Date format
* Domain
* File
* Integer
* List
* Locale
* Message (string with chatcolor support)
* Password
* String

### Examples
##### Setup
```java
YamlConfig config = config = new YamlConfig(this);

ConfigOption<String> optionText = new ConfigOption<>("path.text", "Title Text", XMaterial.PAPER, ConfigTypes.STRING).setDefaultValue("My Text");
            
config.addOption(optionText);
```

##### Getting a value
```java
String text = config.getOptionValue("path.text");
ConsoleHelper.printInfo("Text Value: " + text);
```

##### Setting a value
```java
String newText = "New Text!";
config.setOptionValue("path.text", newText);
```