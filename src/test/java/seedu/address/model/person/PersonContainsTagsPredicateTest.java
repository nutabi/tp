package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.util.List;

import org.junit.jupiter.api.Test;

public class PersonContainsTagsPredicateTest {

    @Test
    public void equals() {
        PersonContainsTagsPredicate firstPredicate =
                new PersonContainsTagsPredicate(List.of("friends"));
        PersonContainsTagsPredicate secondPredicate =
                new PersonContainsTagsPredicate(List.of("colleagues"));

        // same object -> true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> true
        PersonContainsTagsPredicate firstPredicateCopy =
                new PersonContainsTagsPredicate(List.of("friends"));
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> false
        assertFalse(firstPredicate.equals(1));

        // null -> false
        assertFalse(firstPredicate.equals(null));

        // different tags -> false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_singleMatchingTag_returnsTrue() {
        PersonContainsTagsPredicate predicate =
                new PersonContainsTagsPredicate(List.of("friends"));

        // Alice has friends tag
        assertTrue(predicate.test(ALICE));
    }

    @Test
    public void test_multipleTagsPersonMatchesOne_returnsTrue() {
        PersonContainsTagsPredicate predicate =
                new PersonContainsTagsPredicate(List.of("friends", "colleagues"));

        // Alice has friends tag but not colleagues tag, while Benson has friends tag but not colleagues tag.
        assertTrue(predicate.test(ALICE));
        assertTrue(predicate.test(BENSON));
    }

    @Test
    public void test_noMatchingTags_returnsFalse() {
        PersonContainsTagsPredicate predicate =
                new PersonContainsTagsPredicate(List.of("unknown"));

        // Alice does not have the "unknown" tag.
        assertFalse(predicate.test(ALICE));
    }

    @Test
    public void test_caseInsensitiveMatching_returnsTrue() {
        PersonContainsTagsPredicate predicate =
                new PersonContainsTagsPredicate(List.of("FRIENDS"));

        // Boundary value
        // Input tags are normalized to lowercase, so matching is case-insensitive.
        assertTrue(predicate.test(ALICE));
    }

    @Test
    public void test_emptyTagSet_returnsFalse() {
        PersonContainsTagsPredicate predicate =
                new PersonContainsTagsPredicate(List.of());

        // anyMatch over an empty keyword list always returns false.
        assertFalse(predicate.test(ALICE));
    }

    @Test
    public void toStringMethod() {
        PersonContainsTagsPredicate predicate =
                new PersonContainsTagsPredicate(List.of("friends"));

        String expected = PersonContainsTagsPredicate.class.getCanonicalName()
                + "{tags=" + predicate.toString().split("tags=")[1];

        assertTrue(predicate.toString().contains("friends"));
    }
}
