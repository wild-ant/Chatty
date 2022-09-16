package ru.wildant.chatty;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandAdbuy implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player player) {
            //Проверка на необоходимое кол-во аргументов
            if(args.length < 1) {
                player.sendMessage(ChatColor.RED + "Неверное кол-во аргументов.");
                return  false;
            }

            for(Player p : Bukkit.getOnlinePlayers())
            {
                if ((boolean)Chatty.instance.getConfig().get(p.getName() + ".is-global-chat-visible")) p.sendMessage(ChatColor.YELLOW + "[КУПЛЮ] " + Extension.JoinStringArray(args, 0, args.length, " ") + " ("+ player.getName() +")");
            }

            return  true;
        }

        return false;
    }
}
