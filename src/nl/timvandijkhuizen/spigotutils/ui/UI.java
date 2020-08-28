package nl.timvandijkhuizen.spigotutils.ui;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class UI {

    public static final String TAB = "  ";
    
    // UI colors
    public static final ChatColor PRIMARY_COLOR = ChatColor.GREEN;
    public static final ChatColor SECONDARY_COLOR = ChatColor.AQUA;
    public static final ChatColor TEXT_COLOR = ChatColor.GRAY;
    public static final ChatColor ERROR_COLOR = ChatColor.RED;

    // UI sounds
    public static final PredefinedSound CLICK_SOUND = new PredefinedSound(Sound.UI_BUTTON_CLICK, 1, 1);
    public static final PredefinedSound SUCCESS_SOUND = new PredefinedSound(Sound.ENTITY_PLAYER_LEVELUP, 1, 1.5f);
    public static final PredefinedSound ERROR_SOUND = new PredefinedSound(Sound.BLOCK_NOTE_BLOCK_BASS, 10.0F, 1.0F);
    public static final PredefinedSound DELETE_SOUND = new PredefinedSound(Sound.ENTITY_ITEM_BREAK, 1, 1);
    
    public static String color(String text, ChatColor... colors) {
        String colorString = "" + ChatColor.RESET;

        for (ChatColor color : colors) {
            colorString += color;
        }

        return colorString + text;
    }
    
    public static void playSound(Player player, PredefinedSound sound) {
        player.playSound(player.getLocation(), sound.getSound(), sound.getVolume(), sound.getPitch());
    }

}
