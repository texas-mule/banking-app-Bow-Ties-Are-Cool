package randy;

import java.util.Scanner;

public class EmployeePortal {
	  private UserAccount user = null;
	  private BankAccount customer = null;
	  private Scanner scanner = new Scanner(System.in);
	  private Scanner scanner2 = new Scanner(System.in);


	  
	public EmployeePortal(UserAccount user) {
		super();
		this.user = user;
		run ();
	}
	  
	public void run () {
		greating ();
		loadAccount ();
		getAccountInformation();
		getAccountBalance();
		getPersonalInformation();
		if (customer.getStatus() == 0) {
			String choice;
			do {
		System.out.println("Do you want to aprove or disaprove the account");
		System.out.println("Aprove | Deny | Skip");
		choice = scanner2.nextLine();
		choice = choice.toUpperCase();
			} while (!choice.equals("APROVE") && !choice.equals("DENY") && !choice.equals("SKIP"));
			if (choice.equals("APROVE")) {
				aproval();
			} else if (choice.equals("DENY")) {
				deny();
			}
		}
		
		
		
	}
	
	private void greating () {
		System.out.println("Our Employees are what makes great!");
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

	
	private void getAccountInformation () {
	    System.out.println("Account Number: " + customer.getAccountNumber());
	    System.out.print("Main Owner: " + customer.getMainSigner() + "\t");
	    System.out.println("Co Owner: " + customer.getCoSigner());
	    System.out.println("Balance: $" + customer.getBalance());
	    System.out.println();
	}
	
	private void getAccountBalance() {
	    System.out.println("Account Number: " + customer.getAccountNumber());
        System.out.println("Balance: $" + customer.getBalance());
	    System.out.println();
	}
	
	private void getPersonalInformation() {
	    System.out.println("Account Number: " + customer.getAccountNumber());
	    System.out.println("Status: " + customer.getStatus());
	    System.out.println();
	}
	
	private void aproval () {
		Database update = new Database();
		update.updateStatus(1, customer.getAccountNumber());	
	}
	
	private void deny () {
		Database update = new Database();
		update.updateStatus(-1, customer.getAccountNumber());	
	}
	
	
	
}
