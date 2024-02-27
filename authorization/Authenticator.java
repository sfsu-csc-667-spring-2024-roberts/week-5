package authorization;

import java.nio.charset.Charset;
import java.util.Base64;

public class Authenticator {
  public static void main(String[] args) {
    Authenticator auth = new Authenticator(args[0]);

    String result = auth.decryptBasicUserNameAndPassword();
    System.out.println(String.format("Decrypted result for [%s] is [%s]", args[0], result));

    String[] contents = result.split(":");
    System.out.println(String.format("Username: %s Password: %s", contents[0], contents[1]));
  }

  private String encryptedUserNameAndPassword;

  public Authenticator(String encryptedUserNameAndPassword) {
    this.encryptedUserNameAndPassword = encryptedUserNameAndPassword;
  }

  public String decryptBasicUserNameAndPassword() {
    String credentials = new String(
        Base64.getDecoder().decode(this.encryptedUserNameAndPassword),
        Charset.forName("UTF-8"));

    return credentials;
  }

}
