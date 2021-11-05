package fr.nilowk.spawnhead;

import fr.nilowk.spawnhead.command.AddGroup;
import fr.nilowk.spawnhead.command.AddLoc;
import fr.nilowk.spawnhead.db.ProfileSerializationManager;
import fr.nilowk.spawnhead.listener.HeadInteract;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private ProfileSerializationManager profileSerializationManager;

    @Override
    public void onEnable() {

        saveDefaultConfig();
        this.profileSerializationManager = new ProfileSerializationManager();

        getCommand("addloc").setExecutor(new AddLoc(this));
        getCommand("addgroup").setExecutor(new AddGroup(this));

        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new HeadInteract(this), this);

    }

    public ProfileSerializationManager getProfileSerializationManager() {

        return profileSerializationManager;

    }

}
