package enigma.user.domain.service;

public interface CryptService {
    String crypt(String key, String salt);
    String randomSalt();
}
