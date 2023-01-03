package enigma.user.domain.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Role extends BaseEntity {
    private String name;
    private String description;
    private Set<String> scopes;

    private Role(Builder builder) {
        super(builder.id);
        name = builder.name;
        description = builder.description;
        scopes = (builder.scopes != null) ? builder.scopes : new HashSet<>();
    }

    public static class Builder {
        private final String id;
        private String name;
        private String description;
        private Set<String> scopes;

        public Builder(String id) {
            this.id = id;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder scopes(Set<String> scopes) {
            this.scopes = scopes;
            return this;
        }

        public Role build() {
            return new Role(this);
        }
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getScopes() {
        return scopes.stream().toList();
    }

    public boolean hasScope(String scope) {
        return scopes.contains(scope);
    }
}
