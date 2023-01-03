package enigma.user.domain.service;

import org.apache.commons.codec.digest.Crypt;

import java.security.SecureRandom;

public class Sha512CryptService implements CryptService {
    public static final Sha512CryptService INSTANCE = new Sha512CryptService();
    private static final String SALT_CHARSET =
            "./0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private Sha512CryptService() {}

    @Override
    public String crypt(String key, String salt) {
        return Crypt.crypt(key, salt);
    }

    /**
     * The salt string is drawn from the set [a-zA-Z0-9./].
     * SHA-512 salts start with $6$ and are up to 16 chars long.
     * More info in <a href="https://commons.apache.org/proper/commons-codec/apidocs/org/apache/commons/codec/digest/Crypt.html#crypt-java.lang.String-java.lang.String-">Crypt (Apache Commons Codec)</a>
     */
    @Override
    public String randomSalt() {
        var random =  new SecureRandom();
        var salt = new byte[16];
        random.nextBytes(salt);

        var sb = new StringBuilder("$6$");

        for (int i = 0; i < 16; i++) {
            sb.append(SALT_CHARSET.charAt((salt[i] + 128) % SALT_CHARSET.length()));
        }

        return sb.toString();
    }
}
