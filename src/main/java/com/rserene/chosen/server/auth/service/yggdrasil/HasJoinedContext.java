package com.rserene.chosen.server.auth.service.yggdrasil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
import lombok.Generated;
import com.rserene.chosen.server.util.Pair;
import com.rserene.chosen.server.profile.GameProfile;
import com.rserene.chosen.server.config.service.yggdrasil.BaseYggdrasilServiceConfig;

public class HasJoinedContext {
   private final String username;
   private final String serverId;
   private final String ip;
   private final AtomicReference<Pair<GameProfile, BaseYggdrasilServiceConfig>> response = new AtomicReference<>();
   private final Map<BaseYggdrasilServiceConfig, Throwable> serviceUnavailable = new ConcurrentHashMap<>();

   protected HasJoinedContext(String username, String serverId, String ip) {
      this.username = username;
      this.serverId = serverId;
      this.ip = ip;
   }

   @Generated
   public String getUsername() {
      return this.username;
   }

   @Generated
   public String getServerId() {
      return this.serverId;
   }

   @Generated
   public String getIp() {
      return this.ip;
   }

   @Generated
   public AtomicReference<Pair<GameProfile, BaseYggdrasilServiceConfig>> getResponse() {
      return this.response;
   }

   @Generated
   public Map<BaseYggdrasilServiceConfig, Throwable> getServiceUnavailable() {
      return this.serviceUnavailable;
   }
}
