package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class NameContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        NameContainsKeywordsPredicate firstPredicate = new NameContainsKeywordsPredicate(firstPredicateKeywordList);
        NameContainsKeywordsPredicate secondPredicate = new NameContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        NameContainsKeywordsPredicate firstPredicateCopy = new NameContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different predicate -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));

        // empty and whitespace keywords -> returns true
        List<String> thirdPredicateKeywordList = Arrays.asList("first", " ", "second", "");
        assertTrue(secondPredicate.equals(new NameContainsKeywordsPredicate(thirdPredicateKeywordList)));
    }

    //============================== SUCCESS CASES ===================================
    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Only one matching keyword
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Carol").build()));

        // Substring match
        predicate = new NameContainsKeywordsPredicate(Collections.singletonList("Ali"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Partial match across words should still work
        predicate = new NameContainsKeywordsPredicate(List.of("Alice", "B"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Substring match once keyword is normalized and split
        predicate = new NameContainsKeywordsPredicate(Collections.singletonList("Alice-Bob"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Johnson").build()));

        // Mixed-case keywords
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Test fuzzy match success
        // 1 transposition away from Alice (within threshold of 1)
        predicate = new NameContainsKeywordsPredicate(List.of("Aliec"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").build()));
    }

    //============================== FAILURE CASES ===================================
    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Substring not present
        predicate = new NameContainsKeywordsPredicate(Collections.singletonList("xyz"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Keywords match phone, email and address, but does not match name
        predicate = new NameContainsKeywordsPredicate(
                Arrays.asList("12345", "alicetan123@email.com", "Main", "Street"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alicetan123@email.com").build()));

        // Test fuzzy match failure (outside threshold)
        predicate = new NameContainsKeywordsPredicate(List.of("AliceXXXX")); // 4 edits away from "Alice"
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("Keyword1", "Keyword2");
        List<String> normalizedKeywords = List.of("keyword1", "keyword2");
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(keywords);

        String expected = NameContainsKeywordsPredicate.class.getCanonicalName()
                + "{keywords=" + normalizedKeywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
