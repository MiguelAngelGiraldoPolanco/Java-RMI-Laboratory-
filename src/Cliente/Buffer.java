package Cliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Buffer {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static String readLine(String prompt) {
        System.out.print(prompt);
        try {
            return reader.readLine();
        } catch (IOException e) {
            System.out.println("Error leyendo entrada: " + e.getMessage());
            return "";
        }
    }

    public static int readInt(String prompt) {
        while (true) {
            String line = readLine(prompt);
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Entrada no válida. Intenta de nuevo.");
            }
        }
    }
}
