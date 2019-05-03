package randy;

import java.util.ArrayList;
import java.util.Scanner;

public class AdminPortal {
	private UserAccount user = null;
	  private Scanner scanner = new Scanner(System.in);
	  private Scanner scanner2 = new Scanner(System.in);
	  
	  private String admin_action;

	  private Scanner scanner3 = new Scanner(System.in);
	  private BankAccount customer = null;
	  private int current_account;


	  
	public AdminPortal(UserAccount user) {
		super();
		this.user = user;
		run ();
	}
	
	public void run () {
		greating ();
		loadAccount();
		System.out.println("What Do You Want to Do?");
		System.out.println("BALANCE | DEPOSIT | WITHDRAW | TRANSFER");
		admin_action = scanner2.nextLine();
		admin_action = admin_action.toUpperCase();
		if (admin_action.equals("INFO")) {
	        selectInformation();
	        } else if (admin_action.equals("BALANCE")) {
	          balance();
	        } else if (admin_action.equals("DEPOSIT")) {
	          deposit();
	        } else if (admin_action.equals("WITHDRAW")) {
	          withdraw();
	        } else if (admin_action.equals("TRANSFER")) {
	          transfer();
	        } 
			String choice;
			do {
		System.out.println("Do you want to aprove or disaprove the account");
		System.out.println("Aprove | Deny | Cancel | Skip");
		choice = scanner2.nextLine();
		choice = choice.toUpperCase();
			} while (!choice.equals("APROVE") && !choice.equals("DENY") && !choice.equals("SKIP") && !choice.equals("CANCEL"));
			if (choice.equals("APROVE")) {
				aproval();
			} else if (choice.equals("DENY")) {
				deny();
			} else if (choice.equals("CANCEL")) {
	        	cancel();
	        }
		
	}
	
	private void greating () {
		System.out.println("Hello Admin");
	}
	private void loadAccount () {
		int account_number;
		Database loadin = new Database();
	    do {
	    	System.out.println("Enter the Account Number");
	    	account_number = scanner.nextInt();
	    	customer = loadin.loadUserEmployee (account_number);
	      } while (customer==null);
	}
	
	  private void selectInformation() {
		    do {
		      System.out.println("Select an action");
		      System.out.println("Info | Balance | Deposit | Withdraw | Transfer");
		      admin_action = scanner.nextLine();
		      admin_action = admin_action.toUpperCase();
		      System.out.println();
		    } while (!admin_action.equals("INFO") && !admin_action.equals("BALANCE") && !admin_action.equals("DEPOSIT") && !admin_action.equals("WITHDRAW") && !admin_action.contentEquals("TRANSFER"));
		  }
	
	 private double deposit () {
		    double deposit_amount;
		    Database export = new Database();
		    do {
		      System.out.println("How Much are you depositing: ");
		      deposit_amount = scanner3.nextDouble();
		    } while (deposit_amount < 0.0);
		    customer.setBalance(customer.getBalance() + deposit_amount);
		    System.out.println("New Balance");
		    balance();
		    export.updateBalance(customer.getBalance(), customer.getAccountNumber());
		    return customer.getBalance();
		  }

		  private double withdraw () {
		    double withdraw_amount;
		    Database export = new Database();
		    do {
		      System.out.println("Current Balance");
		      balance();
		      System.out.println("How Much are you Withdrawing: ");
		      withdraw_amount = scanner3.nextDouble();
		    } while (withdraw_amount < 0.0 || (customer.getBalance() - withdraw_amount) < 0.0);
		    customer.setBalance(customer.getBalance() - withdraw_amount);
		    System.out.println("New Balance");
		    balance();
		    export.updateBalance(customer.getBalance(), customer.getAccountNumber());
		    return customer.getBalance();
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
		    } while (transfer_amount < 0.0 || (customer.getBalance() - transfer_amount) < 0.0);
		    do {
		      do {
		        System.out.println("Enter the Account Number to transfer money to:");
		        transfer_account = scanner2.nextInt();
		      } while (!export.findAccount(transfer_account)); 
		      temp = export.loadAccount(transfer_account);
		    } while (temp.getStatus()!=1);
		    customer.setBalance(customer.getBalance() - transfer_amount);
		    temp.setBalance(temp.getBalance()+transfer_amount);  
		    System.out.println("New Balance");
		    balance();
		    export.updateBalance(customer.getBalance(), customer.getAccountNumber());
		    export.updateBalance(temp.getBalance(), temp.getAccountNumber());    
		    return customer.getBalance();
		  }
	
		  private double balance () {
			    System.out.println("Balance: $" + customer.getBalance());
			    return customer.getBalance();
			  }
		  
		  private void aproval () {
				Database update = new Database();
				update.updateStatus(1, customer.getAccountNumber());	
			}
			
			private void deny () {
				Database update = new Database();
				update.updateStatus(-1, customer.getAccountNumber());	
			}
			private void cancel () {
				Database update = new Database();
				update.updateStatus(-2, customer.getAccountNumber());	
			}
}
