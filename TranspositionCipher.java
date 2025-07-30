import java.util.*;

public class TranspositionCipher {

    // Helper to convert string to matrix row-wise
    public static char[][] stringToMatrix(String text, int rows, int cols) {
        char[][] matrix = new char[rows][cols];
        int k = 0;
        for (int i = 0; i < rows && k < text.length(); i++) {
            for (int j = 0; j < cols && k < text.length(); j++) {
                matrix[i][j] = text.charAt(k++);
            }
        }
        return matrix;
    }

    public static String matrixToString(char[][] matrix, int rows, int cols) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                sb.append(matrix[i][j]);
        return sb.toString();
    }

    // Encryption for Row Transposition
    public static String encryptRow(String plaintext, int[] rowKey) {
        int rows = rowKey.length;
        int cols = (int) Math.ceil((double) plaintext.length() / rows);
        plaintext = pad(plaintext, rows * cols);

        char[][] matrix = stringToMatrix(plaintext, rows, cols);

        char[][] cipher = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            cipher[i] = matrix[rowKey[i] - 1];
        }
        return matrixToString(cipher, rows, cols);
    }

    // Decryption for Row Transposition
    public static String decryptRow(String ciphertext, int[] rowKey) {
        int rows = rowKey.length;
        int cols = ciphertext.length() / rows;

        char[][] matrix = stringToMatrix(ciphertext, rows, cols);

        char[][] plain = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            plain[rowKey[i] - 1] = matrix[i];
        }
        return matrixToString(plain, rows, cols);
    }

    // Encryption for Column Transposition
    public static String encryptColumn(String plaintext, int[] colKey) {
        int cols = colKey.length;
        int rows = (int) Math.ceil((double) plaintext.length() / cols);
        plaintext = pad(plaintext, rows * cols);

        char[][] matrix = stringToMatrix(plaintext, rows, cols);

        char[][] cipher = new char[rows][cols];
        for (int j = 0; j < cols; j++) {
            int colIndex = colKey[j] - 1;
            for (int i = 0; i < rows; i++) {
                cipher[i][j] = matrix[i][colIndex];
            }
        }
        return matrixToString(cipher, rows, cols);
    }

    // Decryption for Column Transposition
    public static String decryptColumn(String ciphertext, int[] colKey) {
        int cols = colKey.length;
        int rows = ciphertext.length() / cols;

        char[][] matrix = stringToMatrix(ciphertext, rows, cols);

        char[][] plain = new char[rows][cols];
        for (int j = 0; j < cols; j++) {
            int colIndex = colKey[j] - 1;
            for (int i = 0; i < rows; i++) {
                plain[i][colIndex] = matrix[i][j];
            }
        }
        return matrixToString(plain, rows, cols);
    }

    // Padding
    public static String pad(String text, int length) {
        while (text.length() < length) {
            text += 'X';
        }
        return text;
    }

    // Menu
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Transposition Cipher Menu ---");
            System.out.println("1. Encrypt using Row Transposition");
            System.out.println("2. Decrypt using Row Transposition");
            System.out.println("3. Encrypt using Column Transposition");
            System.out.println("4. Decrypt using Column Transposition");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); // clear newline

            if (choice == 5) break;

            System.out.print("Enter message: ");
            String msg = sc.nextLine().replaceAll("\\s+", "").toUpperCase();

            System.out.print("Enter key (space-separated integers): ");
            String[] keyStr = sc.nextLine().split(" ");
            int[] key = Arrays.stream(keyStr).mapToInt(Integer::parseInt).toArray();

            String result = "";
            switch (choice) {
                case 1:
                    result = encryptRow(msg, key);
                    break;
                case 2:
                    result = decryptRow(msg, key);
                    break;
                case 3:
                    result = encryptColumn(msg, key);
                    break;
                case 4:
                    result = decryptColumn(msg, key);
                    break;
                default:
                    System.out.println("Invalid choice!");
            }

            System.out.println("Result: " + result);
        }
        sc.close();
    }
}
