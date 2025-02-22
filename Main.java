package org.example;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;


import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        String baseUrl = "https://nachoiborraies.github.io/java/";
        driver.get(baseUrl);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<WebElement> allLinks = driver.findElements(By.tagName("a"));

        List<String> hrefs = new ArrayList<>();
        for (WebElement link : allLinks) {
            String href = link.getAttribute("href");
            if (href != null && !href.isEmpty()) {
                hrefs.add(href);
            }
        }
        System.out.println("Se encontraron " + hrefs.size() + " enlaces con href.");

        File downloadDir = new File("downloads");
        if (!downloadDir.exists()) {
            downloadDir.mkdir();
        }

        String downloadablePattern = "(?i).*\\.(pdf|zip|jpg|png|txt|exe|mp4|mp3|java|md)";

        for (String href : hrefs) {
            if (!href.startsWith("http")) {
                href = baseUrl + href;
            }

            if (href.matches(downloadablePattern)) {
                String fileName = href.substring(href.lastIndexOf("/") + 1);
                if (fileName.isEmpty()) {
                    fileName = "archivo_sin_nombre";
                }
                System.out.println("Descargando archivo directo: " + fileName);
                downloadFile(href, "downloads/" + fileName);

            } else {
                System.out.println("Guardando contenido de la página: " + href);
                driver.navigate().to(href);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                String pageSource = driver.getPageSource();
                String fileName = href.substring(href.lastIndexOf("/") + 1);
                if (fileName.isEmpty()) {
                    fileName = "index.html";
                } else {
                    if (!fileName.contains(".")) {
                        fileName += ".html";
                    }
                }

                File outFile = new File(downloadDir, fileName);
                try {
                    FileUtils.writeStringToFile(outFile, pageSource, StandardCharsets.UTF_8);
                    System.out.println("Página guardada como: " + outFile.getAbsolutePath());
                } catch (IOException e) {
                    System.err.println("Error guardando la página: " + href);
                    e.printStackTrace();
                }

                driver.navigate().to(baseUrl);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        driver.quit();
    }

    public static void downloadFile(String fileURL, String filePath) {
        try {
            FileUtils.copyURLToFile(new URL(fileURL), new File(filePath));
            System.out.println("Descargado: " + filePath);
        } catch (IOException e) {
            System.err.println("Error al descargar el archivo: " + fileURL);
            e.printStackTrace();
        }
    }
}
