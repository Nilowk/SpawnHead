package fr.nilowk.spawnhead.listener;

import fr.nilowk.spawnhead.Main;
import fr.nilowk.spawnhead.db.FileUtils;
import fr.nilowk.spawnhead.db.Profile;
import fr.nilowk.spawnhead.db.ProfileSerializationManager;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class HeadInteract implements Listener {

    private Main instance;
    private FileConfiguration config;
    private File saveDir;

    public HeadInteract(Main instance) {

        this.instance = instance;
        this.config = instance.getConfig();
        this.saveDir = new File(instance.getDataFolder(), "/profiles/");

    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {

        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        Location loc = event.getClickedBlock().getLocation();

        if (event.getHand() == EquipmentSlot.OFF_HAND) return;

        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {

            if (config.getConfigurationSection("heads").getKeys(false).size() < 1) return;
            for (String str : config.getConfigurationSection("heads").getKeys(false)) {

                ConfigurationSection cf = config.getConfigurationSection("heads." + str);

                Location head = instance.getServer().getWorld(cf.getString("world")).getBlockAt(cf.getInt("x"), cf.getInt("y"), cf.getInt("z")).getLocation();

                if (head == loc) {

                    File file = new File(saveDir, player.getUniqueId() + ".json");
                    String group = cf.getString("group");

                    if (file.exists()) {

                        String json = FileUtils.loadContent(file);

                        Profile profile = instance.getProfileSerializationManager().deserialize(json);
                        if (profile.getHeads().contains(str)) return;

                        profile.getHeads().add(str);
                        headCount(group);
                        json = instance.getProfileSerializationManager().serialize(profile);
                        FileUtils.save(file, json);

                    } else {

                        try {

                            FileUtils.createFile(file);

                        } catch (IOException e) {

                            e.printStackTrace();

                        }

                        Profile profile = new Profile(uuid, player.getName(), new ArrayList<String>(), new ArrayList<Integer>());
                        profile.getHeads().add(str);
                        String json = instance.getProfileSerializationManager().serialize(profile);
                        FileUtils.save(file, json);

                    }

                }

            }

        }

    }

    private int playerHeadCount(Profile profile, String group) {

        int count = 0;

        for (String head : profile.getHeads()) {

            for (String str : config.getConfigurationSection("heads").getKeys(false)) {

                String g = config.getString("heads." + str + ".group");
                if (head.equalsIgnoreCase(str)) {

                    if (g.equalsIgnoreCase(group)) count++;

                }

            }

        }

        return count;

    }

    private int headCount(String group) {

        int count = 0;

        for (String str : config.getConfigurationSection("heads").getKeys(false)) {

            String g = config.getString("heads." + str + ".group");
            if (g.equalsIgnoreCase(group)) count++;

        }

        return count;

    }

}
