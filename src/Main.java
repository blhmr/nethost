import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        if (args.length > 0) {
            String mode = args[0];
            if (mode.equalsIgnoreCase("receive")) {
                System.out.print("Port: ");
                int port = Integer.parseInt(scanner.nextLine());

                System.out.print("Password: ");
                String password = scanner.nextLine();

                System.out.print("Path: ");
                String path = scanner.nextLine();

                NethostServer.start(port, password, path);
            }
            else if (mode.equalsIgnoreCase("send")) {
                System.out.print("Host: ");
                String host = scanner.nextLine();

                System.out.print("Port: ");
                int port = Integer.parseInt(scanner.nextLine());

                System.out.print("Password: ");
                String password = scanner.nextLine();

                System.out.print("Path: ");
                String path = scanner.nextLine();

                try {
                    NethostClient.start(host, port, password, path);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else {
                System.err.println("Invalid argument. (usage: 'nethost send' or 'nethost receive')");
            }
        }
        else {
            System.err.println("Incomplete command. (usage: 'nethost send' or 'nethost receive')");
        }
        scanner.close();
    }
}
