package seedu.address.model.person;

/**
 * Represents the type of duplicate conflict detected for a person.
 */
public enum DuplicateConflict {
    NONE,
    EMAIL,
    TELEGRAM_HANDLE,
    EMAIL_AND_TELEGRAM_HANDLE;

    public boolean hasEmailConflict() {
        return this == EMAIL || this == EMAIL_AND_TELEGRAM_HANDLE;
    }

    public boolean hasTelegramHandleConflict() {
        return this == TELEGRAM_HANDLE || this == EMAIL_AND_TELEGRAM_HANDLE;
    }
}
