package ru.wildant.chatty;

import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ToggleDeathMessagesCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player player)
        {
            boolean current = (boolean)Chatty.instance.getConfig().get(player.getName() + ".do-announce-death");
            Chatty.instance.getConfig().set(player.getName() + ".do-announce-death", !current);
            player.spigot().sendMessage(new TextComponent("Сообщения о смерти: " + (!current ? "\u2713" : "\u2717")));

            return true;
        }

        return  false;
    }
}
