package com.rserene.chosen.server.main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.Generated;
import com.rserene.chosen.server.RSLB;
import com.rserene.chosen.server.api.RSLBAPI;
import com.rserene.chosen.server.api.RSLBAPIProvider;
import com.rserene.chosen.server.player.RSLBPlayerData;
import com.rserene.chosen.server.main.RSLBCoreAPI;
import com.rserene.chosen.server.auth.AuthHandler;
import com.rserene.chosen.server.auth.service.yggdrasil.serialize.GameProfileSerializer;
import com.rserene.chosen.server.command.CommandHandler;
import com.rserene.chosen.server.config.PluginConfig;
import com.rserene.chosen.server.config.service.BaseServiceConfig;
import com.rserene.chosen.server.player.PlayerHandler;
import com.rserene.chosen.server.language.LanguageHandler;
import com.rserene.chosen.server.profile.GameProfile;
import com.rserene.chosen.server.util.MessageUtil;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RSLBCore implements RSLBCoreAPI, RSLBAPI {
   private final RSLB plugin;
   private final Logger logger;
   private volatile boolean debugEnabled;
   private final PluginConfig pluginConfig;
   private final AuthHandler authHandler;
   private final CommandHandler commandHandler;
   private final LanguageHandler languageHandler;
   private final PlayerHandler playerHandler;
   private final Gson gson;

   public RSLBCore(RSLB plugin) {
      this.plugin = plugin;
      this.logger = plugin.getLogger();
      this.languageHandler = new LanguageHandler(this);
      this.pluginConfig = new PluginConfig(plugin.getDataFolder(), this);
      this.authHandler = new AuthHandler(this);
      this.commandHandler = new CommandHandler(this);
      this.playerHandler = new PlayerHandler(this);
       this.gson = new GsonBuilder()
          .setPrettyPrinting()
          .registerTypeAdapter(GameProfile.class, new GameProfileSerializer())
          .create();
   }

   private void showBanner() {
      MessageUtil.sendLegacy(Bukkit.getConsoleSender(), "\u001b[40;36m[RSLB] 正版与 LittleSkin 登录\u001b[0m");
   }

   public void load() throws IOException, URISyntaxException {
      RSLBAPIProvider.setApi(this);
      this.showBanner();
      this.languageHandler.init();
      this.pluginConfig.reload();
      this.commandHandler.init();
      this.playerHandler.register();
      this.logger.info(
         String.format(
            "Loaded, using RSLB v%s on %s - %s",
            this.plugin.getPluginVersion(),
            Bukkit.getName(),
            Bukkit.getVersion()
         )
      );
      this.checkEnvironment();
   }

   private void checkEnvironment() {
      if (!this.plugin.isOnlineModeEnvironment()) {
         this.logger.severe("Please enable online mode, otherwise the plugin will not work!!!");
         this.logger.severe("Server is closing!!!");
         throw new EnvironmentException("offline mode.");
      }

      if (!this.plugin.isForwardedEnvironment()) {
         this.logger.severe("Please enable forwarding, otherwise the plugin will not work!!!");
         this.logger.severe("Server is closing!!!");
         throw new EnvironmentException("do not forward.");
      }
   }

   public void reload() throws IOException, URISyntaxException {
      this.pluginConfig.reload();
      this.languageHandler.reload();
   }

   public void close() {
   }

   @NotNull
   public Collection<BaseServiceConfig> getServices() {
      return Collections.unmodifiableCollection(this.pluginConfig.getServiceIdMap().values());
   }

   @Nullable
   public RSLBPlayerData getPlayerData(@NotNull UUID inGameUUID) {
      return this.playerHandler.getPlayerData(inGameUUID);
   }

   @Generated
   public RSLB getPlugin() {
      return this.plugin;
   }

   @Generated
   public Logger getLogger() {
      return this.logger;
   }

   @Generated
   public boolean isDebugEnabled() {
      return this.debugEnabled;
   }

   @Generated
   public void setDebugEnabled(boolean debugEnabled) {
      this.debugEnabled = debugEnabled;
   }

   public void logDebug(String message) {
      if (this.debugEnabled) {
         this.logger.log(Level.INFO, "[DEBUG] " + message);
      }
   }

   public void logDebug(String message, Throwable throwable) {
      if (this.debugEnabled) {
         this.logger.log(Level.INFO, "[DEBUG] " + message, throwable);
      }
   }

   public void logDebug(Throwable throwable) {
      if (this.debugEnabled) {
         this.logger.log(Level.INFO, "[DEBUG] " + throwable, throwable);
      }
   }

   @Generated
   public PluginConfig getPluginConfig() {
      return this.pluginConfig;
   }

   @Generated
   public AuthHandler getAuthHandler() {
      return this.authHandler;
   }

   @Generated
   public CommandHandler getCommandHandler() {
      return this.commandHandler;
   }

   @Generated
   public LanguageHandler getLanguageHandler() {
      return this.languageHandler;
   }

   @Generated
   public PlayerHandler getPlayerHandler() {
      return this.playerHandler;
   }

   @Generated
   public Gson getGson() {
      return this.gson;
   }

   @Generated
   public String getHttpRequestHeaderUserAgent() {
      return "RSLB/" + this.plugin.getPluginVersion();
   }
}
