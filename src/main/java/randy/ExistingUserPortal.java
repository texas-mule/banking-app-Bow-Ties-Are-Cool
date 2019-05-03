package randy;

import java.util.Scanner;

public class ExistingUserPortal {

  private String account_type;
  private String username;
  private String password;
  private Scanner scanner = new Scanner(System.in);
  private UserAccount user = null;


  public void accountVerfication () {
    accountLogin(); // Have to kill the program and restart if you want to user another account
    do {
      accountPortalCheck();
      accountPortalLogin();
      accountExit();
    } while(!account_type.equals("EXIT"));
  }

  private void accountExit () {
    do {
      System.out.println("Do You want to Exit or log into a different account type?");
      System.out.println("Exit | Continue");
      account_type = scanner.nextLine();
      account_type = account_type.toUpperCase();
    } while (!account_type.equals("EXIT") && !account_type.equals("CONTINUE"));
  }

  // Getting the Account Type and Verifying Input
  private void accountTypeVerfication () {
    do {
      System.out.println( "Enter the Account Portal You Want:" );
      if (checkCustomer()) {
        System.out.print("Customer");
        if (checkEmployee()) {
          System.out.print(" | Employee");
          if (checkAdmin()) {
            System.out.print(" | Admin");
          }
        } else if (checkAdmin()) {
          System.out.print(" | Admin");
        }
      } else if (checkEmployee()) {
        System.out.print("Employee");
        if (checkAdmin()) {
          System.out.print(" | Admin");
        }
      } else if (checkAdmin()) {
        System.out.print("Admin");
      }
      System.out.println("");
      account_type = scanner.nextLine();
      account_type = account_type.toUpperCase ();
      System.out.println();
    } while (!account_type.equals("CUSTOMER") && !account_type.equals("EMPLOYEE") && !account_type.equals("ADMIN"));
  }

  private void accountLogin () {
    Database tempdatabase = new Database();
    do {
      System.out.println( "Enter Username: " );
      username = scanner.nextLine();
      System.out.println();
      System.out.println("Enter Password");
      password = scanner.nextLine();
      System.out.println();
    } while (!tempdatabase.passwordVerfication(username, password));
    user = tempdatabase.LoadUser(username);
  }

  private void accountPortalCheck () {
    do {
      accountTypeVerfication();
    } while (!accountTypeCheck());
  }

  private void accountPortalLogin () {
    if (account_type.equals("CUSTOMER")) {
      customerPortal (user);
    } else if (account_type.equals("EMPLOYEE")) {
      employeePortal (user);
    } else if (account_type.equals("ADMIN")) {
    	adminPortal (user);
    } else {

    }
  }

  private boolean accountTypeCheck () {
    if (account_type.equals("CUSTOMER") && checkCustomer() ||
        account_type.equals("EMPLOYEE") && checkEmployee() ||
        account_type.equals("ADMIN") && checkAdmin()) {
      return true;
    } else {
      return false;
    }
  }

  private boolean checkCustomer () {
    return user.getCustomerPermission();
  }

  private boolean checkEmployee () {
    return user.getEmployeePermission();
  }

  private boolean checkAdmin() {
    return user.getAdminPermission();
  }

  private void customerPortal (UserAccount user) {
    CustomerPortal customer_user = new CustomerPortal(user);
  }
  
  private void employeePortal (UserAccount user) {
	EmployeePortal employee_user = new EmployeePortal(user);  
  }
  
  private void adminPortal (UserAccount user) {
	AdminPortal admin_user = new AdminPortal(user);  
  }
  
}
