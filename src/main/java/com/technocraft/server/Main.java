package com.technocraft.server;

import com.technocraft.server.commands.*;
import com.technocraft.server.listener.DeathEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    @Override
    public void onEnable()
    {
        getCommand("announce").setExecutor(new AnnounceCommand());
        getCommand("chatbypass").setExecutor(new ChatBypass());
        getCommand("global").setExecutor(new GlobalCommand());
        getCommand("opwiz").setExecutor(new OP());
        getCommand("silence").setExecutor(new Silence());
        getCommand("superchat").setExecutor(new SuperChat());
        getCommand("updater").setExecutor(new Updater(this));

        Bukkit.getPluginManager().registerEvents(new Silence(), this);
        Bukkit.getPluginManager().registerEvents(new DeathEvent(), this);
        Bukkit.getPluginManager().registerEvents(new SuperChat(), this);

        System.out.println("Technocraft Core has been enabled!");
    }

    @Override
    public void onDisable()
    {
        System.out.println("Technocraft core has been disabled!");
    }
}
