/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openjms;

/**
 *
 * @author willyk
 */
public class OpenJMS {

    public static void main(String[] arg) {
        try{
            //Vérification des queueues avant de lancer un nouveau truc... et les scénarios en cours
            //avant de lancer un truc

            //1. Dans l'artéfact aller sur le service encours de rafinement...
            //2. Ecouter la queue la plus recente si les elements en cours attendent les ouput
            //3. demander les input et envoyer si les element en cours bloque sur les inputs

            String GagconfigFilePath = "C:/gag/GagconfigFile.json";

            System.out.println("Length  " + arg.length);

            if (arg.length == 1) {
                GagconfigFilePath = arg[0];
            }

            Start cont = new Start();
            cont.start(GagconfigFilePath);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
