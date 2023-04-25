import java.io.*;
import java.util.Scanner;

public class Task2 {
    public static void main(String[] args) {
        /*
        Написати додаток, який підраховує кількість слів у кожному текстовому файлі.
         Вивести назву файлу і кількість слів на екран.
         Передбачається, що слова у файлі розділяються одним пропуском,
         але на початку і в кінці речення може стояти будь-яку кількість пробілів.
          Вивести назву файлу і кількість слів на екран.
         */
        Scanner scanner = new Scanner(System.in);
        //String path = null;
        String path = "task1";
//        boolean directoryExists = false;
//        while (!directoryExists) {
//            System.out.print("Enter absolute path to the directory: ");
//            path = scanner.nextLine();
//            File folder = new File(path);
//            if (folder.exists() && folder.isDirectory()) {
//                directoryExists = true;
//            } else {
//                System.out.println("Please enter real absolute path to the directory.");
//            }
//        }

        File[] files = new File(path).listFiles();

        for (File file : files) {
            if (file.isFile()) {
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(file));
                    String line;
                    int wordCount = 0;
                    while ((line = reader.readLine()) != null) {
                        if(line.trim().isEmpty()){
                            wordCount +=0;
                        }
                        else {
                            wordCount += line.trim().split("[\\s.,;:!?]+").length;
                        }

                    }
                    reader.close();
                    System.out.println(file.getName() + ": " + wordCount + " words");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
