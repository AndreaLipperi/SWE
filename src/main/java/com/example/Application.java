/*package com.example;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

public class Application {

    public static void main(String[] args) {
        String filePath = "src/main/webapp/views/account_pages/index.html";
        File htmlFile = new File(filePath);

        try {
            // Verifica se il Desktop è supportato
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();

                // Verifica se il file esiste
                if (htmlFile.exists()) {
                    desktop.browse(htmlFile.toURI());
                } else {
                    System.out.println("Il file HTML non esiste: " + filePath);
                }
            } else {
                System.out.println("Il supporto per Desktop non è disponibile.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
*/