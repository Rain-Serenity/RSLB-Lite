package com.rserene.chosen.server.util;

import java.util.List;
import java.util.UUID;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public class ValueUtil {
   public static UUID getUuidOrNull(String uuid) {
      UUID ret = null;

      try {
         ret = UUID.fromString(uuid.replaceFirst("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})", "$1-$2-$3-$4-$5"));
      } catch (Exception var3) {
      }

      return ret;
   }

   public static boolean isEmpty(String str) {
      return str == null || str.length() == 0;
   }

   public static String transPapi(String s, Pair<?, ?>... pairs) {
      for (int i = 0; i < pairs.length; i++) {
         s = s.replace("{" + pairs[i].getValue1() + "}", pairs[i].getValue2() + "");
         s = s.replace("{" + i + "}", pairs[i].getValue2() + "");
      }

      return s;
   }

   public static String transPapi(String s, List<Pair<?, ?>> pairs) {
      for (int i = 0; i < pairs.size(); i++) {
         s = s.replace("{" + pairs.get(i).getValue1() + "}", pairs.get(i).getValue2().toString());
         s = s.replace("{" + i + "}", pairs.get(i).getValue2().toString());
      }

      return s;
   }

   /**
    * 透传反编译源码中保留下来的受检异常，避免改变原始字节码的异常传播语义。
    */
   public static RuntimeException sneakyThrow(Throwable throwable) {
      ValueUtil.<RuntimeException>sneakyThrow0(throwable);
      return null;
   }

   @SuppressWarnings("unchecked")
   private static <T extends Throwable> void sneakyThrow0(Throwable throwable) throws T {
      throw (T)throwable;
   }
}
