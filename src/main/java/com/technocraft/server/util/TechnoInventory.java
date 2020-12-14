package com.technocraft.server.util;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TechnoInventory {

    private Player player;
    private List<ItemStack> inventory;
    private Location location;
    private int exp;

    public TechnoInventory(Player player, List<ItemStack> inventory, Location location, int exp)
    {
        this.player = player;
        this.inventory = inventory;
        this.location = location;
        this.exp = exp;
    }

    public List<ItemStack> getInventory()
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


    public int getExp()
    {
        return exp;
    }
}
