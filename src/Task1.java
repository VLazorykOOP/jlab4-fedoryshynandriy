import java.io.*;
import java.util.*;

//    З файлу, заданого користувачем зчитати список слів.
//    Підрахувати кількість входжень цих слів в текст, що міститься в іншому файлі,
//    який також задається користувачем. Вивести на консоль відсортований за спаданням список слів,
//    за якими здійснювався пошук, з кількістю входження кожного з них.
//    В файл, заданий користувачем, вивести відсортований список слів з тексту,
//    в якому здійснювався пошук, попередньо вилучивши усі дублікати.
    public class Task1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        //WORD LIST
        //System.out.println("Enter absolute path to the file with word list: ");
        //String word_list_absolutePath = scanner.nextLine();
        String word_list_absolutePath = "C:\\java\\jlab4-fedoryshynandriy\\task1\\wordList.txt";

        File word_list_file = new File(word_list_absolutePath);
        while (!word_list_file.exists()) {
            System.out.println("Please enter real absolute path: ");
            word_list_absolutePath = scanner.nextLine();
            word_list_file = new File(word_list_absolutePath);
        }
        //TEXT
        //System.out.println("Enter absolute path to the file with text: ");
        //String text_absolutePath = scanner.nextLine();
        String text_absolutePath = "C:\\java\\jlab4-fedoryshynandriy\\task1\\text.txt";

        File text_file = new File(text_absolutePath);
        while (!text_file.exists()) {
            System.out.println("Please enter real absolute path: ");
            text_absolutePath = scanner.nextLine();
            text_file = new File(text_absolutePath);
        }
        //OUTPUT
        //System.out.println("Enter absolute path to the file where to write words: ");
        //String output_absolutePath = scanner.nextLine();
        String output_absolutePath = "C:\\java\\jlab4-fedoryshynandriy\\task1\\output.txt";
        File output_file = new File(output_absolutePath);

        boolean append=false;
        if (output_file.exists()) {
            //OVERWRITE OUTPUT FILE?
            System.out.print("File already exists. Do you want to overwrite it? (Y/N): ");
            String answer = scanner.nextLine();
            while (!answer.equals("Y")&&!answer.equals("N")){
                System.out.println("Please enter Y or N: ");
                answer = scanner.nextLine();
            }
            if (answer.equals("Y")) {
                //APPEND OUTPUT FILE?
                System.out.print("Would you like to append it? (Y/N): ");
                String toAppend = scanner.nextLine();
                while (!toAppend.equals("Y")&&!toAppend.equals("N")){
                    System.out.println("Please enter Y or N: ");
                    toAppend = scanner.nextLine();
                }
                if(toAppend.equals("Y")) {
                    append = true;
                    System.out.println("APPEND TRUE");
                }
            } else {
                System.out.print("Enter new absolute path to the output file: ");
                output_absolutePath = scanner.nextLine();
                output_file = new File(output_absolutePath);
            }
        }

        //CREATE NEW FILE IF IT DOESN't EXIST
        try {
            if (output_file.createNewFile()) {
                System.out.println("File created: " + output_file.getName());
            } else {
                System.out.println(output_file.getName()+" already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred while creating the file: " + e.getMessage());
            return;
        }

        String[] words_list = readWordsFromFile(word_list_absolutePath);
        int[] counts = new int[words_list.length];
        descSort(words_list);
        System.out.println(Arrays.toString(words_list));

        try (BufferedReader reader = new BufferedReader(new FileReader(text_absolutePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] lineWords = line.split("[\\s.,;:!?]+");
                for (String word : lineWords) {
                    for (int i = 0; i < words_list.length; i++) {
                        if (words_list[i].equalsIgnoreCase(word)) {
                            counts[i]++;
                            break;
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return;
        }

        writeDistinctWordsToFile(output_absolutePath, words_list, append);
        System.out.println("Words       counts");
        for (int i = 0; i < words_list.length; i++) {
            System.out.printf("%s        %d%n", words_list[i], counts[i]);
        }
    }

    private static String[] readWordsFromFile(String path_to_the_File) {
        List<String> words = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(path_to_the_File))) {
            String line;
            while ((line = reader.readLine()) != null) {
                words.add(line.trim());
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            System.exit(1);
        }
        return words.toArray(new String[words.size()]);
    }
    private static void writeDistinctWordsToFile(String path_to_the_file, String[] words_list, boolean append) {
        String[] distinctWords = new String[words_list.length];
        int count = 0;
        for (String word : words_list) {
            boolean found = false;
            for (int i = 0; i < count; i++) {
                if (word.equals(distinctWords[i])) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                distinctWords[count] = word;
                count++;
            }
        }
        try {
            FileWriter fileWriter = new FileWriter(path_to_the_file, append);
            PrintWriter writer = new PrintWriter(fileWriter);
            for (int i = 0; i < count; i++) {
                writer.println(distinctWords[i]);
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
        }
    }

    public static void descSort(String[] words_list) {
        for (int i = 1; i < words_list.length; i++) {
            String temp = words_list[i];
            int j = i - 1;
            while (j >= 0 && words_list[j].compareTo(temp) < 0) {
                words_list[j + 1] = words_list[j];
                j--;
            }
            words_list[j + 1] = temp;
        }
    }
}

