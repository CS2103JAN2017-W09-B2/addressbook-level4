package seedu.typed.model.task;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import seedu.typed.commons.exceptions.IllegalValueException;
import seedu.typed.model.tag.Tag;
import seedu.typed.model.tag.UniqueTagList;
import seedu.typed.model.tag.UniqueTagList.DuplicateTagException;
/*
 * Unit testing for TaskBuilder 100%
 * @author YIM CHIA HUI
 */
public class TaskBuilderTest {
    private TaskBuilder testBuilder1 = new TaskBuilder();
    private Name name;
    private Date date;
    private Date from;
    private UniqueTagList tags, tagTest;
    private Set<String> tagSet;
    private Task testTask;
    @Before
    public void setUp() {
        try {
            name = new Name("Meet John");
            date = new Date("12/12/2017");
            from = new Date("");
            tags = new UniqueTagList();
            tagTest = new UniqueTagList();
            tags.add(new Tag("friends"));
            testTask = new Task(name, date, from, tags);
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
    public void setName_validName_success() {
        assertTrue(testBuilder1.setName(name).build().getName().equals(name));
        testBuilder1 = new TaskBuilder();
    }
    @Test
    public void setName_validString_success() {
        try {
            assertTrue(testBuilder1.setName("Meet John").build().getName().equals(name));
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void setDate_validDate_success() {
        assertTrue(testBuilder1.setDate(date).setName(name)
                .build().getDate().equals(date));
    }
    @Test
    public void setDate_validString_success() {
        try {
            assertTrue(testBuilder1.setDate("12/12/2017").setName(name)
                    .build().getDate().equals(date));
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void addTags_validTag_success() {
        try {
            assertTrue(testBuilder1.setName(name)
                    .addTags("friends").build().getTags().equals(tags));
            testBuilder1 = new TaskBuilder();
        } catch (DuplicateTagException e) {
            e.printStackTrace();
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void addTags_duplicateTag_duplicateTagException() {
        try {
            testBuilder1.setName(name).addTags("friends").addTags("friends").build();
            fail();
        } catch (DuplicateTagException e) {
            assertEquals(e.getMessage(), "Operation would result in duplicate tags");
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void taskBuilder_Task_Success() throws IllegalValueException {
        TaskBuilder builder = new TaskBuilder(testTask);
        assertTrue(builder.build().equals(testTask));
    }
    @Test
    public void addTags_setOfTags_success() {
        try {
            UniqueTagList tags = testBuilder1.setName(name).setTags(tagSet).build().getTags();
            for (Tag tag:tags) {
                assertTrue(tagTest.contains(tag));
            }
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void setTags_UniqueTagList_success() {
        assertTrue(testBuilder1.setName(name).setTags(tags).build().getTags().equals(tags));
    }
}
