package enigma.user.application.dto.query;

import enigma.common.mediator.Request;
import enigma.user.application.dto.response.GetUsersResponse;

public record GetUsersQuery() implements Request<GetUsersResponse> {
}
