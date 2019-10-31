package self.liang.cryption.example;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

public class SHA1Util
{

   static String photoString = "/9j/4AAQSkZJRgABAQAAAQABAAD/2wBDAAYEBQYFBAYGBQYHBwYIChAKCgkJChQODwwQFxQY\r\n" + "GBcUFhYaHSUfGhsjHBYWICwgIyYnKSopGR8tMC0oMCUoKSj/2wBDAQcHBwoIChMKChMoGhYa\r\n"
            + "KCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCgoKCj/wAAR\r\n" + "CABQADwDASIAAhEBAxEB/8QAGwAAAgIDAQAAAAAAAAAAAAAABgUEBwIDCAD/xAA2EAACAQMC\r\n"
            + "AwUGAwkBAAAAAAABAgMRBAAhBRJBMQYTUSJhgdGRoXEHsRQzkiPhooJDQjIkFf/EABcBAQEB\r\n" + "AQAAAAAAAAAAAAAAAAIDAQT/xAAfEQACAgICAwEAAAAAAAAAAAABABECAxIhMVFBIhP/2gAM\r\n"
            + "AwEAAhEDEQA/ABfaey6R8PkrhfteyrGR5cJrXaCP8Plku8SHa7KS6udEQeGpPIDGbvFqkf29\r\n" + "kisLz96QqsvDU6UyzgQwBBBB5jOW49/vLu/75R+5U+VFOi+/65ZGwfcH8tbLHcoxAGZHKaZL\r\n"
            + "Yz1Ibcyv/uM8V06RqKui0JxPun3Tij/Qjr4huYzybha75ALy3uI2jfkSaqfA+uGwIK7Zf14A\r\n" + "hBr7bAxNVxLNtC94fLlkzWMJ6zp8D7shPttsWP8A1L+wc3ZyF6ioPE+04CdvFlvd8srCMlYe\r\n"
            + "77x6fU1PwGWatqpbBntHt7LvRniXzfk+BSR0PH7syY5aVEmEJNza2QEcNuzEaVpQDN0UqNGX\r\n" + "EPsz020XjThTKvBzoNMJbSytBt/5ZnXvz0PPAKjstzPSITWtvetwyWzofFWzPsva3WzdqorS\r\n"
            + "N3NpdKTQ86Ake0HHI2SUT1WdhGfA6fDHVhtpO57RI7BzDJIvF6GM0/DHIqYBZmsiSzZ42pkB\r\n" + "kbiOEl3EADpi0wkkkDMZpVEq8VK4H9qLm4Td7qNie6Ea93py0r8/ww5gkXicUOhp0PgMR9r9\r\n"
            + "t7+CW8EjkhQvBw0AHjWleuZYLxEA8oAlyZW7stwr0NM0FY+9fjuaktUtU6HIE5PeFTXQ600z\r\n" + "BpCjBQFplcUVC7jZIXn7oju5eOupPjjGxmmeSz7mvEblBoaac6/Uae3BJSQ44RSvKumHvZCy\r\n"
            + "W5hMkiyFY2DKUanm5cxkMnfDQGBJXt5A55D45AjQqCGFDXG90zA/py09AvvxbLwlq8Ui+nAP\r\n" + "fjeQrdLrX/Vvlm55kmheORKqwoakYDntFcL0jgH9H8cxftRdxoZGaCNB1JUAfPEaqARHtfGu\r\n"
            + "2bnLGkgOvTwwYkvx1rrjztXff+rem5JWQsBVl6YKXCsHqOmHYPSBK62/cQ8ixmtCfDLz7NxL\r\n" + "Z7TBGiVLKHJodSRnPdhII3Dmiga1wuuvuXFtNoveyTPwKEVI5NWNOlOQ9flk6/VoDmQQG5JX\r\n"
            + "Zv7Z/ZORmhLGpi/lPvwH7H9q5O0O1rfKs8ALlOB34unMHmNcKYrx+D9RvicrrDzFr5540UvM\r\n" + "SqDnWn44Odod1qvdWcklxHUAxGId2CerFq1B6U05YWzxxSRlJUVxWpDa65HlhiMYjCJwnUim\r\n"
            + "PYe3Bbwg9lfIiiK8tZEUmiGPzAfHrk5re2a2FwGAhrTiby0NaUPFSnXCGS0tkiqYxQagUxfv\r\n" + "UiTWRSBUWUEPw6a05+ulT7MhlqImrfFkMwUQubK+3JjwhYLQaqVoSx5U519gp+OY7B3d88bz\r\n"
            + "3JgCmoUjjoPp0rhjsVoI7dJpfM7aj0xxxAH1xYTrWQnNba0OW3IdusYLaBUVI0CKAKaDn9fX\r\n" + "J35ubTz4qeY0JJ0zWbo15n24pYkv/9kAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA\r\n"
            + "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA\r\n" + "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA\r\n"
            + "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA\r\n" + "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA\r\n"
            + "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA\r\n" + "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA\r\n"
            + "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA\r\n" + "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA\r\n"
            + "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA\r\n" + "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA\r\n"
            + "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA\r\n" + "AAA=";

   // test:CW0srQbf+WZ1789DzwCo7Lcz0iFIVFY4LUw0MDAtQkk0MC0w
   // hIse2PGnDp6jSFTbVURk8Jru7JI=

   public static void main(String[] args) throws UnsupportedEncodingException
   {
      // String str = "1aniOUpLGBz/AAyrLl/XHcMzfypIVFY4LUw0MDAtQkk0MC0w";
      Base64 decoder = new Base64();
      // byte[] bytes = decoder.decode(str);
      // // zF4KdOi2pPGIwlODJDIVhDe+heU=;
      // System.out.println(decoder.encodeToString(SHA1(bytes)));

      String testPhoto = photoString.replaceAll("\r\n", "");
      String lockSN = "HTV8-L400-BI40-0";
      byte[] face = decoder.decode(testPhoto);
      byte[] lockSn = lockSN.getBytes();

      if (face.length > 0x260 + 20)
      {
         byte[] rv = new byte[36];
         blockCopy(face, 0x260, rv, 0, 20);
         blockCopy(lockSn, 0, rv, 20, 16);

         System.out.println(decoder.encodeToString(rv));
         byte[] hash = SHA1(rv);
         System.out.println(decoder.encodeToString(hash));
      }
   }

   public static byte[] SHA1(byte[] data)
   {
      MessageDigest digest = null;
      try
      {
         digest = MessageDigest.getInstance("SHA-1");
      }
      catch (NoSuchAlgorithmException e)
      {
         e.printStackTrace();
         return null;
      }
      digest.update(data);
      byte[] messageDigest = digest.digest();
      return messageDigest;
   }

   public static String getRemoteUnlockHashString(String base64Photo, String lockSN)
   {
      Base64 decoder = new Base64();
      byte[] face = decoder.decode(base64Photo);
      byte[] lockSn = lockSN.getBytes();

      if (face.length > 0x260 + 20)
      {
         byte[] rv = new byte[36];
         blockCopy(face, 0x260, rv, 0, 20);
         blockCopy(lockSn, 0, rv, 20, 16);
         byte[] hash = SHA1(rv);
         return decoder.encodeToString(hash);
      }

      return null;
   }

   public static void blockCopy(byte[] src, int srcStart, byte[] target, int startTar, int leng)
   {
      for (int i = startTar, j = 0; j < leng; i++, j++)
      {
         target[i] = src[srcStart + j];
      }
   }

}
