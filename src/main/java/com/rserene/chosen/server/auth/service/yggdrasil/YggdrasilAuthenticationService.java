package com.rserene.chosen.server.auth.service.yggdrasil;

import java.util.HashSet;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import com.rserene.chosen.server.profile.GameProfile;
import com.rserene.chosen.server.config.service.BaseServiceConfig;
import com.rserene.chosen.server.config.service.yggdrasil.BaseYggdrasilServiceConfig;
import com.rserene.chosen.server.main.RSLBCore;
import com.rserene.chosen.server.flows.workflows.EntrustFlows;
import com.rserene.chosen.server.flows.workflows.Signal;

public class YggdrasilAuthenticationService {
   private final RSLBCore core;

   public YggdrasilAuthenticationService(RSLBCore core) {
      this.core = core;
   }

   public YggdrasilAuthenticationResult hasJoined(String username, String serverId, String ip) {
      Set<BaseYggdrasilServiceConfig> serviceConfigs = this.core
         .getPluginConfig()
         .getServiceIdMap()
         .entrySet()
         .stream()
         .filter(e -> e.getValue() instanceof BaseYggdrasilServiceConfig)
         .map(e -> (BaseYggdrasilServiceConfig)e.getValue())
         .collect(Collectors.toSet());
      if (serviceConfigs.isEmpty()) {
         return YggdrasilAuthenticationResult.ofNoService();
      }

      EntrustFlows<HasJoinedContext> flows = new EntrustFlows(
         serviceConfigs.stream().map(i -> new YggdrasilAuthenticationFlows(this.core, username, serverId, ip, i)).collect(Collectors.toList())
      );
      HasJoinedContext context = new HasJoinedContext(username, serverId, ip);
      Signal run = flows.run(context);
      if (run == Signal.PASSED) {
         return YggdrasilAuthenticationResult.ofAllowed(
            (GameProfile)context.getResponse().get().getValue1(), (BaseYggdrasilServiceConfig)context.getResponse().get().getValue2()
         );
      }

      if (context.getServiceUnavailable().size() == 0) {
         return YggdrasilAuthenticationResult.ofValidationFailed();
      }

      for (Entry<BaseYggdrasilServiceConfig, Throwable> entry : context.getServiceUnavailable().entrySet()) {
         this.core
            .logDebug("An exception occurred during authentication of the yggdrasil service whose ID is " + entry.getKey().getId(), entry.getValue());
      }

      return YggdrasilAuthenticationResult.ofServerBreakdown();
   }
}
