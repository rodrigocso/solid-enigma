package enigma.application.dto;

public record ChangeUsernameRequest(String userId, int version, String newUsername) {
}
