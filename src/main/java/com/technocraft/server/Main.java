package com.technocraft.server;

import com.technocraft.server.commands.*;
import com.technocraft.server.commands.announce.AnnounceCommandReceiver;
import com.technocraft.server.commands.global.GlobalCommandReceiver;
import com.technocraft.server.listener.*;
import com.technocraft.server.listener.seasonmanager.SMChat;
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
        getCommand("announce").setExecutor(new AnnounceCommandReceiver());
        getCommand("chatbypass").setExecutor(new ChatBypass());
        getCommand("global").setExecutor(new GlobalCommandReceiver());
        getCommand("opwiz").setExecutor(new OP());
        getCommand("silence").setExecutor(new Silence());
        getCommand("raw").setExecutor(new RawCommand());
        getCommand("gm").setExecutor(new GamemodeCommand());
        getCommand("superchat").setExecutor(new SuperChat());
        getCommand("updater").setExecutor(new Updater(this));
        getCommand("revert").setExecutor(new InventoryRevert(this));
        getCommand("getadmin").setExecutor(new GetAdminCommand(this));
        getCommand("debug-firstjoin").setExecutor(new PlayerFirstJoin(this));
        getCommand("sc").setExecutor(new SMChat());
        SMChat.actionBar(this);
        getCommand("manager").setExecutor(new ManagerFlags());
        //getCommand("holiday").setExecutor(new ChristmasManager(this));

        Bukkit.getPluginManager().registerEvents(new Silence(), this);
        Bukkit.getPluginManager().registerEvents(new DeathEvent(this), this);
        Bukkit.getPluginManager().registerEvents(new SuperChat(), this);
        Bukkit.getPluginManager().registerEvents(new ServerPingEvent(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerFirstJoin(this), this);
        Bukkit.getPluginManager().registerEvents(new ChatToDiscord(this), this);
        Bukkit.getPluginManager().registerEvents(new InventoryRevert(this), this);
        Bukkit.getPluginManager().registerEvents(new JoinLeave(this), this);
        Bukkit.getPluginManager().registerEvents(new CommandListener(this), this);
        Bukkit.getPluginManager().registerEvents(new SMChat(), this);
        Bukkit.getPluginManager().registerEvents(new ChristmasManager(this), this);


        /*File logs = new File(this.getDataFolder().getPath() + "/data.txt");
        if (!logs.exists())
        {
            try
            {
                logs.createNewFile();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        Bukkit.getPluginManager().registerEvents(new DamageListener(logs), this);*/

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
