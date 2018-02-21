import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class Application {

	public static void main(String[] args) throws Throwable {
    	ArrayList<Account> initialAccounts = getInitialAccounts();
    	
    	ATM atm = new ATM(initialAccounts);
    	
    	Scanner input = new Scanner(System.in);
    	
    	System.out.println("ATM Simulator");
    	System.out.println("Read from file? [y,n]");
    	String decision = input.next();
    	if(decision=="y") {
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
                
                while((line = bufferedReader.readLine()) != null) {
                    String delims = "[ ]+";
                    String[] tokens = line.split(delims);
                    String time = tokens[0];
                    String cmd = tokens[1];
                    String param = tokens[2];
                    ATM.BankingSession session = null;
                    char prevCMD = ' ';
                    //prevCMD can be 'r' for read, 'd' for deposit, 'w' for withdraw, 'b' for check balance
                    switch(cmd) {
                    	case "CARDREAD":{
                    		Card card = new Card(Integer.parseInt(param));
                    		session = (ATM.BankingSession) atm.start(card);
                    		prevCMD='r';
                    	}
                    	case "NUM":{
                    		//Don't want a null pointer exception
                    		switch(prevCMD) {
                    			case 'r':{
                    				if(session != null) {
                                		session.tryAuthenticate(Integer.parseInt(param));
                            		}
                    			}
                    			case 'w':{
                    				session.withdraw(Integer.parseInt(param));
                    			}
                    			case 'b':{
                    				//we need the atm to print
                    				session.printBalance();
                    			}
                    			case 'd':{
                    				session.deposit(Integer.parseInt(param));
                    			}
                    		}
                  
                    	}
                    	case "BUTTON":{
                    		switch(param) {
                    			case "D":{
                    				prevCMD='d';
                    			}
                    			case "W":{
                    				prevCMD='w';
                    			}
                    			case "CB":{
                    				prevCMD='b';
                    			}
                    			case "CANCEL":{
                    				prevCMD=' ';
                    				session.end();
                    				session = null;
                    			}
                    		}
                    	}
                    	case "DIS":{
                    		session.display(param);
                    	}
                    	case "PRINT":{
                    		session.printString(param);
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
    		
    	}else {
    	//Enter a loop to get user input
	    	while(true) {
	    		//wait for card input (4 digits)
	    		System.out.print("Enter your card (4 digits): ");
	    		int cardNum = input.nextInt();
	    		Card card = new Card(cardNum);
	    		
	    		try {
		    		ATM.BankingSession session = (ATM.BankingSession) atm.start(card);    		
		    		while(!session.getIsAuthenticated()) {
		        		System.out.print("Enter your pin (4 digits): ");
		        		int pin = input.nextInt();
		        		
		        		if (!session.tryAuthenticate(pin)) {
		        			System.out.println("Pin is incorrect. Try again!");
		        		}
		    		}
		    		
		    		boolean done = false;
		    		while(!done) {
		    			System.out.print("Would you like to (W)ithdraw, make a (D)eposit or get a (B)alance inquiry?: ");
		    			String cmd = input.next().toLowerCase();
		    			
		    			switch(cmd) {
			    			case "d": {			
				    				System.out.print("How much would you like to deposit?: ");
				    				int amount = input.nextInt();
				    				if (amount < 1) {
				    					System.out.println("Can't deposit a negative number.");
				    					continue;
				    				}
				    				
				    				System.out.println("New Balance: $" + session.deposit(amount));
				    				done = true;
				    				break;
				    			}
			    			case "w": {
				    				System.out.print("How much would you like to withdraw?: ");
				    				int amount = input.nextInt();
				    				if (amount < 1) {
				    					System.out.println("Can't widthdraw a negative number.");
				    					continue;
				    				}
				    				
				    				int newAmount = session.withdraw(amount);
				    				if (newAmount != -1) {
				    					System.out.println("New Balance: $" + newAmount);
					    				done = true;
				    					break;
				    				} else {
				    					System.out.println("Insufficient funds.");
				    				}
				    			}
			    				break;
			    			case "b": {
			    				System.out.println("Your Balance: $" + session.getBalance());
			    				break;
				    			}
			    			default:
			    				System.out.println("Invalid command. Try again.");
			    				continue;
		    			}
	
		    			//end of transaction
		    			session.end();
		    			System.out.println("Transaction complete!");
		    			System.out.println();
		    			input.close();
		    		}
		    		
	    		} catch (Throwable ex) {
	    			System.out.println(ex.getMessage());
	    		}
	    	}
    	}
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
