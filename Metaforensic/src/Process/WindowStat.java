/*
 * *****************************************************************************
 *    
 * Metaforensic version 1.1 - Análisis forense de metadatos en archivos
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
 * Estado del archivo analizado (abierto o cerrado)
 *
 * @author andy737-1
 * @version 1.1
 */
public class WindowStat {

    private static WindowStat instance = new WindowStat();
    private String estado;

    private WindowStat() {
        estado = "";
    }

    /**
     *
     * @return la instancia de esta clase
     */
    public static WindowStat getWinInstance() {
        return instance;
    }

    /**
     *
     * @param estado del archivo abierto o cerrado
     */
    public void setEstadoId(String estado) {
        this.estado = estado;
    }

    /**
     *
     * @return estado del archivo
     */
    public String getEstadoID() {
        return estado;
    }
}
