import java.util.ArrayList;
import java.util.Scanner;

import java.io.*;

public class Simulator {

	public static void main(String[] args) throws Throwable {
    	ArrayList<Account> initialAccounts = getInitialAccounts();
    	
    	ATM atm = new ATM(initialAccounts);
    	
    	Scanner input = new Scanner(System.in);
    	
    	System.out.println("ATM Simulator");
    	System.out.println("Read from file? [y,n]");
    	String decision = input.nextLine().trim();
    	if(decision.equals("y")) {
    		// The name of the file to open.
            String fileName = "transactions.txt";

            // This will reference one line at a time
            String line = null;

            try {
                // FileReader reads text files in the default encoding.
                FileReader fileReader = 
                    new FileReader(fileName);

                // Always wrap FileReader in BufferedReader.
                BufferedReader bufferedReader = 
                    new BufferedReader(fileReader);
                
                ATM.BankingSession session = null;
                char prevCMD = ' ';
                
                while((line = bufferedReader.readLine()) != null) {
                    String delims = "[ ]+";
                    String[] tokens = line.split(delims);
                    @SuppressWarnings("unused")
					String time = tokens[0];
                    String cmd = tokens[1];
                    String param = tokens[2];
                    
                    System.out.println(line + " ");
                    //prevCMD can be 'r' for read, 'd' for deposit, 'w' for withdraw, 'b' for check balance
                    switch(cmd) {
                    	case "CARDREAD":{
                    		Card card = new Card(Integer.parseInt(param));
                    		session = (ATM.BankingSession) atm.getCardReader().insertCard(card);
                    		prevCMD='r';
                    		break;
                    	}
                    	case "NUM":{
                    		//Don't want a null pointer exception
                    		switch(prevCMD) {
                    			case 'r':{
                    				if(session != null) {
                                		session.tryAuthenticate(Integer.parseInt(param));
                            		}
                    				break;
                    			}
                    			case 'w':{
                    				ATM.WithdrawingTransaction transaction = (ATM.WithdrawingTransaction) session.withdraw();
                    				transaction.provideAmount(Integer.parseInt(param));          				
                    				session.commitTransaction(transaction);
                    				break;
                    			}
                    			case 'b':{
                    				//we need the atm to print
                    				session.printBalance();
                    				break;
                    			}
                    			case 'd':{
                    				ATM.DepositingTransaction transaction = (ATM.DepositingTransaction) session.deposit();
                    				transaction.provideAmount(Integer.parseInt(param));
                    				session.commitTransaction(transaction);
                    				break;
                    			}
                    		}
                    		break;
                    	}
                    	case "BUTTON":{
                    		switch(param) {
                    			case "D":{
                    				prevCMD='d';
                    				break;
                    			}
                    			case "W":{
                    				prevCMD='w';
                    				break;
                    			}
                    			case "CB":{
                    				session.printBalance();
                    				break;
                    			}
                    			case "CANCEL":{
                    				prevCMD=' ';
                    				//session.end();
                    				atm.getCardReader().ejectCard();
                    				session = null;
                    				
                    				break;
                    			}
                    		}
                    		break;
                    	}
                    	case "DIS":{
                    		//do we need an individual display component?
                    		session.display(param);
                    		break;
                    	}
                    	case "PRINT":{
                    		atm.getReceiptPrinter().print(param);
                    		break;
                    	}
                    }
                    
                }   

                // Always close files.
                bufferedReader.close();         
            }
            catch(FileNotFoundException ex) {
                System.out.println(
                    "Unable to open file '" + 
                    fileName + "'");                
            }
            catch(IOException ex) {
                System.out.println(
                    "Error reading file '" 
                    + fileName + "'");                  
                // Or we could just do this: 
                // ex.printStackTrace();
            }
            catch (Exception ex) {
            	System.err.println(ex.toString());
            }
    		
    	}else {
    	//Enter a loop to get user input
    		ATM.BankingSession session = null;
            char prevCMD = ' ';
	    	while(true) {
	    		
                System.out.print("Enter a command: ");
                String cmd = input.nextLine();
                System.out.print("Enter a value: ");
                String param = input.nextLine();
                System.out.println();
	    		switch(cmd) {
            	case "CARDREAD":{
            		Card card = new Card(Integer.parseInt(param));
            		session = (ATM.BankingSession) atm.getCardReader().insertCard(card);
            		prevCMD='r';
            		break;
            	}
            	case "NUM":{
            		//Don't want a null pointer exception
            		switch(prevCMD) {
            			case 'r':{
            				if(session != null) {
                        		session.tryAuthenticate(Integer.parseInt(param));
                    		}
            				break;
            			}
            			case 'w':{
            				ATM.WithdrawingTransaction transaction = (ATM.WithdrawingTransaction) session.withdraw();
            				transaction.provideAmount(Integer.parseInt(param));          				
            				session.commitTransaction(transaction);
            				break;
            			}
            			case 'b':{
            				//we need the atm to print
            				session.printBalance();
            				break;
            			}
            			case 'd':{
            				ATM.DepositingTransaction transaction = (ATM.DepositingTransaction) session.deposit();
            				transaction.provideAmount(Integer.parseInt(param));
            				session.commitTransaction(transaction);
            				break;
            			}
            		}
          
            	}
            	case "BUTTON":{
            		switch(param) {
            			case "D":{
            				prevCMD='d';
            				break;
            			}
            			case "W":{
            				prevCMD='w';
            				break;
            			}
            			case "CB":{
            				session.printBalance();
            				break;
            			}
            			case "CANCEL":{
            				prevCMD=' ';
            				//session.end();
            				atm.getCardReader().ejectCard();
            				session = null;
            				
            				break;
            			}
            		}
            		break;
            	}
            	case "DIS":{
            		//do we need an individual display component?
            		session.display(param);
            		break;
            	}
            	case "PRINT":{
            		atm.getReceiptPrinter().print(param);
            		break;
            	}
            }
	    	}
	    
    	}
    	input.close();
    	
	}

	
	private static ArrayList<Account> getInitialAccounts() {
    	Account accountOne = new Account(1234, 6789, 80);
    	Account accountTwo = new Account(6789, 4321, 60);

    	ArrayList<Account> accounts = new ArrayList<Account>();
    	accounts.add(accountOne);
    	accounts.add(accountTwo);
    	
    	return accounts;
	}
	

}
