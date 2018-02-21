import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Printer {
	public String print(String transType, double amount){
		 LocalDateTime time = LocalDateTime.now();
		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		 String formatDateTime = time.format(formatter);
		 
		return (formatDateTime + " " + transType + " $" + amount);
	}

}
