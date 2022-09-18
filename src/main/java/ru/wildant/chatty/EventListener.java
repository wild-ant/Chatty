package ru.wildant.chatty;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class EventListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.setJoinMessage("");

        String playerName = event.getPlayer().getName();

        //Создание записи в кофигурации при первом входе игрока
        if(!Chatty.instance.getConfig().contains(playerName))
        {
            Chatty.instance.getConfig().set(playerName + ".do-announce-join-and-leave", true);
            Chatty.instance.getConfig().set(playerName + ".do-announce-death", true);
            Chatty.instance.getConfig().set(playerName + ".do-alert-mention", true);
            Chatty.instance.getConfig().set(playerName + ".do-alert-whisper", true);
            Chatty.instance.getConfig().set(playerName + ".is-global-chat-visible", true);
            Chatty.instance.getConfig().set(playerName + ".is-local-chat-visible", true);
            Chatty.instance.getConfig().set(playerName + ".is-whisper-visible", true);
        }

        for(Player recipient : Bukkit.getOnlinePlayers())
        {
            if((boolean)Chatty.instance.getConfig().get(recipient.getName() + ".do-announce-join-and-leave", true))
            {
                recipient.spigot().sendMessage(new ComponentBuilder(playerName + " присоединился к игре.").color(ChatColor.YELLOW).create());
            }
        }
    }

    @EventHandler
    public void onPlayerDies(PlayerDeathEvent event)
    {
        String msg = event.getDeathMessage();
        event.setDeathMessage("");

        for(Player recipient : Bukkit.getOnlinePlayers())
        {
            if((boolean)Chatty.instance.getConfig().get(recipient.getName() + ".do-announce-death"))
            {
                recipient.spigot().sendMessage(new ComponentBuilder(msg).color(ChatColor.WHITE).create());
            }
        }
    }

    @EventHandler
    public void onPlayerChatAsync(AsyncPlayerChatEvent event) {
        Player sender = event.getPlayer();
        String msg = event.getMessage();

        ComponentBuilder messageBuilder = new ComponentBuilder("[")
                .append("BoF").color(ChatColor.GREEN)
                .append("] ").color(ChatColor.WHITE)
                .append(sender.getName()).color(ChatColor.GOLD)
                .append(" \u00BB ").color(ChatColor.GRAY)
                .append(msg).color(ChatColor.WHITE);

        messageBuilder.getComponent(3).setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Отправить сообщение").color(ChatColor.YELLOW).create()));
        messageBuilder.getComponent(3).setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/t " + sender.getName()));

        event.setCancelled(true);
        if(msg.charAt(0) != '!') {
            messageBuilder.removeComponent(0);
            messageBuilder.removeComponent(1);
            messageBuilder.removeComponent(2);

            for (Player recipient : event.getRecipients()) {
                if(recipient.getLocation().distance(sender.getLocation()) < 40 && (boolean)Chatty.instance.getConfig().get(recipient.getName() + ".is-local-chat-visible", true)) {
                    if(msg.contains(recipient.getName())) {
                        messageBuilder.getComponent(2).setColor(ChatColor.YELLOW);
                        recipient.spigot().sendMessage(messageBuilder.create());
                        if ((boolean)Chatty.instance.getConfig().get(recipient.getName() + ".do-alert-mention")) recipient.playSound(recipient.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1, 1);
                        messageBuilder.getComponent(2).setColor(ChatColor.WHITE);
                    }
                    else
                    {
                        recipient.spigot().sendMessage(messageBuilder.create());
                    }
                }
            }
        }
        else {
            msg = msg.substring(1);

            messageBuilder.removeComponent(5);
            messageBuilder.append(msg).color(ChatColor.WHITE);

            for (Player recipient : event.getRecipients()) {
                if ((boolean)Chatty.instance.getConfig().get(recipient.getName() + ".is-global-chat-visible")) {
                    if (msg.contains(recipient.getName())) {
                        messageBuilder.getComponent(5).setColor(ChatColor.YELLOW);
                        recipient.spigot().sendMessage(messageBuilder.create());
                        messageBuilder.getComponent(5).setColor(ChatColor.WHITE);
                        if ((boolean)Chatty.instance.getConfig().get(recipient.getName() + ".do-alert-mention")) recipient.playSound(recipient.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1, 1);
                    } else {
                        recipient.spigot().sendMessage(messageBuilder.create());
                    }
                }
            }
        }
    }
}
