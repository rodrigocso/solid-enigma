package enigma.user.domain.entity;

import com.google.common.collect.Sets;
import enigma.user.domain.service.Sha512CryptService;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserTests {
    @Test
    void newlyCreatedUserShouldHaveNoScopes() {
        User user = new User.Builder("user_id").build();
        var scopes = user.getGroupedScopes();

        assertThat(scopes).containsOnlyKeys((Role) null);
        assertThat(scopes.get(null)).isEmpty();
    }

    @Test
    void canVerifyPassword() {
        User user = new User.Builder("user_id")
                .password("secret")
                .build();

        assertThat(user.verifyPassword("secret")).isTrue();
    }

    @Test
    void canAddScope() {
        User user = new User.Builder("user_id").build();
        user.addScope("app:test");

        assertThat(user.hasScope("app:test")).isTrue();
    }

    @Test
    void canRemoveScope() {
        User user = new User.Builder("user_id")
                .scopes(Sets.newHashSet("app:test"))
                .build();

        assertThat(user.hasScope("app:test")).isTrue();
        user.removeScope("app:test");

        assertThat(user.hasScope("app:test")).isFalse();
    }
}