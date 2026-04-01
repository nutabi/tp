package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class NameContainsKeywordsPredicate implements Predicate<Person> {

    private static final int MIN_EDIT_RATIO = 1;
    private static final double EDIT_DISTANCE_RATIO = 0.2;

    private final List<String> keywords;

    /**
     * Constructs a {@code NameContainsKeywordsPredicate} using a list of name keywords.
     *
     * @param keywords The list of name keywords.
     */
    public NameContainsKeywordsPredicate(List<String> keywords) {
        requireAllNonNull(keywords);

        this.keywords = List.copyOf(keywords);
    }

    @Override
    public boolean test(Person person) {
        // Split name based on white spaces
        String[] nameTokens = person.getName().fullName
                .replaceAll("[^a-zA-Z0-9\\s]", "") // Remove punctuation
                .toLowerCase()
                .split("\\s+");

        return keywords.stream()
                .map(String::toLowerCase)
                .anyMatch(keyword -> Arrays.stream(nameTokens)
                        .anyMatch(token -> {
                            if (token.contains(keyword)) {
                                return true;
                            }

                            int threshold = Math.max(MIN_EDIT_RATIO, (int) (keyword.length() * EDIT_DISTANCE_RATIO));
                            return StringUtil.matchesFuzzy(token, keyword, threshold);
                        }));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NameContainsKeywordsPredicate)) {
            return false;
        }

        NameContainsKeywordsPredicate otherNameContainsKeywordsPredicate = (NameContainsKeywordsPredicate) other;
        return keywords.equals(otherNameContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
