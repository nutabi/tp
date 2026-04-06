package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests whether a {@code Person}'s {@code Email} contains any of the given keywords.
 *
 * <p>Matching is case-insensitive and is performed against the person's full email address.
 * A person is considered a match if at least one keyword is a substring of the email.</p>
 */
public class EmailContainsKeywordsPredicate implements Predicate<Person> {

    private final List<String> keywords;

    /**
     * Creates a {@code EmailContainsKeywordsPredicate} using a list of email keywords.
     *
     * @param keywords The list of email keywords
     */
    public EmailContainsKeywordsPredicate(List<String> keywords) {
        requireAllNonNull(keywords);

        this.keywords = List.copyOf(keywords);
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(keyword -> person.getEmail()
                        .containsIgnoreCase(keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EmailContainsKeywordsPredicate otherEmailContainsKeywordsPredicate)) {
            return false;
        }

        return keywords.equals(otherEmailContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
