package randy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Database{

  private final static String database_url = "";
  private final static String database_username = "";
  private final static String database_password = "";
  private PreparedStatement statement;


  // Takes a username and returns true if the user exist in the db or false if
  // it does not
  public boolean usernameExists (String username) {
    boolean exist = true;
    try (Connection connection = DriverManager.getConnection(database_url, database_username, database_password)) {
      String sql = ("SELECT username FROM useraccount WHERE username = ?;");
      statement = connection.prepareStatement(sql);
      statement.setString(1, username);
      ResultSet resultSet = statement.executeQuery();
      if (resultSet.next()) {
        exist = true;
      } else {
        exist = false;
      }
      resultSet.close();
    } catch (SQLException ex) {
      System.out.println("ERROR: 001");
    }
    return exist;
  }

  public boolean BankAccountExists (String main_signer) {
    boolean exist = true;
    try (Connection connection = DriverManager.getConnection(database_url, database_username, database_password)) {
      Statement statement = connection.createStatement();
      String sql = ("SELECT mainsigner, cosigner FROM bank WHERE username = '" + main_signer + "';");
      statement.execute(sql);
      ResultSet resultSet = statement.getResultSet();
      if (resultSet.next()) {
        exist = true;
      } else {
        exist = false;
      }
      resultSet.close();
    } catch (SQLException ex) {
      System.out.println("ERROR: 001");
    }
    return exist;
  }

  public BankAccount loadAccount (int lookup_account) {
    BankAccount temp = null;  
    try (Connection connection = DriverManager.getConnection(database_url, database_username, database_password)) {
      Statement statement = connection.createStatement();
      String sql = ("SELECT * FROM bankaccount WHERE accountnumber = " + lookup_account + ";");
      statement.execute(sql);
      ResultSet resultSet = statement.getResultSet();
      if (resultSet.next()) {     

        temp = new BankAccount(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4), resultSet.getDouble(5));
      }
      resultSet.close();
    } catch (SQLException ex) {
      System.out.println("ERROR: 001");
    }
    return temp;
  }

  public boolean findAccount (int lookup_account) {
    boolean exist = false;
    try (Connection connection = DriverManager.getConnection(database_url, database_username, database_password)) {
      Statement statement = connection.createStatement();
      String sql = ("SELECT accountnumber FROM bankaccount WHERE accountnumber = " + lookup_account + ";");
      statement.execute(sql);
      ResultSet resultSet = statement.getResultSet();
      if (resultSet.next()) {
        exist = true;
      } else {
        exist = false;
      }
      resultSet.close();
    } catch (SQLException ex) {
      System.out.println("ERROR: 001");
    }
    return exist;
  }

  // This takes the user that was passed and fills in the database with the user
  // information
  public boolean createNewUser (UserAccount new_user) {
    try (Connection connection = DriverManager.getConnection(database_url, database_username, database_password)) {
      Statement statement = connection.createStatement();
      String sql = ("insert into useraccount (username, password, customer, employee, admin) values ('"+ new_user.getUsername() +"', '"+ new_user.getPassword() +"', "+ new_user.getCustomerPermission() +", "+ new_user.getEmployeePermission() +", "+ new_user.getAdminPermission() +")");
      statement.execute(sql);
    } catch (SQLException ex) {
      System.out.println("ERROR: 002");
      return false;
    }
    return true;
  }

  public ArrayList<BankAccount> loadInBankAccount (UserAccount user) {
    ArrayList<BankAccount> accounts = new ArrayList<BankAccount>();
    try (Connection connection = DriverManager.getConnection(database_url, database_username, database_password)) {
      Statement statement = connection.createStatement();
      String sqlMain = ("SELECT * FROM bankaccount WHERE mainsigner = '" + user.getUsername() + "';");
      String sqlCo = ("SELECT * FROM bankaccount WHERE cosigner = '" + user.getUsername() + "';");
      boolean isResultSet = statement.execute(sqlMain);
      if (isResultSet) {
        ResultSet resultSet = statement.getResultSet();
        while (resultSet.next()) {
          BankAccount temp = new BankAccount(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4), resultSet.getDouble(5));
          accounts.add(temp);
        }
        resultSet.close();
      }
      isResultSet = statement.execute(sqlCo);
      if (isResultSet) {
        ResultSet resultSet = statement.getResultSet();
        while (resultSet.next()) {
          BankAccount temp = new BankAccount(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4), resultSet.getDouble(5));
          accounts.add(temp);
        }
        resultSet.close();
      }
    } catch (SQLException ex) {
      System.out.println("ERROR: 003");
      return null;
    }
    return accounts;
  }

  public void exportBankAccount (BankAccount new_account) {
    try (Connection connection = DriverManager.getConnection(database_url, database_username, database_password)) {
      Statement statement = connection.createStatement();
      String sql;
      if (new_account.getCoSigner()==null) {  
        sql = ("insert into bankaccount (mainsigner, status, balance) values ( '"+ new_account.getMainSigner() +"',"+ new_account.getStatus() +", "+ new_account.getBalance() +");");
      } else {
        sql = ("insert into bankaccount (mainsigner, cosigner, status, balance) values ( '"+ new_account.getMainSigner() +"', '" + new_account.getCoSigner() +"',"+ new_account.getStatus() +", "+ new_account.getBalance() +");");
      }
      statement.execute(sql);
    } catch (SQLException ex) {
      System.out.println("ERROR: 002");
    }
  }

  public void updateBalance (double new_balance, int account_number) {
    try (Connection connection = DriverManager.getConnection(database_url, database_username, database_password)) {
      Statement statement = connection.createStatement();
      String sql = ("update bankaccount set balance = "+ new_balance +" where accountnumber =" + account_number +";");
      statement.execute(sql);
    } catch (SQLException ex) {
      System.out.println("ERROR: 002");
    }
  }
  
  public void updateStatus (int new_status, int account_number) {
	    try (Connection connection = DriverManager.getConnection(database_url, database_username, database_password)) {
	      Statement statement = connection.createStatement();
	      String sql = ("update bankaccount set status = "+ new_status +" where accountnumber =" + account_number +";");
	      statement.execute(sql);
	    } catch (SQLException ex) {
	      System.out.println("ERROR: 002");
	    }
	  }

  public boolean passwordVerfication (String username, String password) {
    boolean success = false;
    try (Connection connection = DriverManager.getConnection(database_url, database_username, database_password)) {
      Statement statement = connection.createStatement();
      String username_sql = ("SELECT username FROM useraccount WHERE username = '" + username + "';");
      String password_sql = ("SELECT username, password FROM useraccount WHERE (username = '" + username + "' AND password = '" + password + "');");
      statement.execute(username_sql);
      ResultSet resultSet = statement.getResultSet();
      if (resultSet.next()) {
        statement.execute(password_sql);
        resultSet = statement.getResultSet();
        if (resultSet.next()) {
          System.out.println("Welcome: " + username); 
          System.out.println();
          success = true;
        } else {
          System.out.println("Username or Password are Incorrect");
          System.out.println("Re-enter Username or Password");
        }
      } else {
        System.out.println("Username or Password are Incorrect");
        System.out.println("Re-enter Username or Password");
      }
      resultSet.close();
    } catch (SQLException ex) {
      System.out.println("ERROR: 004");
    }
    return success;
  }

  public UserAccount LoadUser (String username) {
    UserAccount founduser = null;
    try (Connection connection = DriverManager.getConnection(database_url, database_username, database_password)) {
      Statement statement = connection.createStatement();
      String sql = ("SELECT * FROM useraccount WHERE username = '" + username + "'");
      statement.execute(sql);
      ResultSet resultSet = statement.getResultSet();
      if (resultSet.next()) {
        founduser= new UserAccount(resultSet.getString("username"), resultSet.getString("password"), resultSet.getBoolean("customer"), resultSet.getBoolean("employee"), resultSet.getBoolean("admin")); 
      } else {
        System.out.println("Error: User Doesn't Exist");
      }
      resultSet.close();
    } catch (SQLException ex) {
      System.out.println("ERROR: 005");
    }
    return founduser;
  }


public BankAccount loadUserEmployee (int account_number) {
    BankAccount founduser = null;
    try (Connection connection = DriverManager.getConnection(database_url, database_username, database_password)) {
      Statement statement = connection.createStatement();
      String sql = ("SELECT * FROM bankaccount WHERE accountnumber = " + account_number + ";");
      statement.execute(sql);
      ResultSet resultSet = statement.getResultSet();
      if (resultSet.next()) {
          founduser = new BankAccount(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4), resultSet.getDouble(5));
      } else {
        System.out.println("Error: User Doesn't Exist");
      }
      resultSet.close();
    } catch (SQLException ex) {
      System.out.println("ERROR: 005");
    }
    return founduser;
  }
}
