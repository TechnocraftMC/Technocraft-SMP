package com.technocraft.server.commands.announce;

import com.technocraft.server.commands.global.GlobalCommand;
import com.technocraft.server.util.Chat;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class AnnounceCommandReceiver implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (args.length == 0)
        {
            String help = Chat.help("Server Announce", new String[]{"/announce <message>"}, new String[]{"Send the server a message"});
            sender.sendMessage(help);
            Chat.help("Server Announce", new String[]{"/announce <message>"}, new String[]{"Send the server a message"});
            return false;
        }


        StringBuilder sb = new StringBuilder();
        String message;
        for (int i = 0; i < args.length; i++)
        {
            sb.append(args[i]).append(" ");
        }


        message = sb.toString().trim();

        new GlobalCommand().global(message);
        new AnnounceCommand().announce(null, message);


        return false;
    }
}