package com.technocraft.server;

import com.technocraft.server.commands.*;
import com.technocraft.server.listener.*;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import javax.security.auth.login.LoginException;

public class Main extends JavaPlugin {

    private JDA jda;

    @Override
    public void onEnable()
    {
        getCommand("announce").setExecutor(new AnnounceCommand());
        getCommand("chatbypass").setExecutor(new ChatBypass());
        getCommand("global").setExecutor(new GlobalCommand());
        getCommand("opwiz").setExecutor(new OP());
        getCommand("silence").setExecutor(new Silence());
        getCommand("raw").setExecutor(new RawCommand());
        getCommand("gm").setExecutor(new GamemodeCommand());
        getCommand("superchat").setExecutor(new SuperChat());
        getCommand("updater").setExecutor(new Updater(this));
        getCommand("getadmin").setExecutor(new GetAdminCommand(this));

        Bukkit.getPluginManager().registerEvents(new Silence(), this);
        Bukkit.getPluginManager().registerEvents(new DeathEvent(), this);
        Bukkit.getPluginManager().registerEvents(new SuperChat(), this);
        Bukkit.getPluginManager().registerEvents(new ServerPingEvent(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerFirstJoin(this), this);
        Bukkit.getPluginManager().registerEvents(new ChatToDiscord(this), this);


        try
        {
            jda = JDABuilder.createDefault("NjAxODA4NjEwMjIxMjI4MDMy.XTHr8Q.b2Q-tXhyIJ9JSiTJB-B4d4mKqfk").build();
            jda.addEventListener(new DiscordToChat());
        } catch (LoginException e)
        {
            e.printStackTrace();
        }

        System.out.println("Technocraft Core has been enabled!");
    }

    public JDA getJDA()
    {
        return jda;
    }

    @Override
    public void onDisable()
    {
        jda.shutdownNow();
        System.out.println("Technocraft core has been disabled!");
    }
}
