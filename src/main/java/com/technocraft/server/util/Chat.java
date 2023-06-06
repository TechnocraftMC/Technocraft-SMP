package com.technocraft.server.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Chat {
    private static ChatColor prefixColorCode = ChatColor.BLUE;
    private static String prefix = "Server";
    private static char divider = '>';

    private static ChatColor bodyColor = ChatColor.WHITE;

    private static ChatColor valueColor = ChatColor.GOLD;

    private static ChatColor miniAnnouncementColor = ChatColor.AQUA;


    //Get the Technocraft>
    public static String getPrefix(String prefix)
    {
        return getPrefixColorCode() + (prefix == null ? Chat.prefix : prefix) + divider + " ";
    }

    //Get the Technocraft prefix colorcode
    public static String getPrefixColorCode()
    {
        //Make it a string because Bukkit is weird
        return "" + prefixColorCode + "";
    }

    //Get the body color
    public static String getBodyColor()
    {
        return "" + bodyColor + "";
    }

    //Get the body color + body
    public static String getBody(String body)
    {
        return getBodyColor() + body;
    }


    //Make a message with a prefix and a body
    public static String message(String prefix, String body)
    {
        return getPrefix(prefix) + getBody(body);
    }

    public static String getValueColor()
    {
        return "" + valueColor + "";
    }

    public static String value(String value, String body)
    {
        return valueFormatter(value, body, BodyType.NORMAL, true);
    }

    public static String valueOnly(String prefix, String value, String body)
    {
        return getPrefix(prefix) + valueFormatter(value, body, BodyType.NORMAL, false);
    }

    public static String error(String prefix, String error)
    {
        return message(prefix, ChatColor.RED + error);
    }

    public static String getminiAnnColor()
    {
        return "" + miniAnnouncementColor + "";
    }

    //Get the body color + body for mini announcements
    public static String getminiAnn(String announcement)
    {
        return getminiAnnColor() + announcement;
    }

    //Get announcement value format
    public static String valueAnn(String value, String body)
    {
        //Check to see if it is a message like VALUE's value
        return valueFormatter(value, body, BodyType.ANNOUNCEMENT, true);
    }


    private static String valueFormatter(String value, String body, BodyType type, boolean isSpace)
    {
        //Check to see if it is a message like VALUE's value
        if (body.startsWith("'") || body.startsWith(".") || body.startsWith(",") || body.startsWith(")"))
        {
            return (isSpace ? " " : "") + getValueColor() + value + (type.equals(BodyType.NORMAL) ? getBody(body) : getminiAnn(body));
        } else
        {
            return (isSpace ? " " : "") + getValueColor() + value + " " + (type.equals(BodyType.NORMAL) ? getBody(body) : getminiAnn(body));
        }
    }



    public static String helpListFormat(String command, String instruction)
    {
        return getPrefixColorCode() + divider + " " + getValueColor() + command + " " + getBodyColor() + instruction + ".";
    }

    public static String help(String commandName, Help[] helpCommands)
    {
        StringBuilder s = new StringBuilder();
        s.append(message("Help", "Listing Commands for: " + getValueColor() + commandName + getBodyColor() + ". \n"));
        for (int i = 0; i < helpCommands.length; i++)
        {
            Help help = helpCommands[i];
            s.append(helpListFormat(help.getCommand(), help.getInstruction()) + "\n");
        }
        return s.toString();
    }



    public static void ping(Player p)
    {
        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 2F, 1F);
    }
    public static void pingAll()
    {
        for(Player p : Bukkit.getOnlinePlayers())
        {
            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 2F, 1F);
        }
    }


    //SEASON MANAGER


    public static String getSMPrefix() { return ChatColor.translateAlternateColorCodes('&', "&b&lManager" + getBodyColor()); }




}
enum BodyType {
    NORMAL, ANNOUNCEMENT
}