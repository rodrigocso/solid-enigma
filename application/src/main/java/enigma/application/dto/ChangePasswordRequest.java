package enigma.application.dto;

public record ChangePasswordRequest(String userId, int version, String currentPassword, String newPassword) {
}
