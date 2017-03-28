package seedu.typed.model.task;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.time.Month;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import seedu.typed.commons.exceptions.IllegalValueException;
import seedu.typed.model.tag.Tag;
import seedu.typed.model.tag.UniqueTagList;
import seedu.typed.model.tag.UniqueTagList.DuplicateTagException;
//@@author A0139379M
/*
 * Unit testing for EventBuilder 100%
 * @author YIM CHIA HUI
 */
public class EventBuilderTest {
    EventBuilder testBuilder1 = new EventBuilder();
    Name name1;
    Name name2;
    DateTime aprilFoolDay;
    DateTime christmasDay;
    DateTime dayAfterAprilFoolDay;
    DateTime dayAfterChristmasDay;
    String details1;
    String details2;
    UniqueTagList tags, tagTest;
    Set<String> tagSet;
    Event testEvent1;
    Event testEvent2;
    @Before
    public void setUp() {
        try {
            name1 = new Name("Meet John");
            name2 = new Name("Meet May");
            aprilFoolDay = DateTime.getDateTime(2017, Month.APRIL, 1, 0, 0);
            christmasDay = DateTime.getDateTime(2017, Month.DECEMBER, 25, 0, 0);
            dayAfterAprilFoolDay = DateTime.getDateTime(2017, Month.APRIL, 2, 0, 0);
            dayAfterChristmasDay = DateTime.getDateTime(2017, Month.DECEMBER, 26, 0, 0);
            details1 = "Have a cup of coffee!";
            details2 = "Buy present for May!";
            tags = new UniqueTagList();
            tagTest = new UniqueTagList();
            tags.add(new Tag("friends"));
            testEvent1 = new Event(name1, aprilFoolDay, dayAfterAprilFoolDay, false, details1, tags);
            testEvent2 = new Event(name2, christmasDay, dayAfterChristmasDay, true, details2, tags);
            tagSet = new HashSet<String>();
            tagSet.add("friends");
            tagSet.add("work");
            tagTest.add(new Tag("friends"));
            tagTest.add(new Tag("work"));
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void setDetails_validDetails_success() {
        assertTrue(testBuilder1
                .setName(name1).setDetails(details1)
                .build().getDetails().equals(details1));
    }
    @Test
    public void setName_validName_success() {
        assertTrue(testBuilder1.setName(name1).build().getName().equals(name1));
        testBuilder1 = new EventBuilder();
    }
    @Test
    public void setName_validString_success() {
        try {
            assertTrue(testBuilder1.setName("Meet John").build().getName().equals(name1));
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }
        testBuilder1 = new EventBuilder();
    }
    @Test
    public void startDate_validDate_success() {
        assertTrue(testBuilder1
                .setName(name1)
                .startDate(aprilFoolDay)
                .build().getStartDate().equals(aprilFoolDay));
        testBuilder1 = new EventBuilder();
    }
    @Test
    public void endDate_validDate_success() {
        assertTrue(testBuilder1
                .setName(name1)
                .endDate(aprilFoolDay)
                .build().getEndDate().equals(aprilFoolDay));
        testBuilder1 = new EventBuilder();
    }
    @Test
    public void date_validDates_success() {
        Event testEvent = testBuilder1.setName(name1)
                .date(aprilFoolDay, dayAfterAprilFoolDay)
                .build();
        assertTrue(testEvent.getEndDate().equals(dayAfterAprilFoolDay));
        assertTrue(testEvent.getStartDate().equals(aprilFoolDay));
    }
    @Test
    public void addTags_validTag_success() {
        try {
            assertTrue(testBuilder1.setName(name1)
                    .addTags("friends").build().getTags().equals(tags));
            testBuilder1 = new EventBuilder();
        } catch (DuplicateTagException e) {
            e.printStackTrace();
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void addTags_duplicateTag_duplicateTagException() {
        try {
            testBuilder1.setName(name1).addTags("friends").addTags("friends").build();
            fail();
        } catch (DuplicateTagException e) {
            assertEquals(e.getMessage(), "Operation would result in duplicate tags");
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }
        testBuilder1 = new EventBuilder();
    }
    @Test
    public void taskBuilder_Task_Success() {
        EventBuilder builder = new EventBuilder(testEvent1);
        assertTrue(builder.build().equals(testEvent1));
    }
    @Test
    public void addTags_setOfTags_success() {
        try {
            UniqueTagList tags = testBuilder1.setName(name1).setTags(tagSet).build().getTags();
            for (Tag tag:tags) {
                assertTrue(tagTest.contains(tag));
            }
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void setTags_UniqueTagList_success() {
        assertTrue(testBuilder1.setName(name1).setTags(tags).build().getTags().equals(tags));
        testBuilder1 = new EventBuilder();
    }
    @Test
    public void isRecurring_Recurring_success() {
        assertTrue(testBuilder1.setName(name1).isRecurring(true).build().isRecurring());
        testBuilder1 = new EventBuilder();
    }

}
//@@author A0139379M