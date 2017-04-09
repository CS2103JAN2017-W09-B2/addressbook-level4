package seedu.typed.model.task;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.Month;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.typed.commons.exceptions.IllegalValueException;
import seedu.typed.model.tag.Tag;
import seedu.typed.model.tag.UniqueTagList;
//@@author A0139379M
/**
 * Unit testing for Task class 98.3%
 * @author YIM CHIA HUI
 *
 */
public class TaskTest {
    private Name nullName;
    private Name name;
    private Name name2;
    private Tag tag;
    private Tag tag2;
    private DateTime date;
    //@@author A0141094M
    private Notes notes;
    private DateTime from;
    private DateTime to;
    //@@author
    private Task test;
    private UniqueTagList tagList;
    private UniqueTagList tagList2;

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    @Before
    public void setUp() {
        try {
            nullName = null;
            tag = new Tag("work");
            tag2 = new Tag("friends");
            name = new Name("Meet John");
            name2 = new Name("Meet Honey");
            date = DateTime.getDateTime(2017, Month.APRIL, 1, 0, 0);
            //@@author A0141094M
            notes = new Notes();
            from = DateTime.getDateTime(2017, Month.APRIL, 4, 0, 0);
            to = DateTime.getDateTime(2017, Month.APRIL, 7, 0, 0);
            //@@author
            test = new Task(name, notes, date, null, null, new UniqueTagList(), false);
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
        Task test = new TaskBuilder().setName(name).setDeadline(date).setNotes("").build();
        assertTrue(test.equals(test));
    }
    @Test
    public void equals_notSameButSimilar_success() throws IllegalValueException {
        Task test2 = new TaskBuilder().setName(name).setTags(new UniqueTagList())
                .setDeadline(date).setNotes("").build();
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
        Task test = new Task(nullName, notes, date, from, to, new UniqueTagList(), false);
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
        Task test = new TaskBuilder().setName(nullName).build();
        test.setName(name);
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
    //@@author A0141094M
    @Test
    public void toString_valid_success() {
        assertEquals(test.toString(), " Name: Meet John Notes:  "
                + "By: Saturday, Apr 01, 2017 00:00 "
                + "Completed: false Tags: ");
    }
    //@@author
}
