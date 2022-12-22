package enigma.domain.entity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class User extends BaseEntity {
    private String username;
    private String password;
    private final Set<Role> roles;
    private final Set<String> scopes;

    private User(Builder builder) {
        super(builder.id);
        username = builder.username;
        password = builder.password;
        roles = (builder.roles != null) ? builder.roles : new HashSet<>();
        scopes = (builder.scopes != null) ? builder.scopes : new HashSet<>();
    }

    public static class Builder {
        private final String id;
        private String username;
        private String password;
        private Set<Role> roles;
        private Set<String> scopes;

        public Builder(String id) {
            this.id = id;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder roles(Set<Role> roles) {
            this.roles = roles;
            return this;
        }

        public Builder scopes(Set<String> scopes) {
            this.scopes = scopes;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean verifyPassword(String password) {
        return password.equals(this.password);
    }

    public boolean addScope(String scope) {
        return scopes.add(scope);
    }

    public boolean hasScope(String scope) {
        return scopes.contains(scope) || roles.stream().anyMatch(r -> r.hasScope(scope));
    }

    public boolean removeScope(String scope) {
        return scopes.remove(scope);
    }

    public HashMap<Role, List<String>> getGroupedScopes() {
        var groupedScopes = new HashMap<Role, List<String>>();

        groupedScopes.put(null, scopes.stream().toList());
        roles.forEach(r -> groupedScopes.put(r, r.getScopes()));

        return groupedScopes;
    }
}
