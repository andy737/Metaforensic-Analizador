/*
 * *****************************************************************************
 *    
 * Metaforensic version 1.0 - Análisis forense de metadatos en archivos
 * electrónicos Copyright (C) 2012-2013 TSU. Andrés de Jesús Hernández Martínez,
 * TSU. Idania Aquino Cruz, All Rights Reserved, https://github.com/andy737   
 * 
 * This file is part of Metaforensic.
 *
 * Metaforensic is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Metaforensic is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Metaforensic.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * E-mail: andy1818ster@gmail.com
 * 
 * *****************************************************************************
 */
package Process;

/**
 * Resguardo del id de proyecto para diferentes operaciones
 *
 * @author andy737-1
 * @version 1.0
 */
public class IdVal {

    private String id;
    private static IdVal instance = new IdVal();

    private IdVal() {
        id = "";
    }

    /**
     *
     * @return la instancia de esta clase
     */
    public static IdVal getInstance() {
        return instance;
    }

    /**
     *
     * @return id de proyecto
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id de proyecto
     */
    public void setId(String id) {
        this.id = id;
    }
}
