package ca.mcgill.purposeful.configuration;

/** Enum class that defines the authorities that can be assigned to a used*/
public enum Authority {
  /** Owner Authority, can do everything */
  Owner,
  /** Moderator Authority */
  Moderator,
  /** User Authority */
  User
}
// hasRole('member') == hasAuthority('ROLE_member')
