package ru.wildant.chatty;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandEvent implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player player)
        {
            if(args.length < 1) {
                player.sendMessage(ChatColor.RED + "Неверное кол-во аргументов.");
                return  false;
            }

            Location pLocation = player.getLocation();
            for(Player p : Bukkit.getOnlinePlayers())
            {
                p.sendMessage(ChatColor.GREEN + "[СОБЫТИЕ] " + Extension.JoinStringArray(args, 0, args.length, " ") + " ("+ player.getName() +" | " + player.getWorld().getEnvironment().name() + " | ("+ pLocation.getBlockX() + ", "+ pLocation.getBlockY() +", " + pLocation.getBlockZ()+"))");
            }

            return true;
        }

        return false;
    }
}
