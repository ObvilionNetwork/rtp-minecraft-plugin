package ru.obvilion.rtp.utils;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import org.bukkit.Location;

import com.sk89q.worldguard.protection.managers.RegionManager;
import org.bukkit.World;

public class WorldGuardPl {
    public static boolean isClaimed(Location location) {
        World world = location.getWorld();
        RegionManager manager = WorldGuardPlugin.inst().getRegionManager(world);

        ApplicableRegionSet rs = manager.getApplicableRegions(com.sk89q.worldguard.bukkit.BukkitUtil.toVector(location));
        return rs.size() > 0;
    }
}
