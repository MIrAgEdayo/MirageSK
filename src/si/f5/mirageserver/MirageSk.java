package si.f5.mirageserver;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class MirageSk extends JavaPlugin {

    MirageSk instance;
    SkriptAddon addon;

    public void onEnable() {
        instance = this;
        addon = Skript.registerAddon(this);

        try {
            addon.loadClasses("si.f5.mirageserver", "elements");
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    public MirageSk getInstance () {

        return instance;
    }

    public SkriptAddon getAddonInstance () {

        return addon;
    }
}