package com.rserene.chosen.server.util;

import com.rserene.chosen.server.RSLBL;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class MessageUtil {
   private static final LegacyComponentSerializer LEGACY = LegacyComponentSerializer.legacyAmpersand();

   private MessageUtil() {
   }

   public static void sendLegacy(CommandSender sender, String message) {
      for (String line : message.split("\\r?\\n")) {
         sender.sendMessage(LEGACY.deserialize(line));
      }
   }

   public static void kick(Player player, String message) {
      Player target = player;
      Location location = target.getLocation();
      Bukkit.getRegionScheduler().run(RSLBL.getInstance(), location, task -> {
         if (target.isOnline()) {
            target.kick(LEGACY.deserialize(message));
         }
      });
   }

   public static Set<Player> getPlayers(String name) {
      HashSet<Player> players = new HashSet<>();
      String lowerName = name.toLowerCase(Locale.ROOT);
      for (Player player : Bukkit.getOnlinePlayers()) {
         if (player.getName().toLowerCase(Locale.ROOT).startsWith(lowerName)) {
            players.add(player);
         }
      }
      return players;
   }

   public static Set<Player> getOnlinePlayers() {
      return new HashSet<>(Bukkit.getOnlinePlayers());
   }
}
