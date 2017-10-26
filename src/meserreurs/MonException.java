package meserreurs;

import javax.ws.rs.Produces;

public class MonException  extends Exception  implements java.io.Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;
    private String type;

    public MonException() {
    }

    public MonException( String libelle,  String type) {
        this.message = libelle;
        this.type = type;
    }

    public MonException( String libelle) {
        this.message = libelle;
       
    }

    
    public String getMessage() {
        return message;
    }

    public void setMessage(java.lang.String libelle) {
        this.message = libelle;
    }
    
    
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
}
