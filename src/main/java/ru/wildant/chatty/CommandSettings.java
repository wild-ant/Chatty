package ru.wildant.chatty;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandSettings implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player player)
        {
            ComponentBuilder joinLeaveBuilder = new ComponentBuilder("Сообщения о входе/выходе \u00bb ").append("[Переключить]");
            ComponentBuilder deathBuilder = new ComponentBuilder("Сообщения о смерти \u00bb ").append("[Переключить]");
            ComponentBuilder mentionSoundBuilder = new ComponentBuilder("Звуковое оповешение при упоминании в чате \u00bb ").append("[Переключить]");
            ComponentBuilder whisperSoundBuilder = new ComponentBuilder("Звуковое оповешение о личных сообщениях \u00bb ").append("[Переключить]");
            ComponentBuilder globalChatBuilder = new ComponentBuilder("Видимость глобального чата \u00bb ").append("[Переключить]");
            ComponentBuilder localChatBuilder = new ComponentBuilder("Видимость локального чата \u00bb ").append("[Переключить]");
            ComponentBuilder whisperBuilder = new ComponentBuilder("Видимость личных сообщений \u00bb ").append("[Переключить]");

            joinLeaveBuilder.getComponent(1).setColor(ChatColor.GOLD);
            deathBuilder.getComponent(1).setColor(ChatColor.GOLD);
            mentionSoundBuilder.getComponent(1).setColor(ChatColor.GOLD);
            whisperSoundBuilder.getComponent(1).setColor(ChatColor.GOLD);
            globalChatBuilder.getComponent(1).setColor(ChatColor.GOLD);
            localChatBuilder.getComponent(1).setColor(ChatColor.GOLD);
            whisperBuilder.getComponent(1).setColor(ChatColor.GOLD);

            joinLeaveBuilder.getComponent(1).setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/toggle-join-leave-messages"));
            deathBuilder.getComponent(1).setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/toggle-death-messages"));
            mentionSoundBuilder.getComponent(1).setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/toggle-mention-sound"));
            whisperSoundBuilder.getComponent(1).setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/toggle-whisper-sound"));
            globalChatBuilder.getComponent(1).setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/toggle-global-chat-messages"));
            localChatBuilder.getComponent(1).setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/toggle-local-chat-messages"));
            whisperBuilder.getComponent(1).setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/toggle-whisper-messages"));

            player.spigot().sendMessage(joinLeaveBuilder.create());
            player.spigot().sendMessage(deathBuilder.create());
            player.spigot().sendMessage(mentionSoundBuilder.create());
            player.spigot().sendMessage(whisperSoundBuilder.create());
            player.spigot().sendMessage(globalChatBuilder.create());
            player.spigot().sendMessage(localChatBuilder.create());
            player.spigot().sendMessage(whisperBuilder.create());

            return true;
        }

        return false;
    }
}
