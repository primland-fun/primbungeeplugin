package ru.primland.bungee_plugin;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    public static final Pattern separatorPattern = Pattern.compile("(&[a-f0-9]|&#[a-f0-9]{6})?((?:&[rlonmk])+)?([^&]+)");

    public static final Map<String, ChatColor> colors = new HashMap<>();

    static {
        colors.put("&0", ChatColor.BLACK);
        colors.put("&1", ChatColor.DARK_BLUE);
        colors.put("&2", ChatColor.DARK_GREEN);
        colors.put("&3", ChatColor.DARK_AQUA);
        colors.put("&4", ChatColor.DARK_RED);
        colors.put("&5", ChatColor.DARK_PURPLE);
        colors.put("&6", ChatColor.GOLD);
        colors.put("&7", ChatColor.GRAY);
        colors.put("&8", ChatColor.DARK_GRAY);
        colors.put("&9", ChatColor.BLUE);
        colors.put("&a", ChatColor.GREEN);
        colors.put("&b", ChatColor.AQUA);
        colors.put("&c", ChatColor.RED);
        colors.put("&d", ChatColor.LIGHT_PURPLE);
        colors.put("&e", ChatColor.YELLOW);
        colors.put("&f", ChatColor.WHITE);
    }

    @Contract("_ -> new")
    public static @NotNull TextComponent translate(String text) {
        TextComponent parent = new TextComponent();
        Matcher matcher = separatorPattern.matcher(text);
        while(matcher.find()) {
            TextComponent child = new TextComponent(matcher.group(3));
            String colorCode = matcher.group(1);
            String formatCodes = matcher.group(2);
            if(colorCode != null && !colorCode.startsWith("&#"))
                child.setColor(colors.get(colorCode));

            if(colorCode != null && colorCode.startsWith("&#"))
                child.setColor(ChatColor.of(colorCode.replace("&", "")));

            if(formatCodes != null) {
                for(String format : formatCodes.split("&")) {
                    if(format.equals("r"))
                        child.setReset(true);

                    if(format.equals("l"))
                        child.setBold(true);

                    if(format.equals("o"))
                        child.setItalic(true);

                    if(format.equals("n"))
                        child.setUnderlined(true);

                    if(format.equals("m"))
                        child.setStrikethrough(true);

                    if(format.equals("k"))
                        child.setObfuscated(true);
                }
            }

            parent.addExtra(child);
        }

        return parent;
    }
}
