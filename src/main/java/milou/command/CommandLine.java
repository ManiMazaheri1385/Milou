package milou.command;

import milou.email.*;
import milou.service.*;
import java.util.Scanner;

public class CommandLine {

    private static Scanner scanner = new Scanner(System.in);
    private static User user;
    private static boolean loggedIn = false;
    private static boolean viewing = false;

    public void start() {
        System.out.println();

        while (true) {

            if (!loggedIn) {
                System.out.println("[L]ogin, [S]ign up, [E]xit: ");
            }
            else if (!viewing) {
                System.out.println("[S]end, [V]iew, [R]eply, [F]orward, [E]xit: ");
            }
            else {
                System.out.println("[A]ll emails, [U]nread emails, [S]ent emails, Read by [C]ode, [B]ack, [E]xit: ");
            }

            System.out.print("> ");
            String command = scanner.nextLine().trim();

            if (command.equals("E")) {
                System.out.println("Exiting...");
                break;
            }

            if (!loggedIn) {
                primaryProcessCommand(command);
            } else if (viewing) {
                viewProcessCommand(command);
            } else {
                processLoggedInCommand(command);
            }

            System.out.println();
        }

        System.out.println();
    }

    public void primaryProcessCommand(String command) {
        try {
            switch (command) {
                case "L" -> login();
                case "S" -> signUp();
                default -> System.out.println("Unknown command: " + command);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void processLoggedInCommand(String command) {
        try {
            switch (command) {
                case "S" -> send();
                case "V" -> viewing = true;
                case "R" -> reply();
                case "F" -> forward();
                default -> System.out.println("Unknown command: " + command);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void viewProcessCommand(String command) {
        try {
            switch (command) {
                case "A" -> allEmails();
                case "U" -> unreadEmails();
                case "S" -> sentEmails();
                case "C" -> readByCode();
                case "B" -> viewing = false;
                default -> System.out.println("Unknown command: " + command);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void login() {
        System.out.println("Enter your Email: ");
        String email = scanner.nextLine();

        System.out.println("Enter your Password: ");
        String password = scanner.nextLine();

        user = UserService.login(email, password);

        loggedIn = true;

        System.out.println("Welcome back, " + user.getName() + "!");

        System.out.println();

        unreadEmails();
    }

    private void signUp() {
        System.out.println("Enter your Name: ");
        String name = scanner.nextLine();

        System.out.println("Enter your Email: ");
        String email = scanner.nextLine();

        System.out.println("Enter your Password: ");
        String password = scanner.nextLine();

        UserService.signUp(name, email, password);

        System.out.println("Your new account is created");
        System.out.println("Go ahead and login!");
    }

    private void send() {
        System.out.println("Enter Recipient(s): ");
        String recipients = scanner.nextLine();

        System.out.println("Enter Subject: ");
        String subject = scanner.nextLine();

        System.out.println("Enter Body: ");
        StringBuilder bodyBuilder = new StringBuilder();
        String line;
        while (!(line = scanner.nextLine()).isEmpty()) {
            bodyBuilder.append(line).append("\n");
        }
        String body = bodyBuilder.toString().trim();

        Email email = EmailService.send(user, recipients, subject, body);

        System.out.println("Successfully sent your email.");
        System.out.println("Code: " + email.getCode());
    }

    private void reply() {
        System.out.println("Code:");
        String code = scanner.nextLine();

        System.out.println("Enter Body: ");
        StringBuilder bodyBuilder = new StringBuilder();
        String line;
        while (!(line = scanner.nextLine()).isEmpty()) {
            bodyBuilder.append(line).append("\n");
        }
        String body = bodyBuilder.toString().trim();

        EmailService.reply(user, code, body);
    }

    private void forward() {
        System.out.println("Code:");
        String code = scanner.nextLine();

        System.out.println("Enter Recipient(s): ");
        String recipients = scanner.nextLine();

        EmailService.forward(user, code, recipients);
    }

    private void allEmails() {
        EmailService.allEmails(user);
    }

    private void unreadEmails() {
        EmailService.unreadEmails(user);
    }

    private void sentEmails() {
        EmailService.sentEmails(user);
    }

    private void readByCode() {
        System.out.println("Code:");
        String code = scanner.nextLine();

        EmailService.readByCode(user, code);
    }

}
