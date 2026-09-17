package com.rserene.chosen.server.command.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.rserene.chosen.server.command.CommandHandler;
import com.rserene.chosen.server.util.MessageUtil;
import org.bukkit.command.CommandSender;

public class RootCommand {
   private final CommandHandler handler;

   public RootCommand(CommandHandler handler) {
      this.handler = handler;
   }

   public LiteralArgumentBuilder<CommandSender> register(LiteralArgumentBuilder<CommandSender> literalArgumentBuilder) {
      return literalArgumentBuilder
         .then(
            this.handler.literal("reload")
               .requires(sender -> sender.hasPermission("rslb.reload"))
               .executes(this::executeReload)
         )
         .then(new MInfoCommand(this.handler).register(this.handler.literal("info")))
         .then(new MHelpCommand(this.handler).register(this.handler.literal("help")));
   }

   private int executeReload(CommandContext<CommandSender> context) {
      try {
         CommandHandler.getCore().reload();
         MessageUtil.sendLegacy(context.getSource(), CommandHandler.getCore().getLanguageHandler().getMessage("command_message_reloaded"));
         return 0;
      } catch (Throwable $ex) {
         throw com.rserene.chosen.server.util.ValueUtil.sneakyThrow($ex);
      }
   }
}
