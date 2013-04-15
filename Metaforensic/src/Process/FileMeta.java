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
 * Objeto de archivos recolectados
 *
 * @author andy737-1
 * @version 1.0
 */
public class FileMeta {

    private String nombre;
    private int posicion;

    /**
     * Constructor inicia variables
     */
    public FileMeta() {
        nombre = "";
        posicion = 0;
    }

    /**
     *
     * @param nombre del archivo recolectado
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     *
     * @return archivo
     */
    public String getNombre() {
        return nombre;
    }

    /**
     *
     * @param posicion en el archivo .afa
     */
    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    /**
     *
     * @return posicion
     */
    public int getPosicion() {
        return posicion;
    }
}
