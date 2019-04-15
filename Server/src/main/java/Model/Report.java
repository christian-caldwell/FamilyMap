package Model;

import java.io.Serializable;

public class Report implements Serializable {
    private boolean status;
    private String message;

    public Report(String message, boolean status)
    {
        this.status = status;
        this.message = message;
    }

    public boolean getStatus() {
        return this.status;
    }

    public String getMessage() {
        return this.message;
    }
}
