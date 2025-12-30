import java.util.Scanner;
import java.io.*;

public class Mini_Notepad {

    static Scanner sc = new Scanner(System.in);

    // Main Method
    public static void main(String[] args) {
        System.out.println(" Launching Notepad...");
        while (true) {
            System.out.println("Select an option given below:");
            System.out.println(" 1. Create a new file ");
            System.out.println(" 2. Overwrite content to a file ");
            System.out.println(" 3. Append content to a file ");
            System.out.println(" 4. Read the content of a file ");
            System.out.println(" 5. Delete a file ");
            System.out.println(" 6. Exit Notepad ");
            System.out.print(" Enter your choice (1-6): ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    createFile();
                    break;
                case 2:
                    overwriteFile();
                    break;
                case 3:
                    appendFile();
                    break;
                case 4:
                    readFile();
                    break;
                case 5:
                    deleteFile();
                    break;
                case 6:
                    System.out.println(" Exiting Notepad. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println(" Invalid choice. Please select a valid option (1-6).");
                    break;
            }
        }
    }

    // Method 001 ( to create a new file )
    public static void createFile() {
        System.out.println(" Enter the name of the file to be created: ");
        String filename = sc.nextLine();
        File file = new File(filename);
        try {
            if (file.createNewFile()) {
                System.out.println(" File created: " + file.getName());
            } else {
                System.out.println(" File already exists.");
            }
        } catch (IOException e) {
            System.out.println(" An error occurred while creating a new file.");
            e.printStackTrace();
        }
    }

    // Method 002 ( to overwrite content to a file )
    public static void overwriteFile() {
        System.out.println("Enter the name of the file to overwrite content: ");
        String filename = sc.nextLine();
        try {
            FileWriter writer = new FileWriter(filename, false);
            System.out.println("Enter the content to overwrite: ");
            String content;
            while (true) {
                content = sc.nextLine();
                if (content.equals("End") || content.equals("END") || content.equals("end") || content.equals("eNd")
                        || content.equals("enD")) {
                    writer.write(content + "\n");
                    break;
                }
            }
            System.out.println("File Overwritten successfully .");
        } catch (IOException e) {
            System.out.println("An error occurred while overwriting the file.");
            e.printStackTrace();
        }

    }

    // Method 003 (to append the content)
    public static void appendFile() {
        System.out.println("Enter the file name to be append");
        String filename = sc.nextLine();
        try {
            FileWriter writer = new FileWriter(filename, true);
            System.out.println("Write your content here : ");
            String content;
            while (true) {
                content = sc.nextLine();
                if (content.equals("End") || content.equals("END") || content.equals("end") || content.equals("eNd")
                        || content.equals("enD")) {
                    writer.write(content + "\n");
                    break;
                }
            }
            System.out.println("Appended successfully to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred while appending the file.");
            e.printStackTrace();
        }
    }

    // Method 004 ( to read the content of a file )
    public static void readFile() {
        System.out.println("Enter the name of file to be read: ");
        String filename;
        try {
            filename = sc.nextLine();
            FileReader fr = new FileReader(filename);
            BufferedReader br = new BufferedReader(fr);
            String line;
            System.out.println("\n-----The content of the file is -----\n");
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
            System.out.println("\n-----End of file-----\n");
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file.");
            e.printStackTrace();
        // } catch (FileNotFoundException fe) {
        //     System.out.println("File not found. Please check the filename and try again.");
        //      e.printStackTrace();
        } catch (Exception e) {
            System.out.println("An unexpected error occurred.");
            e.printStackTrace();
        }
    }

    // Method 005 ( to delete a file )
    public static void deleteFile() {
        System.out.println("Enter the name of the file to be deleted :");
        String filename = sc.nextLine();
        File file = new File(filename);
        try {
            if (file.delete()) {
                System.out.println("File deleted successfully : " + filename);
            }
        // } catch (FileNotFoundException ee) {
        //     System.out.println("File not found. Please check the filename and try again if exists.");
        //     ee.printStackTrace();
        } catch (Exception e2) {
            System.out.println("An unexpected error occurred.");
            e2.printStackTrace();
        }
    }

}