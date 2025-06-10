package com.technocraft.server.commands;

import com.technocraft.server.util.Chat;
import com.technocraft.server.util.Help;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;

public class ManagerFlags implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args)
    {

        ArrayList<Help> helpBuilder = new ArrayList<>();
        helpBuilder.add(new Help("/manager p(vp) <enable | disable>", "Toggle PvP"));
        helpBuilder.add(new Help("/manager i(tems) <enable | disable>", "Toggle item pickup/drop"));
        helpBuilder.add(new Help("/manager d(amage) <enable | disable>", "Toggle whether entities take damage"));

        String help = Chat.help("Manager Flags", helpBuilder.toArray(new Help[3]));
        if (args.length < 2)
        {
            sender.sendMessage(help);
            return true;
        }

        boolean isEnabled = false;
        String action;
        if (args[1].equalsIgnoreCase("enable") | args[1].equalsIgnoreCase("e"))
        {
            action = "";
            isEnabled = true;
        } else if (args[1].equalsIgnoreCase("disable") | args[1].equalsIgnoreCase("d"))
        {
            action = " deny";
        } else
        {
            sender.sendMessage(help);
            return true;
        }

        String flagSerialized;

        if (args[0].equalsIgnoreCase("pvp") | args[0].equalsIgnoreCase("p"))
        {
            Bukkit.dispatchCommand(sender, "rg flag __global__ pvp" + action);
            flagSerialized = "PvP";
        } else if (args[0].equalsIgnoreCase("items") | args[0].equalsIgnoreCase("i"))
        {
            Bukkit.dispatchCommand(sender, "rg flag __global__ item-pickup" + action);
            Bukkit.dispatchCommand(sender, "rg flag __global__ item-drop" + action);
            flagSerialized = "Item Pickup/Drop";
        } else if (args[0].equalsIgnoreCase("damage") | args[0].equalsIgnoreCase("d"))
        {
            Bukkit.dispatchCommand(sender, "rg flag __global__ mob-damage" + action);
            flagSerialized = "Mob Damage";
        } else if (args[0].equalsIgnoreCase("block") | args[0].equalsIgnoreCase("b"))
        {
            Bukkit.dispatchCommand(sender, "rg flag __global__ block-break" + action);
            Bukkit.dispatchCommand(sender, "rg flag __global__ block-place" + action);
            flagSerialized = "Block Break/Place";
        } else
        {
            sender.sendMessage(help);
            return true;
        }

        Bukkit.dispatchCommand(sender, "rg reload");

        String value;
        if (isEnabled)
        {
            value = ChatColor.GREEN + "enabled";
        } else
        {
            value = ChatColor.RED + "disabled";
        }

        Bukkit.broadcastMessage(Chat.message("World Management", Chat.value(flagSerialized, "has been " + value + Chat.getBodyColor() + ".")));
        Chat.pingAll();
        return false;

    }
}