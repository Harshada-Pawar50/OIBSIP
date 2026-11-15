
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    private String details;
    private String date;

    public Transaction(String details) {
        this.details = details;
        this.date = LocalDateTime.now().format(
            DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")
        );
    }

    public void display() {
        System.out.println(date + " - " + details);
    }
}
