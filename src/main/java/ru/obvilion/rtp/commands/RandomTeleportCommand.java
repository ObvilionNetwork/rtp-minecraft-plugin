package ru.obvilion.rtp.commands;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import ru.obvilion.rtp.ObvilionRTP;
import ru.obvilion.rtp.Lang;
import ru.obvilion.rtp.utils.WorldGuardPl;

public class RandomTeleportCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Entity)) {
            sender.sendMessage("You are not Player");
            return false;
        }

        final Entity player = (Entity) sender;
        final World world = Bukkit.getWorlds().get(0);

        Location location = generate();
        location.setY(location.getY() + 4f);

        player.teleport(location);

        new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (Exception e) {

            }

            location.setY(location.getY() - 0.5f);
            player.teleport(location);
        }).start();
        
        world.playSound(location, Sound.ENDERMAN_TELEPORT, 1, 1);
        sender.sendMessage(
            Lang.RTP_OK
                .replace("{0}", location.getBlockX() + "")
                .replace("{1}", location.getBlockY() + "")
                .replace("{2}", location.getBlockZ() + "")
        );

        return true;
    }

    private Location generate() {
        final boolean xIsPositive = Math.random() > 0.5f;
        final boolean zIsPositive = Math.random() > 0.5f;

        final int x = getRandomValue(ObvilionRTP.MIN_RTP_RADIUS, ObvilionRTP.MAX_RTP_RADIUS) * (xIsPositive ? 1 : -1);
        final int z = getRandomValue(ObvilionRTP.MIN_RTP_RADIUS, ObvilionRTP.MAX_RTP_RADIUS) * (zIsPositive ? 1 : -1);

        Location loc = new Location(Bukkit.getWorlds().get(0), x, 200, z);
        int y = loc.getWorld().getHighestBlockYAt(loc);
        loc.setY(y - 1);

        try {
            if (WorldGuardPl.isClaimed(loc)) {
                loc = generate();
            }
        } catch (Exception e) {
            // Ignored
        }

        Material t = loc.getBlock().getType();
        if (t == Material.WATER || t == Material.STATIONARY_WATER || t == Material.LAVA) {
            loc = generate();
        }

        return loc;
    }

    private static int getRandomValue(int min, int max) {
        int value = (int) Math.round(Math.random() * max);

        if (value < min) {
            value = getRandomValue(min, max);
        }

        return value;
    }
}
