package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class NameContainsKeywordsPredicate implements Predicate<Person> {

    private static final int MIN_ALLOWED_EDITS = 1;
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
        String[] nameTokens = StringUtil.normalize(person.getName().fullName)
                .split("\\s+");

        return keywords.stream()
                .map(StringUtil::normalize)
                .anyMatch(keyword -> matchesAnyToken(keyword, nameTokens));
    }

    private boolean matchesAnyToken(String keyword, String[] tokens) {
        for (String token : tokens) {
            if (token.contains(keyword)) {
                return true;
            }

            if (isFuzzyMatch(token, keyword)) {
                return true;
            }
        }

        return false;
    }

    private boolean isFuzzyMatch(String token, String keyword) {
        int threshold = Math.max(MIN_ALLOWED_EDITS,
                (int) (keyword.length() * EDIT_DISTANCE_RATIO));

        return StringUtil.matchesFuzzy(token, keyword, threshold);
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
