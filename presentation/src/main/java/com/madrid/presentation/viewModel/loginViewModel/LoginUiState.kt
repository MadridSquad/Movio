import com.madrid.domain.exceptions.MovioException

data class LoginUiState(
    val username: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val errorState: MovioException? = null, // ← Replace LoginError with MovioException
    val showPassword: Boolean = false,
    val isTwoFactorRequired: Boolean = false,
    val loginSuccess: Boolean = false,
    val isGuest: Boolean = false
) {
    val canLogin: Boolean
        get() = username.isNotBlank() && password.isNotBlank() && !isLoading
}
