package ru.wildant.chatty;

import org.bukkit.plugin.java.JavaPlugin;

public class Chatty extends JavaPlugin {
    //Instance
    public static Chatty instance;

    //Setting up
    @Override
    public void onEnable() {
        instance = this;

        this.saveDefaultConfig();

        getLogger().info("Chatty enabled.");
        this.getCommand("tell").setExecutor(new CommandWhisper());
        this.getCommand("adsell").setExecutor(new CommandAdsell());
        this.getCommand("adbuy").setExecutor(new CommandAdbuy());
        this.getCommand("event").setExecutor(new CommandEvent());
        this.getCommand("alert").setExecutor(new CommandAlert());
        this.getCommand("settings").setExecutor(new CommandSettings());
        this.getCommand("toggle-join-leave-messages").setExecutor(new ToggleJoinLeaveMessageCommand());
        this.getCommand("toggle-death-messages").setExecutor(new ToggleDeathMessagesCommand());
        this.getCommand("toggle-mention-sound").setExecutor(new ToggleMentionSoundCommand());
        this.getCommand("toggle-whisper-sound").setExecutor(new ToggleWhisperSound());
        this.getCommand("toggle-global-chat-messages").setExecutor(new ToggleGlobalChatMessagesCommand());
        this.getCommand("toggle-local-chat-messages").setExecutor(new ToggleLocalChatMessagesCommand());
        this.getCommand("toggle-whisper-messages").setExecutor(new ToggleWhisperMessagesCommand());

        this.getServer().getPluginManager().registerEvents(new EventListener(), this);
    }

    @Override
    public void onDisable() {
        getLogger().info("Chatty disabled.");
        this.saveConfig();
    }
}
