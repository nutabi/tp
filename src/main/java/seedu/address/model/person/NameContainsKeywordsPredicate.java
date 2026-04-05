package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Predicate that tests if a {@code Person}'s {@code Name} matches any given keywords.
 *
 * <p>Matching is case-insensitive and performed on normalized strings.
 * Supports:</p>
 * <ul>
 *   <li>Exact substring matching (keyword contained in a name token)</li>
 *   <li>Fuzzy matching within a Damerau–Levenshtein distance threshold to handle typos</li>
 * </ul>
 *
 * <p>The fuzzy threshold is computed as:
 * {@code max(MIN_ALLOWED_EDITS, keyword.length() * EDIT_DISTANCE_RATIO)}.</p>
 *
 * @see StringUtil#normalize(String)
 * @see StringUtil#isWithinEditDistance(String, String, int)
 */
public class NameContainsKeywordsPredicate implements Predicate<Person> {

    // Minimum number of edits allowed in fuzzy matching (prevents zero edits for very short keywords)
    private static final int MIN_ALLOWED_EDITS = 1;
    // Ratio of keyword length to determine allowed edits in fuzzy matching (e.g., 20% of the keyword length)
    private static final double EDIT_DISTANCE_RATIO = 0.2;

    private final List<String> normalizedKeywords;

    /**
     * Constructs a {@code NameContainsKeywordsPredicate} using a list of name keywords.
     *
     * <p> Keywords will be normalized to lowercase and any special characters are removed.</p>
     *
     * @param keywords The list of name keywords to match against (cannot be null or contain null elements)
     * @throws NullPointerException if {@code keywords} is null or contains null elements
     */
    public NameContainsKeywordsPredicate(List<String> keywords) {
        requireAllNonNull(keywords);

        this.normalizedKeywords = keywords.stream()
                .map(StringUtil::normalize)
                .flatMap(s -> Arrays.stream(s.split("\\s+")))
                .filter(s -> !s.isEmpty())
                .toList();
    }

    @Override
    public boolean test(Person person) {
        // Split name based on white spaces
        List<String> nameTokens = Arrays.asList(StringUtil.normalize(person.getName().fullName)
                        .split("\\s+"));

        return normalizedKeywords.stream()
                .anyMatch(keyword -> matchesAnyToken(nameTokens, keyword));
    }

    private boolean matchesAnyToken(List<String> nameTokens, String keyword) {
        return nameTokens.stream()
                .anyMatch(token -> token.contains(keyword)
                        || isFuzzyMatch(token, keyword));
    }

    /**
     * Checks whether the given {@code token} approximately matches the {@code keyword}
     * using the Damerau-Levenshtein algorithm.
     *
     * @param token the string to test
     * @param keyword the target keyword
     * @return {@code true} if {@code token} matches {@code keyword} within the allowed edit distance
     */
    private boolean isFuzzyMatch(String token, String keyword) {
        int threshold = computeThreshold(keyword);

        // Optimisation to avoid expensive distance calculation if lengths differ too much
        if (Math.abs(token.length() - keyword.length()) > threshold) {
            return false;
        }

        return StringUtil.isWithinEditDistance(keyword, token, threshold);
    }

    /**
     * Computes the maximum allowed number of edits for a given keyword.
     *
     * <p>The allowed number of edits is calculated as the maximum of:
     * <ul>
     *     <li>{@code MIN_ALLOWED_EDITS} (minimum allowed edits)</li>
     *     <li>{@code ceil(keyword.length() * EDIT_DISTANCE_RATIO)}</li>
     * </ul>
     * This ensures that longer keywords can tolerate more mismatches proportionally,
     * while still enforcing a minimum edit allowance.
     *
     * @param keyword the keyword for which to compute the allowed edits
     * @return the maximum allowed number of edits for this keyword
     */
    private int computeThreshold(String keyword) {
        return Math.max(
                MIN_ALLOWED_EDITS,
                (int) Math.ceil(keyword.length() * EDIT_DISTANCE_RATIO));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NameContainsKeywordsPredicate otherNameContainsKeywordsPredicate)) {
            return false;
        }

        return normalizedKeywords.equals(otherNameContainsKeywordsPredicate.normalizedKeywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("keywords", normalizedKeywords)
                .toString();
    }
}
