package enigma.user.application.dto.response;

import java.util.Collection;

public record GetUsersResponse(Collection<String> usernames) {
}
