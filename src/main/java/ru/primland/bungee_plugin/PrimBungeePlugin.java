package ru.primland.bungee_plugin;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import org.jetbrains.annotations.NotNull;
import ru.primland.bungee_plugin.motd.MotdListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class PrimBungeePlugin extends Plugin {
    private static PrimBungeePlugin instance;
    private static Configuration config;

    @Override
    public void onEnable() {
        instance = this;

        // Loads configuration
        if(!getDataFolder().exists()) getDataFolder().mkdirs();

        File configFile = new File(getDataFolder(), "config.yml");
        if(!configFile.exists()) {
            send("&eКонфигурация не существует, попытка создать &rconfig.yml");
            try {
                FileOutputStream output = new FileOutputStream(configFile);
                InputStream input = getResourceAsStream("config.yml");
                input.transferTo(output);
            } catch(IOException error) {
                send("&cНе удалось сохранить базовую конфигурацию");
                error.printStackTrace();
                return;
            }
        }

        try {
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);
        } catch(IOException error) {
            send("&cНе удалось загрузить конфигурацию");
            error.printStackTrace();
            return;
        }

        // Registers a MOTD listener
        getProxy().getPluginManager().registerListener(this, new MotdListener());

        send("Плагин включён");
    }

    public static void send(String... strings) {
        send(getInstance().getProxy().getConsole(), strings);
    }

    public static void send(@NotNull CommandSender sender, String... strings) {
        List<String> stringList = new ArrayList<>(List.of(strings));

        Configuration config = PrimBungeePlugin.getInstance().getConfig();
        String prefix = "ξ &#65caefPrim&#f4d172Land › &r";
        if(config != null) prefix = config.getString("prefix");
        stringList.add(0, prefix);

        sender.sendMessage(Utils.translate(String.join("", stringList)));
    }

    public static PrimBungeePlugin getInstance() {
        return instance;
    }

    public Configuration getConfig() {
        return config;
    }
}
