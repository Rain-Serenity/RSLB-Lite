package com.rserene.chosen.server.auth.validate.entry;

import java.util.UUID;
import com.rserene.chosen.server.auth.validate.ValidateContext;
import com.rserene.chosen.server.config.service.BaseServiceConfig;
import com.rserene.chosen.server.flows.workflows.BaseFlows;
import com.rserene.chosen.server.flows.workflows.Signal;
import com.rserene.chosen.server.profile.GameProfile;

public class AssignInGameFlows extends BaseFlows<ValidateContext> {

   public Signal run(ValidateContext validateContext) {
      try {
         UUID onlineUUID = validateContext.getBaseServiceAuthenticationResult().getResponse().getId();
         String loginName = validateContext.getBaseServiceAuthenticationResult().getResponse().getName();
         BaseServiceConfig serviceConfig = validateContext.getBaseServiceAuthenticationResult().getServiceConfig();

         UUID inGameUUID = serviceConfig.getInitUUID().generateUUID(onlineUUID, loginName);
         String inGameName = serviceConfig.generateName(loginName);

         GameProfile inGameProfile = validateContext.getInGameProfile();
         inGameProfile.setId(inGameUUID);
         inGameProfile.setName(inGameName);

         return Signal.PASSED;
      } catch (Throwable $ex) {
         throw com.rserene.chosen.server.util.ValueUtil.sneakyThrow($ex);
      }
   }
}
