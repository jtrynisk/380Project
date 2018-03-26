package App;

import java.util.ArrayList;
import java.util.Scanner;
public class UserLogin {
	//Ask if first time user -> Y = call a function inorder to create the new user || N = call a function to check the...
	//users creds --> this would be used at first in terminal
	//Parse the XML file that the users are stored in
	private Scanner sc;
	private ArrayList<User> uList;
	private boolean userAccess;
	
	public UserLogin() {
		//sc = new Scanner(System.in);
		userAccess = false;
		uList = new ArrayList<>();
	}
	
	public void callToArms() {
		System.out.println("New User? Or press enter to exit.");
		sc = new Scanner(System.in);
		String userStatus = sc.nextLine();
		if(userStatus.equalsIgnoreCase("No") || userStatus.equalsIgnoreCase("N")) {
			login(sc);
		} else if(userStatus.equalsIgnoreCase("Yes") || userStatus.equalsIgnoreCase("Y")) {
			newUserSetup(sc);
			login(sc);
		} else if(userStatus.equals("")) {
			return;
		} else {
			System.out.println("Not a valid answer.");
			callToArms(sc);
		}
	}
	
	public void callToArms(Scanner sc) {
		System.out.println("New User? Or press enter to exit.");
		String userStatus = sc.nextLine();
		if(userStatus.equalsIgnoreCase("No") || userStatus.equalsIgnoreCase("N")) {
			login(sc);
		} else if(userStatus.equalsIgnoreCase("Yes") || userStatus.equalsIgnoreCase("Y")) {
			newUserSetup(sc);
			login(sc);
		} else if(userStatus.equals("")) {
			return;
		} else {
			callToArms(sc);
		}
	}
	
	public void newUserSetup(Scanner sc) {
		//Create the User object
		System.out.print("Enter a username. ");
		String createdUName = sc.nextLine();
		System.out.print("Enter a password. ");
		String firstEnteredPass = sc.nextLine();
		System.out.print("Enter the password again. ");
		if(firstEnteredPass.equals(sc.nextLine()))
			uList.add(new User(createdUName, firstEnteredPass));
		else {
			System.out.println("Passwords do not match try again!");
			newUserSetup(createdUName, sc);
		}
	}
	
	public void newUserSetup(String alreadyCreatedUName, Scanner sc) {
		//Create the User object if passwords don't match
		System.out.print("Enter a password. ");
		String firstEnteredPass = sc.nextLine();
		System.out.print("Enter the password again. ");
		if(firstEnteredPass.equals(sc.nextLine()))
			uList.add(new User(alreadyCreatedUName, firstEnteredPass));
		else {
			System.out.println("Passwords do not match try again!");
			newUserSetup(alreadyCreatedUName, sc);
		}
	}

	public void login(Scanner sc) {
		//Call the database the users are saved in
		//Compare to see if this is a valid user
		User loginUser;
		System.out.println("Enter your username, or press enter to exit. ");
		String potentialUserName = sc.nextLine();
		int tries = 0;
		while(!(potentialUserName.equals("")) && (userAccess == false)) {
			if(existsUserByName(potentialUserName) == true) {
				loginUser = findUserByName(potentialUserName);
				System.out.println("Please enter your password");
				String potentialPassword = sc.nextLine();
				if(loginUser.getPassword().equals(potentialPassword)) {
					userAccess = true;
				} else if(tries < 3) {
					System.out.println("Not the correct password. You have " + (3 - tries) + " tries remaining");
					tries++;
				} else {
					System.out.println("You have been locked out.");
					break;
				}
			} else if(!(potentialUserName.equals(""))) {
				System.out.println("This is not a valid username.");
				System.out.println("Enter your username, or press enter to exit. ");
				potentialUserName = sc.nextLine();
			} else {
				callToArms();
			}
		}
		if(potentialUserName.equals(""))
			callToArms(sc);
	}
	
	public boolean existsUserByName(String findIfExists) {
		for(User u:uList) {
			if(u.getUserName().equals(findIfExists))
				return true;
		}
		return false;
	}
	
	public User findUserByName(String userNameToFindUser) {
		for(User u:uList) {
			if(u.getUserName().equals(userNameToFindUser))
				return u;
		}
		return null;
	}
	
	public void addUser(User u) {
		uList.add(u);
	}
	
	public boolean getUserAccess() {
		return userAccess;
	}
	
	public Scanner getScanner() {
		return sc;
	}
	
	public void setUserList(ArrayList<User> ul) {
		this.uList = ul;
	}

	public void writeToXML() {
		UserXMLWriter uxmlw = new UserXMLWriter();
		uxmlw.write(this.uList);
	}
}