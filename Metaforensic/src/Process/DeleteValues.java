/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Process;

/**
 *
 * @author andy737-1
 */
public class DeleteValues {

    private String id;
    private static DeleteValues instance = new DeleteValues();

    private DeleteValues() {
        id = "";
    }

    public static DeleteValues getInstance() {
        return instance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
