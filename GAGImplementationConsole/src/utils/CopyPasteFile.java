/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import org.json.JSONException;

/**
 *
 * @author willykk
 */
public class CopyPasteFile {
    
    public static void copyFile (String srcPath, String destPath,String instanceFile) throws IOException {
        
        File src = new File(srcPath);
        new File(destPath).mkdirs();
        
        File dest = new File(destPath+"/"+instanceFile);
        
        
        InputStream in = new BufferedInputStream(new FileInputStream(src));
        OutputStream out = new BufferedOutputStream(new FileOutputStream(dest));
        byte[] buf = new byte[4096];
        int n;
        while ((n=in.read(buf, 0, buf.length)) > 0)
            out.write(buf, 0, n);

        in.close();
        out.close();
    }
    
    public static void writeAtTheEnd(String file, String line) throws IOException{
        FileWriter writer = new FileWriter(file,true);
        writer.write(line);
        writer.close();
    }
    
    public static void main(String[] arg) throws JSONException, IOException{
        /*try {
            CopyPasteFile.copyFile("/home/willykk/Fichier.xml", "/home/willykk/serviceAvailable/","");
        } catch (IOException ex) {
            Logger.getLogger(CopyPasteFile.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        
        //CopyPasteFile.writeAtTheEnd("/home/willykk/test.txt", "lool");
        String input = "";
        while(input.equals("")){
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            input = br.readLine();
            System.out.println(input.length()+" humm "+input.equals(""));
        }
    }
    
}
