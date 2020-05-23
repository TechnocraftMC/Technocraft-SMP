package com.technocraft.server;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    @Override
    public void onEnable()
    {
        System.out.println("Technocraft Core has been enabled!");
    }

    @Override
    public void onDisable()
    {
        System.out.println("Technocraft core has been disabled!");
    }
}
