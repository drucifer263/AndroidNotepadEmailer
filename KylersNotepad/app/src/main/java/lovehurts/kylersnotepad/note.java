package lovehurts.kylersnotepad;

/**
 * Created by Kyler on 7/10/2016.
 */
public class note {
    private String subject;
    private String notes;

    public note(){

    }
    public note(String sub, String not){
        this.subject = sub;
        this.notes = not;
    }
    public void setNote(String n){
        this.notes = n;
    }
    public String getNote(){
        return this.notes;
    }

    public void setSubject(String s){
        this.subject = s;
    }
    public String getSubject(){
        return this.subject;
    }
}
