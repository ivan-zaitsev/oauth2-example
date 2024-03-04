# Challenge for Authorization Code PKCE-enhanced flow

```
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

public String generateCodeVerifier() {
    SecureRandom secureRandom = new SecureRandom();
    byte[] codeVerifier = new byte[32];
    secureRandom.nextBytes(codeVerifier);
    return Base64.getUrlEncoder().withoutPadding().encodeToString(codeVerifier);
}

public String generateCodeChallange(String codeVerifier) throws Exception {
    byte[] bytes = codeVerifier.getBytes("US-ASCII");
    MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
    messageDigest.update(bytes, 0, bytes.length);
    byte[] digest = messageDigest.digest();
    return Base64.getUrlEncoder().withoutPadding().encodeToString(digest);
}

var codeVerifier = generateCodeVerifier();
var codeChallenge = generateCodeChallange(codeVerifier);
```
