package com.technocraft.server;

import com.technocraft.server.commands.*;
import com.technocraft.server.commands.announce.AnnounceCommandReceiver;
import com.technocraft.server.commands.global.GlobalCommandReceiver;
import com.technocraft.server.listener.lockdown.ServerPingEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Override
    public void onEnable()
    {
        getCommand("announce").setExecutor(new AnnounceCommandReceiver());
        getCommand("chatbypass").setExecutor(new ChatBypass());
        getCommand("global").setExecutor(new GlobalCommandReceiver());
        getCommand("opwiz").setExecutor(new OP());
        getCommand("silence").setExecutor(new Silence());
        getCommand("raw").setExecutor(new RawCommand());
        getCommand("gm").setExecutor(new GamemodeCommand());
        getCommand("superchat").setExecutor(new SuperChat());
        getCommand("updater").setExecutor(new Updater(this));
        getCommand("manager").setExecutor(new ManagerFlags());

        Bukkit.getPluginManager().registerEvents(new Silence(), this);
        Bukkit.getPluginManager().registerEvents(new SuperChat(), this);
        Bukkit.getPluginManager().registerEvents(new ServerPingEvent(), this);
        System.out.println("Technocraft Core has been enabled!");
    }

    @Override
    public void onDisable()
    {
        System.out.println("Technocraft Core has been disabled!");
    }


}
