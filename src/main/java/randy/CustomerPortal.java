package randy;

import java.util.Scanner;
import java.util.ArrayList;

public class CustomerPortal {

  private String customer_action;
  private int customer_account;
  private String co_signer;
  private Scanner scanner = new Scanner(System.in);
  private Scanner scanner2 = new Scanner(System.in);
  private Scanner scanner3 = new Scanner(System.in);
  private UserAccount user = null;
  private ArrayList<BankAccount> accounts = null;
  private ArrayList<Integer> account_numbers = new ArrayList<Integer> ();
  private int current_account;

  public CustomerPortal (UserAccount user) {
    this.user = user;
    run();
  }

  public void run () {
    customerGreating();
    loadInAccounts();
    customerMenu();
    if (customer_action.equals("CREATE")) {
      openNewBankAccount();  
    } else {
      selectBankAccount();
      if (accounts.get(current_account).getStatus() == 1) {
        selectInformation();
        if (customer_action.equals("INFO")) {
          accountInformation();
        } else if (customer_action.equals("BALANCE")) {
          balance();
        } else if (customer_action.equals("DEPOSIT")) {
          deposit();
        } else if (customer_action.equals("WITHDRAW")) {
          withdraw();
        } else if (customer_action.equals("TRANSFER")) {
          transfer();
        }
      } else {
        System.out.println("Sorry that account is not active/ been aproved by an admin");  
      }
    }
  }

  private void customerGreating () {
    System.out.println("Hello Valued Customer");
  }

  private void loadInAccounts() {
    Database loadin = new Database();
    accounts = loadin.loadInBankAccount(user);
  }

  private void customerMenu() {
    do {
      System.out.println("Are you Creating a New Account or Selecting a current account?:");
      System.out.println("Create | Select"); //"Balance | Deposit | Withdraw");
      customer_action = scanner.nextLine();
      customer_action = customer_action.toUpperCase ();
      System.out.println();
    } while (!customer_action.equals("CREATE") && !customer_action.equals("SELECT"));
  }

  private void selectBankAccount() {
    accounts.forEach((n) -> account_numbers.add(n.getAccountNumber()));
    do {
      System.out.println("What Account Do You Want?");
      System.out.println();
      accounts.forEach((n) -> n.printOutCustomerView());
      customer_account = scanner2.nextInt();
    } while (!selectAccountNumber(customer_account));
  }

  private boolean selectAccountNumber (int search) {
    int count = 0;
    for (Integer account_number : account_numbers) {
      if (account_number.equals(search)) {
        current_account = count;
        return true; 
      }
      count++;
    }
    return false;
  }

  private void selectInformation() {
    do {
      System.out.println("Select an action");
      System.out.println("Info | Balance | Deposit | Withdraw | Transfer");
      customer_action = scanner.nextLine();
      customer_action = customer_action.toUpperCase();
      System.out.println();
    } while (!customer_action.equals("INFO") && !customer_action.equals("BALANCE") && !customer_action.equals("DEPOSIT") && !customer_action.equals("WITHDRAW") && !customer_action.contentEquals("TRANSFER"));
  }

  private void accountInformation() {
    System.out.println("Account Number: " + accounts.get(current_account).getAccountNumber());
    System.out.print("Main Owner: " + accounts.get(current_account).getMainSigner() + "\t");
    System.out.println("Co Owner: " + accounts.get(current_account).getCoSigner());
    System.out.println("Balance: $" + accounts.get(current_account).getBalance());
    System.out.println();
  }

  private double balance () {
    System.out.println("Balance: $" + accounts.get(current_account).getBalance());
    return accounts.get(current_account).getBalance();
  }

  private double deposit () {
    double deposit_amount;
    Database export = new Database();
    do {
      System.out.println("How Much are you depositing: ");
      deposit_amount = scanner3.nextDouble();
    } while (deposit_amount < 0.0);
    accounts.get(current_account).setBalance(accounts.get(current_account).getBalance() + deposit_amount);
    System.out.println("New Balance");
    balance();
    export.updateBalance(accounts.get(current_account).getBalance(), accounts.get(current_account).getAccountNumber());
    return accounts.get(current_account).getBalance();
  }

  private double withdraw () {
    double withdraw_amount;
    Database export = new Database();
    do {
      System.out.println("Current Balance");
      balance();
      System.out.println("How Much are you Withdrawing: ");
      withdraw_amount = scanner3.nextDouble();
    } while (withdraw_amount < 0.0 || (accounts.get(current_account).getBalance() - withdraw_amount) < 0.0);
    accounts.get(current_account).setBalance(accounts.get(current_account).getBalance() - withdraw_amount);
    System.out.println("New Balance");
    balance();
    export.updateBalance(accounts.get(current_account).getBalance(), accounts.get(current_account).getAccountNumber());
    return accounts.get(current_account).getBalance();
  }

  private double transfer () {
    double transfer_amount;
    int transfer_account;
    BankAccount temp = null;
    Database export = new Database();
    do {
      System.out.println("Current Balance");
      balance();
      System.out.println("How Much are you Transfering: ");
      transfer_amount = scanner3.nextDouble();
    } while (transfer_amount < 0.0 || (accounts.get(current_account).getBalance() - transfer_amount) < 0.0);
    do {
      do {
        System.out.println("Enter the Account Number to transfer money to:");
        transfer_account = scanner2.nextInt();
      } while (!export.findAccount(transfer_account)); 
      temp = export.loadAccount(transfer_account);
    } while (temp.getStatus()!=1);
    accounts.get(current_account).setBalance(accounts.get(current_account).getBalance() - transfer_amount);
    temp.setBalance(temp.getBalance()+transfer_amount);  
    System.out.println("New Balance");
    balance();
    export.updateBalance(accounts.get(current_account).getBalance(), accounts.get(current_account).getAccountNumber());
    export.updateBalance(temp.getBalance(), temp.getAccountNumber());    
    return accounts.get(current_account).getBalance();
  }

  private void openNewBankAccount () {
    Database export = new Database();
    String password;
    do {
      System.out.println("Is there a co-signer");
      System.out.println("Yes | No");
      co_signer = scanner.nextLine();
      co_signer = co_signer.toUpperCase();
    } while (!co_signer.equals ("YES") && !co_signer.equals("NO"));
    if (co_signer.equals("YES")) {
      do {
        System.out.println( "Co-Signer's Username: " ); 
        co_signer = scanner.nextLine();
        System.out.println();
        System.out.println("Co-Signer's Password: ");
        password = scanner.nextLine();
        System.out.println();
      } while (!export.passwordVerfication(co_signer, password));
    } else {
      co_signer = null;
    }
    BankAccount new_account = new BankAccount(user.getUsername(), co_signer, 0, 0.00);
    System.out.println("Do Know that an Admin will have to aprove the account");
    export.exportBankAccount(new_account);
  }
}
