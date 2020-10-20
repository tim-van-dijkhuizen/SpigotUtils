package nl.timvandijkhuizen.spigotutils.ui;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.cryptomorin.xseries.XSound;

public class UI {

    public static final String TAB = "  ";
    
    // UI colors
    public static final ChatColor COLOR_PRIMARY = ChatColor.GREEN;
    public static final ChatColor COLOR_SECONDARY = ChatColor.AQUA;
    public static final ChatColor COLOR_TEXT = ChatColor.GRAY;
    public static final ChatColor COLOR_SUCCESS = ChatColor.GREEN;
    public static final ChatColor COLOR_ERROR = ChatColor.RED;

    // UI sounds
    public static final PredefinedSound SOUND_CLICK = new PredefinedSound(XSound.UI_BUTTON_CLICK, 1, 1);
    public static final PredefinedSound SOUND_SUCCESS = new PredefinedSound(XSound.ENTITY_PLAYER_LEVELUP, 1, 1.5f);
    public static final PredefinedSound SOUND_ERROR = new PredefinedSound(XSound.BLOCK_NOTE_BLOCK_BASS, 10.0F, 1.0F);
    public static final PredefinedSound SOUND_DELETE = new PredefinedSound(XSound.ENTITY_ITEM_BREAK, 1, 1);
    
    public static String color(String text, ChatColor... colors) {
        String colorString = "" + ChatColor.RESET;

        for (ChatColor color : colors) {
            colorString += color;
        }

        return colorString + text;
    }
    
    public static void playSound(Player player, PredefinedSound sound) {
        player.playSound(player.getLocation(), sound.getSound().parseSound(), sound.getVolume(), sound.getPitch());
    }

}
