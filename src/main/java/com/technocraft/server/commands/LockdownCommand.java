package com.technocraft.server.commands;

import com.technocraft.server.Main;
import com.technocraft.server.commands.announce.AnnounceCommand;
import com.technocraft.server.commands.global.GlobalCommand;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.time.Duration;

public class LockdownCommand implements CommandExecutor {
    private Main main;
    public static boolean isLocked;

    public LockdownCommand(Main main)
    {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args)
    {
        if (isLocked)
        {
            disableLockdownMode();
        } else { enableLockdownMode(); }
        return false;

    }

    private void enableLockdownMode()
    {
        new AnnounceCommand().announce("Maintenance Mode", "The server is entering maintenance mode");

        Bukkit.getScheduler().runTaskLater(main, new Runnable() {
            @Override
            public void run()
            {
                new GlobalCommand().global("All server members will be kicked.", true);
            }
        }, toTicks(5));

        Bukkit.getScheduler().runTaskLater(main, new Runnable() {
            @Override
            public void run()
            {
                new GlobalCommand().global("Please check Discord for updates.", true);
            }
        }, toTicks(7));

        Bukkit.getScheduler().runTaskLater(main, new Runnable() {
            @Override
            public void run()
            {
                for (Player player : Bukkit.getOnlinePlayers())
                {
                    if (!player.isOp() || !player.hasPermission("group.admin"))
                    {
                        player.kick(Component.text("Server maintenance mode has begun. Please check Discord for updates."));
                    }
                }
                new GlobalCommand().global("All players kicked", true);
                isLocked = true;
            }
        }, toTicks(12));
    }

    private void disableLockdownMode()
    {
        new AnnounceCommand().announce("Maintenance Mode", "The server is no longer in maintenance mode");
        new GlobalCommand().global("Whitelist disabled", true);
        isLocked = false;
    }

    private Long toTicks(int seconds)
    {
        return seconds * 20L;
    }
}
