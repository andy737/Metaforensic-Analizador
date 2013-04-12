/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Process;

/**
 *
 * @author andy737-1
 */
public class OpenValues {

    private String id;
    private static OpenValues instance = new OpenValues();

    private OpenValues() {
        id = "";
    }

    public static OpenValues getInstance() {
        return instance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}