package seedu.address.commons.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ConfigTest {
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void toString_defaultObject_stringReturned() {
    String defaultConfigAsString = "App title : Typical App Title\n" + "Current log level : INFO\n"
        + "Preference file Location : preferences.json\n" + "Local data file location : taskmanager.xml\n"
        + "TaskManager name : TypicalTaskManagerName";

    assertEquals(defaultConfigAsString, new Config().toString());
  }

  @Test
  public void equalsMethod() {
    Config defaultConfig = new Config();
    assertNotNull(defaultConfig);
    assertTrue(defaultConfig.equals(defaultConfig));
  }

}
