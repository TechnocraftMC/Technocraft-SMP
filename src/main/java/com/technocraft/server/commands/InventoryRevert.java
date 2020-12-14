package com.technocraft.server.commands;

import com.google.common.io.LittleEndianDataInputStream;
import com.technocraft.server.util.Chat;
import com.technocraft.server.util.TechnoInventory;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemFactory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class InventoryRevert implements CommandExecutor, Listener {

    private static ArrayList<TechnoInventory> inventories = new ArrayList<>();


    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args)
    {
        boolean doTeleport = true;
        boolean sendMessage = true;

        if (args.length == 0)
        {
            sender.sendMessage("Please enter a user and any flags.");
            return true;
        }


        if (args.length > 1)
        {
            if (args[1].contains("-nt"))
            {
                doTeleport = false;
            }
            if (args[1].equalsIgnoreCase("-s"))
            {
                sendMessage = false;
            }
        }



        Player player;

        try
        {
            player = Bukkit.getPlayer(args[0]);
        } catch (Exception e)
        {
            sender.sendMessage("Please enter a valid player");
            return true;
        }


        for (int i = inventories.size() - 1; i >= 0; i--)
        {
            TechnoInventory inventory = inventories.get(i);
            if (inventory.getPlayer().equals(player))
            {
                if (!player.isOnline())
                {
                    sender.sendMessage("Player offline.");
                    return true;
                }



/*
                for (ItemStack itemStack : inventory.getInventory())
                {

                    player.getInventory().addItem(new Item

                }
                //inventory.getInventory().clear();*/



                if (doTeleport)
                {
                    player.teleport(inventory.getLocation());
                }

                String message = Chat.message("Teleport", Chat.value(player.getName(), "has their old inventory") + ".");
                if (sendMessage && !player.equals(inventory.getPlayer()))
                {
                    player.sendMessage(message);
                }

                sender.sendMessage(message);
                flushList(inventory.getPlayer(), i);
                return false;

            }

            sender.sendMessage("Player does not have their inventory stored");
        }


        return false;
    }

    /**
     *
     * @param player The player who will have their past death's flushed
     * @param startIndex Will clear all instances of player before the startIndex (exclusively)
     */
    private void flushList(Player player, int startIndex)
    {
        for (int i = startIndex - 1; i >= 0; i--)
        {
            TechnoInventory inventory = inventories.get(i);
            if (inventory.getPlayer().equals(player))
            {
                inventories.remove(i);
            }
        }
    }


    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e)
    {
        inventories.add(new TechnoInventory(e.getEntity(), e.getDrops(), e.getEntity().getLocation(), e.getDroppedExp()));
    }

}
