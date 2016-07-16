package kz.stqa.pft.mantis.model;

/**
 * Created by xeniya on 7/16/16.
 */
public class MailMessage {
    public String to;
    public String text;

    public MailMessage(String to, String text){
        this.to = to;
        this.text = text;
    }
}
