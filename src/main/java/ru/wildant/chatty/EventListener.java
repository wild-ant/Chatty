package ru.wildant.chatty;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import net.md_5.bungee.api.chat.TextComponent;

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

        //Формирование сообщения Shitty things are happening here... Better redesign this completely CTRL+A, DEL
        TextComponent prefix = new TextComponent("");
        prefix.setColor(ChatColor.GREEN);

        TextComponent divider = new TextComponent(" \u00BB ");
        divider.setColor(ChatColor.GRAY);

        TextComponent prefixMentioned = prefix.duplicate();

        TextComponent mainComponent = new TextComponent(sender.getName());
        mainComponent.setColor(ChatColor.GOLD);
        mainComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Отправить сообщение").color(ChatColor.YELLOW).create()));
        mainComponent.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/t " + sender.getName()));

        TextComponent mainMentionedComponent = mainComponent.duplicate();

        TextComponent msgComponent = new TextComponent(msg);
        msgComponent.setColor(ChatColor.WHITE);

        TextComponent msgMentionedComponent = msgComponent.duplicate();
        msgMentionedComponent.setColor(ChatColor.YELLOW);

        event.setCancelled(true);
        if(msg.charAt(0) != '!') {
            prefix.addExtra(mainComponent);
            prefix.addExtra(divider);
            prefix.addExtra(msgComponent);
            prefixMentioned.addExtra(mainMentionedComponent);
            prefixMentioned.addExtra(divider);
            prefixMentioned.addExtra(msgMentionedComponent);
            for (Player recipient : event.getRecipients()) {
                if(recipient.getLocation().distance(sender.getLocation()) < 40 && (boolean)Chatty.instance.getConfig().get(recipient.getName() + ".is-local-chat-visible", true)) {
                    if(msg.contains(recipient.getName())) {
                        recipient.spigot().sendMessage(prefixMentioned);
                        if ((boolean)Chatty.instance.getConfig().get(recipient.getName() + ".do-alert-mention")) recipient.playSound(recipient.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1, 1);
                    }
                    else
                    {
                        recipient.spigot().sendMessage(prefix);
                    }
                }
            }
        }
        else {
            msg = msg.substring(1);

            prefix.setText("[BoF] ");

            prefixMentioned.setText("[BoF] ");

            prefix.addExtra(mainComponent);
            prefix.addExtra(divider);
            msgComponent.setText(msg);
            prefix.addExtra(msgComponent);

            prefixMentioned.addExtra(mainMentionedComponent);
            prefixMentioned.addExtra(divider);
            msgMentionedComponent.setText(msg);
            prefixMentioned.addExtra(msgMentionedComponent);

            for (Player recipient : event.getRecipients()) {
                if ((boolean)Chatty.instance.getConfig().get(recipient.getName() + ".is-global-chat-visible")) {
                    if (msg.contains(recipient.getName())) {
                        recipient.spigot().sendMessage(prefixMentioned);
                        if ((boolean)Chatty.instance.getConfig().get(recipient.getName() + ".do-alert-mention")) recipient.playSound(recipient.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1, 1);
                    } else {
                        recipient.spigot().sendMessage(prefix);
                    }
                }
            }
        }
    }
}
