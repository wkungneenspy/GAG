/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package configFile;

/**
 *
 * @author willyk
 */
public class GagconfigFile {
    
    String openJMSHOME;
    
    String configPath;
    
    String openJMSConfigPath;
    
    String schemaExecution;
    
    String instanceFolder ;
    
    public String getOpenJMSHOME() {
        return openJMSHOME;
    }

    public void setOpenJMSHOME(String openJMSHOME) {
        this.openJMSHOME = openJMSHOME;
    }

    public String getConfigPath() {
        return configPath;
    }

    public void setConfigPath(String configPath) {
        this.configPath = configPath;
    }

    public String getOpenJMSConfigPath() {
        return openJMSConfigPath;
    }

    public void setOpenJMSConfigPath(String openJMSConfigPath) {
        this.openJMSConfigPath = openJMSConfigPath;
    }

    public String getSchemaExecution() {
        return schemaExecution;
    }

    public void setSchemaExecution(String schemaExecution) {
        this.schemaExecution = schemaExecution;
    }

    public String getInstanceFolder() {
        return instanceFolder;
    }

    public void setInstanceFolder(String instanceFolder) {
        this.instanceFolder = instanceFolder;
    }
}
