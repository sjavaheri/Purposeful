package ca.mcgill.purposeful.configuration;

/**
 * Enum class that defines the authorities that can be assigned to a user
 */
public enum Authority {
  Owner,
  Moderator,
  User
}
// hasRole('member') == hasAuthority('ROLE_member')
