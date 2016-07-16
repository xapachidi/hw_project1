package kz.stqa.pft.sandbox;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xeniya on 6/10/16.
 */
public class Collections {

    public static void main(String[] args){
        String[] langs = new String[4];
        langs[0] = "Java";
        langs[1] = "C#";
        langs[2] = "Pyton";
        langs[3] = "PHP";

        List<String> languages = new ArrayList<String>();
        languages.add("Java");
        languages.add("C#");
        languages.add("Pyton");



        //можно и так написать
        //String[] langs = ("Java", "C#", "Pyton", "PHP");
        //List<String> languages =Arrays.asList("Java", "C#", "Pyton", "PHP");

       /* for (int i = 0; i<langs.length; i++){
            System.out.println("Я хочу выучить " + langs[i]);
        }

        for (int i = 0; i<languages.size(); i++){
            System.out.println("Я хочу выучить " + languages.get(i));
        }
        */
        for (String l : langs) {
            System.out.println("Я хочу выучить " + l);
        }

        for (String l : languages) {
            System.out.println("Я хочу выучить " + l);
        }

    }
}
