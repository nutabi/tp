package seedu.address.model.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagType;

public class SampleDataUtilTest {

    @Test
    public void getSamplePersons_notEmpty() {
        Person[] persons = SampleDataUtil.getSamplePersons();
        assertTrue(persons.length > 0);
    }

    @Test
    public void getSampleAddressBook_success() {
        ReadOnlyAddressBook addressBook = SampleDataUtil.getSampleAddressBook();
        AddressBook expected = new AddressBook();

        for (Person person : SampleDataUtil.getSamplePersons()) {
            expected.addPerson(person);
        }

        assertEquals(expected, addressBook);
    }

    @Test
    public void getGeneralTagSet_validTags_success() {
        Set<Tag> tags = SampleDataUtil.getGeneralTagSet("friends", "colleagues");

        assertTrue(tags.contains(new Tag("friends", TagType.GENERAL)));
        assertTrue(tags.contains(new Tag("colleagues", TagType.GENERAL)));
    }

    @Test
    public void getRoleTagSet_validTags_success() {
        Set<Tag> tags = SampleDataUtil.getRoleTagSet("tutor", "student");

        assertTrue(tags.contains(new Tag("tutor", TagType.ROLE)));
        assertTrue(tags.contains(new Tag("student", TagType.ROLE)));
    }

    @Test
    public void getCourseTagSet_validTags_success() {
        Set<Tag> tags = SampleDataUtil.getCourseTagSet("CS2103", "CS2040");

        assertTrue(tags.contains(new Tag("CS2103", TagType.COURSE)));
        assertTrue(tags.contains(new Tag("CS2040", TagType.COURSE)));
    }
}
