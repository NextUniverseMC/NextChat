package main.ml.nextuniverse.chat;

import com.google.common.util.concurrent.FutureCallback;
import com.mrpowergamerbr.temmiewebhook.DiscordMessage;
import com.mrpowergamerbr.temmiewebhook.TemmieWebhook;
import de.btobastian.javacord.DiscordAPI;
import de.btobastian.javacord.Javacord;
import de.btobastian.javacord.entities.message.Message;
import de.btobastian.javacord.listener.message.MessageCreateListener;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by TheDiamondPicks on 8/07/2017.
 */
public class Main extends JavaPlugin implements Listener {
    HashMap<String, String> nickName = new HashMap<String, String>();

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);

        DiscordAPI api = Javacord.getApi("Mzc3NTUzNjUzMzM2MTEzMTYy.DOOnww.ZymYmGMRGsSMXw4F6YmYTnM58GA", true);
        // connect
        api.connect(new FutureCallback<DiscordAPI>() {
            @Override
            public void onSuccess(DiscordAPI api) {
                // register listener
                api.registerListener(new MessageCreateListener() {
                    @Override
                    public void onMessageCreate(DiscordAPI api, Message message) {
                        if (!message.isPrivateMessage()) {
                            if (message.getChannelReceiver().getId().equals("377547884591316993") && !message.getAuthor().isBot()) {
                                Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + message.getAuthor().getName() + " [Discord]" + ChatColor.WHITE
                                + ": " + message.getContent());
                            }
                        }
                    }
                });
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(AsyncPlayerChatEvent e) {
        if (!e.isCancelled()) {
            HoverEvent nick;
            if (nickName.containsKey(e.getPlayer().getName()))
                nick = new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Realname: ").color(ChatColor.GOLD).append(e.getPlayer().getName()).create());
            else
                nick = new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("No nickname set").color(ChatColor.GRAY).create());
            Player p = e.getPlayer();
            e.setCancelled(true);
            if (p.hasPermission("chatformat.owner")) {
                for (Player z : Bukkit.getOnlinePlayers()) {
                    z.spigot().sendMessage(new ComponentBuilder("[Owner] ").color(ChatColor.RED).bold(true).append(p.getDisplayName()).color(ChatColor.GRAY).bold(false).event(nick).append(": ").color(ChatColor.RED).append(e.getMessage()).color(ChatColor.RED).create());
                }
            } else if (p.hasPermission("chatformat.admin")) {
                for (Player z : Bukkit.getOnlinePlayers()) {
                    z.spigot().sendMessage(new ComponentBuilder("[Administrator] ").color(ChatColor.YELLOW).bold(true).append(p.getDisplayName()).color(ChatColor.GRAY).bold(false).event(nick).append(": ").color(ChatColor.YELLOW).append(e.getMessage()).color(ChatColor.YELLOW).create());
                }
            } else if (p.hasPermission("chatformat.mod")) {
                for (Player z : Bukkit.getOnlinePlayers()) {
                    z.spigot().sendMessage(new ComponentBuilder("[Moderator] ").color(ChatColor.DARK_GREEN).bold(true).append(p.getDisplayName()).color(ChatColor.GRAY).bold(false).event(nick).append(": ").color(ChatColor.DARK_GREEN).append(e.getMessage()).color(ChatColor.DARK_GREEN).create());
                }
            } else if (p.hasPermission("chatformat.helper")) {
                for (Player z : Bukkit.getOnlinePlayers()) {
                    z.spigot().sendMessage(new ComponentBuilder("[Helper] ").color(ChatColor.AQUA).bold(true).append(p.getDisplayName()).color(ChatColor.GRAY).bold(false).event(nick).append(": ").color(ChatColor.AQUA).append(e.getMessage()).color(ChatColor.AQUA).create());
                }
            } else if (p.hasPermission("chatformat.supporter")) {
                for (Player z : Bukkit.getOnlinePlayers()) {
                    z.spigot().sendMessage(new ComponentBuilder("[Supporter] ").color(ChatColor.DARK_PURPLE).bold(true).append(p.getDisplayName()).color(ChatColor.GRAY).bold(false).event(nick).append(": ").color(ChatColor.DARK_PURPLE).append(e.getMessage()).color(ChatColor.RESET).create());
                }
            } else {
                for (Player z : Bukkit.getOnlinePlayers()) {
                    z.spigot().sendMessage(new ComponentBuilder(p.getDisplayName()).color(ChatColor.GRAY).bold(false).event(nick).append(": ").color(ChatColor.DARK_GRAY).append(e.getMessage()).color(ChatColor.RESET).create());
                }
            }
            e.setFormat("");

            TemmieWebhook webhook = new TemmieWebhook("https://discordapp.com/api/webhooks/377547902333091858/Dd5G-gx1r7Wk5pug69cBKdO3Q1F4EP-7-So3bOvHTFOOQq0i18jB1itHr6ajTTaL6MCo");
            DiscordMessage message = DiscordMessage.builder()
                    .avatarUrl("https://crafatar.com/avatars/" + e.getPlayer().getName() + "?overlay&default=Steve")
                    .username("TheDiamondPicks [In-Game]")
                    .content(e.getMessage())
                    .build();
            webhook.sendMessage(message);

        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equals("nick")) {
            if (!sender.hasPermission("nextchat.nick"))
                sender.sendMessage(ChatColor.RED + "You must have the Supporter rank to use this command. You can get it at " + ChatColor.WHITE + "/store");
            else {
                if (args.length != 1)
                    sender.sendMessage(ChatColor.RED + "Invalid arguments. Usage: " + ChatColor.RESET + "/nick <nickname>");
                else {
                    if (sender instanceof Player) {
                        if (args[0].equals("off")) {
                            Player p = (Player) sender;
                            p.setDisplayName(p.getName());
                            p.sendMessage(ChatColor.AQUA + "Nickname removed!");
                            nickName.remove(p.getName());
                        } else {
                            try {
                                OfflinePlayer o = Bukkit.getOfflinePlayer(args[0]);
                                if (o.isOnline() || o.hasPlayedBefore())
                                    sender.sendMessage(ChatColor.RED + "You cannot impersonate other players!");
                                else {
                                    if (nickName.containsValue("~" + args[0]))
                                        sender.sendMessage(ChatColor.RED + "That nickname is taken!");
                                    else {
                                        Player p = (Player) sender;
                                        p.setDisplayName("~" + args[0]);
                                        nickName.put(p.getName(), p.getDisplayName());
                                        p.sendMessage(ChatColor.AQUA + "Nickname set!");
                                    }
                                }

                            } catch (NullPointerException e) {
                                if (nickName.containsValue("~" + args[0]))
                                    sender.sendMessage(ChatColor.RED + "That nickname is taken!");
                                else {
                                    Player p = (Player) sender;
                                    p.setDisplayName("~" + args[0]);
                                    nickName.put(p.getName(), p.getDisplayName());
                                    p.sendMessage(ChatColor.AQUA + "Nickname set!");
                                }
                            }
                        }
                    } else {
                        sender.sendMessage("The console cannot use that command");
                    }
                }
            }
        }
        if (command.getName().equals("realname")) {
            if (args.length != 1)
                sender.sendMessage(ChatColor.RED + "Invalid arguments. Usage: " + ChatColor.RESET + "/realname <nickname>");
            else {
                if (args[0].length() > 16)
                    sender.sendMessage(ChatColor.RED + "Invalid arguments. Usage: " + ChatColor.RESET + "/realname <nickname>");
                else {
                    if (!nickName.containsValue("~" + args[0]))
                        sender.sendMessage(ChatColor.RED + "That nickname is not being used.");
                    else {
                        for (Map.Entry<String, String> entry : nickName.entrySet()) {
                            String name = entry.getKey();
                            String nick = entry.getValue();
                            if (nick.equals("~" + args[0])) {
                                sender.sendMessage(ChatColor.WHITE + "~" + args[0] + ChatColor.GREEN + "'s real name is " + ChatColor.WHITE + name);
                                break;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        nickName.remove(e.getPlayer().getName());
        e.getPlayer().setDisplayName(e.getPlayer().getName());
    }
}
