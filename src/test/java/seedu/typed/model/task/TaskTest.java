package seedu.typed.model.task;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.typed.commons.exceptions.IllegalValueException;
import seedu.typed.model.tag.Tag;
import seedu.typed.model.tag.UniqueTagList;
/**
 * Unit testing for Task class 98.3%
 * @author YIM CHIA HUI
 *
 */
public class TaskTest {
    private Name nullName;
    private Name name;
    private Name name2;
    private Notes notes;
    private Tag tag;
    private Tag tag2;
    private Date nullDate;
    private Date date;
    private Date from;
    private Date to;
    private Task test;
    private UniqueTagList tagList;
    private UniqueTagList tagList2;

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    @Before
    public void setUp() {
        try {
            nullName = null;
            nullDate = null;
            tag = new Tag("work");
            tag2 = new Tag("friends");
            name = new Name("Meet John");
            name2 = new Name("Meet Honey");
            notes = new Notes("");
            date = new Date("12/12/2017");
            from = new Date("");
            to = new Date("");
            test = new Task(name, notes, date, from, to, new UniqueTagList());
            tagList = new UniqueTagList();
            tagList2 = new UniqueTagList();
            tagList2.add(tag2);
            tagList.add(tag);
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void equals_same_success() throws IllegalValueException {
        Task test = new TaskBuilder().setName(name).setDate("").setFrom("").setTo("").setNotes("").build();
        assertTrue(test.equals(test));
    }
    @Test
    public void equals_notSameButSimilar_success() throws IllegalValueException {
        Task test2 = new TaskBuilder().setName(name).setTags(new UniqueTagList())
                .setDate(date).setFrom("").setTo("").setNotes("").build();
        assertTrue(test.equals(test2));
    }
    @Test
    public void equals_notSameAndNotSameInstance_false() throws IllegalValueException {
        Task test = new TaskBuilder().setName(name).build();
        assertFalse(test.equals(name));
    }
    @Test
    public void task_nameNull_assertError() throws IllegalValueException {
        thrown.expect(AssertionError.class);
        Task test = new Task(nullName, notes, date, from, to, new UniqueTagList());
        test.setName(name);
    }
    @Test
    public void task_task_success() {
        Task test2 = new Task(test);
        assertTrue(test.equals(test2));
    }
    @Test
    public void setName_valid_success() throws IllegalValueException {
        Task test = new TaskBuilder().setName(name).build();
        test.setName(name2);
        assertTrue(test.getName().equals(name2));
    }
    @Test
    public void setName_nameNull_assertError() throws IllegalValueException {
        thrown.expect(AssertionError.class);
        Task test = new TaskBuilder().setName(name).build();
        test.setName(nullName);
    }

    @Test
    public void setDate_dateNull_assertError() throws IllegalValueException {
        thrown.expect(AssertionError.class);
        Task test = new TaskBuilder().setName(name).build();
        test.setDate(nullDate);
    }
    @Test
    public void setDate_valid_success() throws IllegalValueException {
        Task test = new TaskBuilder().setName(name).build();
        test.setDate(date);
        assertTrue(test.getDate().equals(date));
    }
    @Test
    public void getTags_valid_success() throws IllegalValueException {
        Task test = new TaskBuilder().setName(name).setTags(tagList).build();
        assertTrue(test.getTags().equals(tagList));
    }
    @Test
    public void setTags_valid_success() throws IllegalValueException {
        Task test = new TaskBuilder().setName(name).build();
        test.setTags(tagList2);
        assertTrue(test.getTags().equals(tagList2));
    }
    @Test
    public void resetData_valid_success() throws IllegalValueException {
        Task test1 = new TaskBuilder().setName(name).build();
        test1.resetData(test);
        assertTrue(test1.equals(test));
    }
    @Test
    public void resetDate_null_assertError() throws IllegalValueException {
        thrown.expect(AssertionError.class);
        Task test = new TaskBuilder().setName(name).build();
        test.resetData(null);
    }
    @Test
    public void toString_valid_success() {
        assertEquals(test.toString(), " Name: Meet John Notes:  Date: 12/12/2017 From:  To:  Completed: false Tags: ");
    }
}
