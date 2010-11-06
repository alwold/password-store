
import com.alwold.password.PasswordSafePasswordStore;
import com.alwold.password.PasswordStore;
import com.alwold.password.PasswordStoreException;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author alwold
 */
public class TestPasswordSafePasswordStore {
	@Test
	public void testPasswordSafe() throws PasswordStoreException {
		PasswordStore store = new PasswordSafePasswordStore(TestPasswordSafePasswordStore.class.getResource("/test.psafe3").getPath());
		Assert.assertEquals("abc123", store.getPassword("testAccount", "testService"));
	}
}
