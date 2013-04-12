/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Process;

/**
 *
 * @author andy737-1
 */
public class SelectValues {

    private String id;
    private static SelectValues instance = new SelectValues();

    private SelectValues() {
        id = "";
    }

    public static SelectValues getInstance() {
        return instance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
