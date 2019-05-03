package randy;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.junit.Test;
import org.junit.Before;
import org.junit.BeforeClass;

public class DatabaseTest {
  static Database testdb;
  
  @BeforeClass
  public static void setup() {
    testdb = new Database();
  }

  @Test
  public void usernameExistsTest1() {
    assertTrue(testdb.usernameExists("randy"));
  }
  
  @Test
  public void usernameExistsTest2() {
    assertTrue(testdb.usernameExists("molly"));
  }
  
  @Test
  public void usernameExistsTest3() {
    assertFalse(testdb.usernameExists("Randy"));
  }
  
  @Test
  public void usernameExistsTest4() {
    assertFalse(testdb.usernameExists("adsfasdfadsf"));
  }
}
