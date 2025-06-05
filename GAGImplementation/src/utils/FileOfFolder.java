/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import openjms.Start;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author willykk
 */
public class FileOfFolder {
    /* Voila une methode qui te permettra de dresser la liste de fichier inclus dans un
     repertoire, et ceci en recurence :
     */

    public static ArrayList scanDir(String theDirectory) throws IOException {
        //System.out.println("scanDir "+theDirectory);
        File currDir = new File(theDirectory);
        ArrayList finalListFiles = new ArrayList();
        // Return null if not a directory
        if (currDir.exists() && !currDir.isDirectory()) {
            return null;
        }

        String[] fileList = currDir.list();

        for (int i = 0; i < fileList.length; i++) {
            Object temp = scanDir(theDirectory + "/"/*File.separator*/ + fileList[i]);
            if (temp == null) // --- Add the path of the current file
            {
                finalListFiles.add(theDirectory + "/"/*File.separator*/ + fileList[i]);
            } else
                        // --- Add the array list of the current directory
                        ;
        }

        // --- Finally, return the arrayList
        return finalListFiles;
    }

    public static String lireValeur(File file) {
        try {
            String x = FileUtils.readFileToString(file);

            while (x.length() == 0) {

                System.out.println("Je dors un peu");
                Thread.sleep(4000);

                x = FileUtils.readFileToString(file);
            }

            System.out.println("x = " + x);

            FileUtils.writeStringToFile(file, "");
            //Thread.sleep(3000);

            return x.replaceAll("(\\r|\\n)", "");
        } catch (IOException ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        } catch (InterruptedException ex) {
            Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws IOException {
        ArrayList<String> a = (ArrayList<String>) FileOfFolder.scanDir("/home/willykk/Bureau/OpenJMS/build/classes/openjms");

        for (int i = 0; i < a.size(); i++) {
            System.out.println(" " + a.get(i));
        }
    }
}
