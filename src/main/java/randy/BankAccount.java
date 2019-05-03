package randy;

public class BankAccount {
  private int account_number;
  private String main_signer;
  private String co_signer;
  private int status;
  private double balance;

  //CONSTRUCTORS
  public BankAccount (int account_number, String main_signer, String co_signer, int status, double balance) {
    this.account_number = account_number;
    this.main_signer = main_signer;
    this.co_signer = co_signer;
    this.status = status;
    this.balance = balance;
  }
  public BankAccount (String main_signer, String co_signer, int status, double balance) {
    this.main_signer = main_signer;
    this.co_signer = co_signer;
    this.status = status;
    this.balance = balance;
  }

  //GETTERS & SETTERS
  public int getAccountNumber () {
    return account_number;  
  }
  public void setAccountNumber (int account_number) {
    this.account_number = account_number; 
  }
  public String getMainSigner () {
    return main_signer;
  }
  public void setMainSigner (String main_signer) {
    this.main_signer = main_signer; 
  }
  public String getCoSigner () {
    return co_signer;
  }
  public void setCoSigner (String co_signer) {
    this.co_signer = co_signer; 
  }
  public int getStatus () {
    return status;
  }
  public void setStatus (int status) {
    this.status = status; 
  }
  public double getBalance () {
    return balance;
  }
  public void setBalance (double balance) {
    this.balance = balance; 
  }

  //PRINT OUT FUNCTION
  public void printOutAccount () {
    System.out.println("AccountNumber: " + getAccountNumber());
    System.out.println("Main Signer: " + getMainSigner());
    System.out.println("Co Signer: " + getCoSigner());
    System.out.println("Status: " + getStatus());
    System.out.println("Balance: " + getBalance());
    System.out.println();
  }

  public void printOutCustomerView () {
    System.out.println("AccountNumber: " + getAccountNumber());
    System.out.println("Main Signer: " + getMainSigner());
    System.out.println("Co Signer: " + getCoSigner());
    System.out.println("Balance: " + getBalance());
    System.out.println();
  }
}
