package randy;

import java.util.Scanner;

public class NewUserPortal {

  private String username;
  private String password;
  private Scanner scanner = new Scanner(System.in);
  private Database database = new Database();



  public void accountCreation () {
    newUsernameCreation();
    newPasswordCreation();
    addUserToDatabase();
    newUserAccountMessager();
  }

  //USERNAME SECTION
  private boolean newUsernameCreation () {
    do {
      System.out.println( "Enter a New Username (case sensitive): " );
      username = scanner.nextLine();
      System.out.println();
    } while (username.length() > 1 && usernameAvailable(username));
    return true;
  }

  private boolean usernameAvailable (String username) {
    if (!database.usernameExists(this.username)) {
      System.out.println("That Username is Avaible!");
      System.out.println();
    } else if (database.usernameExists(this.username)) {
      System.out.println("Someone Else has the Username, Choose Another");
      System.out.println();
    }
    return database.usernameExists(this.username);
  }

  //PASSWORD SECTION
  private boolean newPasswordCreation () {
    String password_first_check;
    do {
      System.out.println( "Enter a Safe Password (case sensitive, 8 Chars Miniumum): " );
      password_first_check = scanner.nextLine();
    } while (password_first_check.length() < 8 || !newPasswordVerfication(password_first_check));
    password = password_first_check;
    return true;
  }

  private boolean newPasswordVerfication(String password_first_check) {
    String password_second_check;
    do {
      System.out.println();
      System.out.println( "Re-Enter the Password (case sensitive, 8 Chars Miniumum): " );
      password_second_check = scanner.nextLine();
    } while (password_second_check.length() < 8 || !password_second_check.equals(password_first_check));
    System.out.println();
    return true;
  }

  //DATABASE SECTION
  private void addUserToDatabase () {
    UserAccount adding_account = new UserAccount(username, password, true, false, false);
    //TODO: Need to add a logger that will log that a user account will be made.
    database.createNewUser(adding_account); 
  }

  //FINAL MESSAGES
  private void newUserAccountMessager() {
    System.out.println( "Account Created !" );
    System.out.println( "Username: " + username );
    System.out.println();
  }

}
