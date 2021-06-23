package ru.obvilion.rtp;

import org.bukkit.plugin.java.JavaPlugin;
import ru.obvilion.rtp.commands.RandomTeleportCommand;

import java.util.logging.Logger;

public final class ObvilionRTP extends JavaPlugin {
    final public static int MAX_RTP_RADIUS = 2500;
    final public static int MIN_RTP_RADIUS = 500;
    final public static boolean IS_DEV = false;

    public static Logger logger = null;

    public void onEnable() {
        logger = getLogger();
        this.getCommand("rtp").setExecutor(new RandomTeleportCommand());

        logger.info("Plugin enabled!");
    }

    public void onDisable() {
        logger.info("Plugin disabled!");
    }
}
