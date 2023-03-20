import java.util.Scanner;
import java.util.List;
public class Main {
    public static void main(String[] args) {
        LibraryManagementSystem system = new LibraryManagementSystem();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to library management system!!");
        System.out.println("Commands accepted by the system: ");
        List<String> commands = system.getAllowedCommands();
        for(String command : commands){
            System.out.println(command);
        }
        System.out.println(" ");
        System.out.println("input your commands: ");

        while(true){
            String command = scanner.nextLine();
            boolean stop = system.processCommand(command);
            if(stop)break;
        }
    }
}