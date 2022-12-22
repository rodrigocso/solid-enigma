package enigma.application.dto;

import enigma.domain.entity.User;

import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record UserProfileResponse(String id, int version, String username, Map<String, List<String>> scopes) {
    public static UserProfileResponse from(User user) {
        return new UserProfileResponse(
                user.getId(),
                user.getVersion(),
                user.getUsername(),
                user.getGroupedScopes().entrySet().stream()
                        .map(entry -> new SimpleEntry<>(
                                (entry.getKey() != null) ? entry.getKey().getName() : "",
                                entry.getValue()))
                        .collect(Collectors.toMap(SimpleEntry::getKey, SimpleEntry::getValue)));
    }
}
