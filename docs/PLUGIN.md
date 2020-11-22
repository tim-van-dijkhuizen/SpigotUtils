# Plugin API
In order to use services you need the PluginBase class, which extends JavaPlugin. One import thing to know is that you should **not** override the onEnable() and onDisable() methods, instead you should override init, load or unload to make sure all code is executed in the right order. Services also support these methods and they are executed in the same order.

### init()
This method is only executed once when the plugin is first loaded. Use this to for example load your static resources.

### load()
This method is executed onEnable() after init() and whenever the plugin/service is loaded. Use this to for example load your configuration.

### unload()
This method is executed onDisable() and whenever the plugin/service is unloaded. Use this to for example perform some cleanup before shutting down or reloading.