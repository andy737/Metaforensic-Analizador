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
 * Objeto para procesar proyecto
 *
 * @author andy737-1
 * @version 1.0
 */
public class NewValues {

    private static NewValues instance = new NewValues();
    private String autor;
    private String descripcion;
    private String nombre;
    private String ruta;

    private NewValues() {
    }

    /**
     *
     * @return instacia
     */
    public static NewValues getInstance() {
        return instance;
    }

    /**
     *
     * @param autor del proyecto
     */
    public void setAutor(String autor) {
        this.autor = autor;
    }

    /**
     *
     * @param descripcion del proyecto
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     *
     * @param nombre del proyecto
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     *
     * @param ruta del archivo .afa
     */
    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    /**
     *
     * @return autor
     */
    public String getAutor() {
        return autor;
    }

    /**
     *
     * @return descripción
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     *
     * @return nombre del proyecto
     */
    public String getNombre() {
        return nombre;
    }

    /**
     *
     * @return ruta del archivo .afa
     */
    public String getRuta() {
        return ruta;
    }
}
