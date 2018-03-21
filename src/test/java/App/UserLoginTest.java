package App;

import static org.junit.Assert.*;
import java.util.Scanner;
import org.junit.Test;
import App.*;

public class UserLoginTest {
	UserLogin ul;
	
	@Test
	public void testExistsUserByName() {
		ul = new UserLogin();
		ul.addUser(new User("Taddy Mason", "password"));
		assertTrue(ul.existsUserByName("Taddy Mason"));
	}
	
	@Test
	public void testLogin() {
		ul = new UserLogin();
		ul.addUser(new User("Taddy Mason", "password"));
		ul.login("Taddy Mason", "password");
		assertTrue(ul.getUserAccess());
	}

}
