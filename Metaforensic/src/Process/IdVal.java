/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Process;

/**
 *
 * @author andy737-1
 */
public class IdVal {

    private String id;
    private static IdVal instance = new IdVal();

    private IdVal() {
        id = "";
    }

    public static IdVal getInstance() {
        return instance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
