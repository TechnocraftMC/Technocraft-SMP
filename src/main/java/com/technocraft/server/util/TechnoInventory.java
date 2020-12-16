package com.technocraft.server.util;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class TechnoInventory {

    private Player player;
    private ItemStack[] inventory;
    private Location location;
    private List<ItemStack> drops;

    public TechnoInventory(Player player, ItemStack[] inventory, Location location, List<ItemStack> drops)
    {
        this.player = player;
        this.inventory = inventory;
        this.location = location;
        this.drops = drops;

    }

    public ItemStack[] getInventory()
    {
        return inventory;
    }

    public Player getPlayer()
    {
        return player;
    }

    public Location getLocation()
    {
        return location;
    }

    public void removeDrops()
    {
    }
}
