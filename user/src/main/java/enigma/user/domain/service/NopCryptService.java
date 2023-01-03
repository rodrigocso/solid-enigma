package enigma.user.domain.service;

public class NopCryptService implements CryptService {
    public static final NopCryptService INSTANCE = new NopCryptService();

    private NopCryptService() {}

    @Override
    public String crypt(String key, String salt) {
        return key;
    }

    @Override
    public String randomSalt() {
        return null;
    }
}
