package randy;

import java.util.Scanner;

public class Inital {

  private String existance;
  private Scanner scanner = new Scanner(System.in);


  //RUNNING THE SELCTION PORTAL
  public void run () {
    greating();
    existanceVerfication();
    if (existance.equals("NEW")) {
      NewUserPortal new_user = new NewUserPortal();
      new_user.accountCreation();
    } else if (existance.equals("EXISTING")) {
      ExistingUserPortal existing_user = new ExistingUserPortal();
      existing_user.accountVerfication();
    }
  }

  //START UP MESSAGES
  private static void greating () {
    System.out.println( "*****************************************" );
    System.out.println( "* Welcome To Central Texas Credit Union *" );
    System.out.println( "*****************************************" );
    System.out.println();
  }

  // Seeing if they are setting up a new account or using an existing one
  private void existanceVerfication () {
    do {
      System.out.println( "Are you a New or Existing User?:" );
      System.out.println( "New | Existing" );
      existance = scanner.nextLine();
      existance = existance.toUpperCase();
      System.out.println();
    } while (!existance.equals("NEW") && !existance.equals("EXISTING"));
  }
}
