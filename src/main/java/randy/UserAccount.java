package randy;

public class UserAccount {
  private String username;
  private String password;
  private boolean customer_permission;
  private boolean employee_permission;
  private boolean admin_permission;
  //aproval acts like a multi state bool flag
  //Zero means the account hasnt been looked or marked yet for aproval or
  //disaproval
  //One means the account has been aproved
  //-1 means that the account has been denied
  //-2 means the account has a hold on the account cause them to not be able to
  //withdraw or deposit
  //-3 means the account was suspended after it had been aproved
  // 2 means that the account was deactive by the end user there accounts
  // information will remain, however it will require an admins override in
  // order to reinstate the account. This will send the account back to state
  // zero
  private int aproval = 0;

  public UserAccount (String username, String password, boolean customer_permission, boolean employee_permission, boolean admin_permission) {
    this.username = username;
    this.password = password;
    this.customer_permission = customer_permission;
    this.employee_permission = employee_permission;
    this.admin_permission = admin_permission;
  }

  public String getUsername () {
    return username;
  }
  public void setUsername (String username) {
    this.username = username;
  }
  public String getPassword () {
    return password;
  }
  public void setPassword (String password) {
    this.password = password;
  }
  public boolean getCustomerPermission () {
    return customer_permission;
  }
  public void setCustomerPermission (boolean customer_permission) {
    this.customer_permission = customer_permission;
  }
  public boolean getEmployeePermission () {
    return employee_permission;
  }
  public void setEmployeePermission (boolean employee_permission) {
    this.employee_permission = employee_permission;
  }
  public boolean getAdminPermission () {
    return admin_permission;
  }
  public void setAdminPermission (boolean admin_permission) {
    this.admin_permission = admin_permission;
  }

  public void printOutUser () {
    System.out.println("Username: " + getUsername());
    System.out.println("Password: " + getPassword());
    System.out.println("Customer: " + getCustomerPermission());
    System.out.println("Employee: " + getEmployeePermission());
    System.out.println("Admin: " + getAdminPermission());
    System.out.println();
  }
}
