/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;

/**
 * Loads configuration from a JSON file. 
 * @author 
 */
public class LConfigLoader {
    /**
     * Loads configuration from a JSON file. To use this static member, 
     * 1. create your class that will hold the configuration
     * 2. use this code example:
     *      MyConfigClass cfg;
     *      cfg=getConfigFromJSON(MyConfigClass.class,configuration.json);        
     * @param user the class for the configuration. The class members should match the fields in the JSON. 
     * As well, the class should have getters and setters with Jackson annotations.
     * @param jsonFile the path to the JSON file containing the configuration
     * @return an object of the class user, filled with configuration information
     * @throws BadJSONRequestException
     * @throws IOException 
     */
    public static <T> T getConfigFromJSON(Class<T> user, String jsonFile) throws Exception, IOException{
         T myobj;
            try {             
                ObjectMapper mapper;
                mapper  = new ObjectMapper();
                mapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                
                System.out.println("Loading "+ jsonFile + "....");
                
                myobj = (T) mapper.readValue(new File(jsonFile), user);
                
                return myobj;                                
            }catch(JsonParseException e){
                e.printStackTrace(System.out);  
                throw new Exception("Parser failed. Couldn't load configuration from "+jsonFile);
            }catch (JsonMappingException e) {
		e.printStackTrace(System.out);
                //throw new BadJSONRequestException("Mapping failed");
                // throw an IO Exception because mapping error might be caused
                // by IO errors, like blocked connections. 
                throw new IOException();
            }catch (IOException ioe) { 
                System.err.println(ioe);                  
                throw ioe;                
            }                      
    }
}
