package com.rserene.chosen.server.command.argument;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;

public class StringArgumentType implements ArgumentType<String> {
   public static String readString(StringReader reader) {
      int argBeginning = reader.getCursor();

      while (reader.canRead() && reader.peek() != ' ') {
         reader.skip();
      }

      return reader.getString().substring(argBeginning, reader.getCursor());
   }

   public String parse(StringReader reader) {
      return readString(reader);
   }
}
