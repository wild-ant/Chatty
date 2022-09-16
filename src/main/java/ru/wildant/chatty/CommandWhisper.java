package ru.wildant.chatty;

import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandWhisper implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player player) {
            //Проверка на необоходимое кол-во аргументов
            if(args.length < 2) {
                player.sendMessage(ChatColor.RED + "Неверное кол-во аргументов.");
                return false;
            }

            //Проверка на существование игрока-цели
            Player target = Bukkit.getPlayer(args[0]);
            if(target == null)
            {
                player.sendMessage(ChatColor.RED + "Игрока с данным именем не существует или он не в сети.");
                return false;
            }

            if(!(boolean)Chatty.instance.getConfig().get(target.getName() + ".is-whisper-visible"))
            {
                player.spigot().sendMessage(new ComponentBuilder("Игрок-цель отключил личные сообщения и ваше сообщение не было получено.").color(net.md_5.bungee.api.ChatColor.LIGHT_PURPLE).create());
                return true;
            }

            ComponentBuilder playerBuilder = new ComponentBuilder("[")
                    .append(new TextComponent("Вы"))
                    .append(" | ")
                    .append(target.getName())
                    .append("]")
                    .append(" \u00BB ")
                    .append(Extension.JoinStringArray(args, 1, args.length, " "));
            ComponentBuilder targetBuilder = new ComponentBuilder("[")
                    .append(new TextComponent(player.getName()))
                    .append(" | ")
                    .append("Вы")
                    .append("]")
                    .append(" \u00BB ")
                    .append(Extension.JoinStringArray(args, 1, args.length, " "));

            playerBuilder.getComponent(0).setColor(net.md_5.bungee.api.ChatColor.GOLD);
            playerBuilder.getComponent(1).setColor(net.md_5.bungee.api.ChatColor.GREEN);
            playerBuilder.getComponent(2).setColor(net.md_5.bungee.api.ChatColor.GRAY);
            playerBuilder.getComponent(3).setColor(net.md_5.bungee.api.ChatColor.GREEN);
            playerBuilder.getComponent(4).setColor(net.md_5.bungee.api.ChatColor.GOLD);
            playerBuilder.getComponent(5).setColor(net.md_5.bungee.api.ChatColor.GRAY);
            playerBuilder.getComponent(6).setColor(net.md_5.bungee.api.ChatColor.WHITE);

            targetBuilder.getComponent(0).setColor(net.md_5.bungee.api.ChatColor.GOLD);
            targetBuilder.getComponent(1).setColor(net.md_5.bungee.api.ChatColor.GREEN);
            targetBuilder.getComponent(2).setColor(net.md_5.bungee.api.ChatColor.GRAY);
            targetBuilder.getComponent(3).setColor(net.md_5.bungee.api.ChatColor.GREEN);
            targetBuilder.getComponent(4).setColor(net.md_5.bungee.api.ChatColor.GOLD);
            targetBuilder.getComponent(5).setColor(net.md_5.bungee.api.ChatColor.GRAY);
            targetBuilder.getComponent(6).setColor(net.md_5.bungee.api.ChatColor.WHITE);


            player.spigot().sendMessage(playerBuilder.create());
            target.spigot().sendMessage(targetBuilder.create());
            if ((boolean)Chatty.instance.getConfig().get(target.getName() + ".do-alert-whisper")) target.playSound(target.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1, 1);
            return true;
        }

        return false;
    }
}
