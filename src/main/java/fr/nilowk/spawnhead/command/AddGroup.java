package fr.nilowk.spawnhead.command;

import fr.nilowk.spawnhead.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.*;

public class AddGroup implements CommandExecutor {

    private Main instance;
    private FileConfiguration config;

    public AddGroup(Main instance) {

        this.instance = instance;
        this.config = instance.getConfig();

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {

            Player player = (Player) sender;

            if (config.getStringList("group") != null) {

                List<String> group = config.getStringList("group");
                if (group.contains(args[0])) {

                    player.sendMessage(config.getString("commands.addgroup.message.exist").replace("{GROUP}", args[0]));

                }
                group.add(args[0]);
                config.set("group", group);
                instance.saveConfig();

                player.sendMessage(config.getString("commands.addgroup.message.add"));

                return true;

            }

            List<String> group = new ArrayList<>();
            group.add(args[0]);
            config.set("group", group);
            instance.saveConfig();

            player.sendMessage(config.getString("commands.addgroup.message.add"));

        }

        return false;

    }
}
