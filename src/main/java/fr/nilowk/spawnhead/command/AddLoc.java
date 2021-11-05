package fr.nilowk.spawnhead.command;

import fr.nilowk.spawnhead.Main;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

public class AddLoc implements CommandExecutor {

    private Main instance;
    private FileConfiguration config;

    public AddLoc(Main instance) {

        this.instance = instance;
        this.config = instance.getConfig();

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {

            Player player = (Player) sender;
            Location loc = player.getTargetBlockExact(10).getLocation();
            ConfigurationSection cf = config.getConfigurationSection("heads");

            for (String str : cf.getKeys(false)) {

                ConfigurationSection cs = config.getConfigurationSection("heads." + str);
                if (str.equalsIgnoreCase(args[1])) {

                    cs.set("group", args[0]);
                    cs.set("location.world", loc.getWorld());
                    cs.set("location.x", loc.getX());
                    cs.set("location.y", loc.getY());
                    cs.set("location.z", loc.getZ());
                    instance.saveConfig();
                    player.sendMessage(config.getString("commands.addloc.message.edit"));
                    return true;

                }

            }

            cf.addDefault(args[1] + ".group", args[0]);
            cf.addDefault(args[1] + ".location.world", loc.getWorld());
            cf.addDefault(args[1] + ".location.x", loc.getX());
            cf.addDefault(args[1] + ".location.y", loc.getY());
            cf.addDefault(args[1] + ".location.z", loc.getZ());
            instance.saveConfig();
            player.sendMessage(config.getString("commands.addloc.message.add"));

        }

        return false;

    }
}
