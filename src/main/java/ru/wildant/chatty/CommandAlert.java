package ru.wildant.chatty;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class CommandAlert implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player player)
        {
            if(args.length < 1) {
                player.sendMessage(ChatColor.RED + "Неверное кол-во аргументов.");
                return  false;
            }

            OffsetDateTime time = Clock.systemUTC().instant().atOffset(ZoneOffset.ofHours(3));
            for(Player p : Bukkit.getOnlinePlayers())
            {
                p.sendMessage(ChatColor.DARK_RED + "[ВНИМАНИЕ] " + Extension.JoinStringArray(args, 0, args.length, " ") + " ("+ player.getName() + " | " + time.getHour() + ":" + time.getMinute() + ":" + time.getSecond() +" МСК)");
            }

            return true;
        }

        return false;
    }
}
