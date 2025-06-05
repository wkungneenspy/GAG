/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import openjms.Start;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author willyk
 */
public class TestExclusionMutuelle {
    
    // 
    
    public static void main (String[] arg){
        try {
            String x = FileUtils.readFileToString(new File(Start.gagconfigFile.getMutexFile()+"toto.txt"));
            
            while(x.length() == 0){
                
                System.out.println("Je dors un peu");
                Thread.sleep(3000);
                
                x = FileUtils.readFileToString(new File(Start.gagconfigFile.getMutexFile()+"toto.txt"));
            }
            
            System.out.println("x = "+x.length());
          
            FileUtils.writeStringToFile(new File(Start.gagconfigFile.getMutexFile()+"toto.txt"), "");
            
        } catch (IOException ex) {
            Logger.getLogger(TestExclusionMutuelle.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(TestExclusionMutuelle.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    
}
