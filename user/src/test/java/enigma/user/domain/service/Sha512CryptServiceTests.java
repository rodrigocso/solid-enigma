package enigma.user.domain.service;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Sha512CryptServiceTests {
    @Test
    void randomSha512Salt() {
        var sut = Sha512CryptService.INSTANCE;
        String salt = sut.randomSalt();

        assertThat(salt).startsWith("$6$");
        assertThat(salt.length()).isEqualTo(19);
    }

    @Test
    void sha512PasswordHashShouldHaveLengthOf106() {
        var sut = Sha512CryptService.INSTANCE;
        String salt = sut.randomSalt();
        String password = sut.crypt("secret-password", salt);

        assertThat(password.length()).isEqualTo(106);
    }
}
