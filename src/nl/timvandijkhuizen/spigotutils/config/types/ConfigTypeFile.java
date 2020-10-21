package nl.timvandijkhuizen.spigotutils.config.types;

import java.io.File;
import java.util.function.Consumer;
import java.util.regex.Pattern;

import nl.timvandijkhuizen.spigotutils.config.ConfigOption;
import nl.timvandijkhuizen.spigotutils.config.ConfigType;
import nl.timvandijkhuizen.spigotutils.config.OptionConfig;
import nl.timvandijkhuizen.spigotutils.menu.actions.ActionFileExplorer;
import nl.timvandijkhuizen.spigotutils.menu.items.MenuItemClick;

public class ConfigTypeFile implements ConfigType<File> {

    private File root;
    private Pattern[] allowed;

    public ConfigTypeFile() {
        this(new File("."), null);
    }

    public ConfigTypeFile(File root) {
        this(root, null);
    }

    public ConfigTypeFile(Pattern[] allowed) {
        this(new File("."), allowed);
    }

    public ConfigTypeFile(File root, Pattern[] allowed) {
        this.root = root;
        this.allowed = allowed;
    }

    @Override
    public File getValue(OptionConfig config, ConfigOption<File> option) {
        String filePath = config.getString(option.getPath());

        if (filePath == null) {
            return null;
        }

        return new File(filePath);
    }

    @Override
    public void setValue(OptionConfig config, ConfigOption<File> option, File value) {
        config.set(option.getPath(), value != null ? value.getPath() : "");
    }

    @Override
    public String getValueLore(OptionConfig config, ConfigOption<File> option) {
        File value = getValue(config, option);
        return value != null ? value.getPath() : "";
    }

    @Override
    public boolean isValueEmpty(OptionConfig config, ConfigOption<File> option) {
        File value = getValue(config, option);
        return value == null || !value.exists();
    }

    @Override
    public void getValueInput(OptionConfig config, ConfigOption<File> option, MenuItemClick event, Consumer<File> callback) {
        new ActionFileExplorer(root, getValue(config, option), allowed, callback, event.getMenu()).onClick(event);
    }

}
