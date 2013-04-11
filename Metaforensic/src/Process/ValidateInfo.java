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

import java.io.File;
import java.io.IOException;

/**
 * Clase encargada de validar la información ingresada por el usuario
 *
 * @author andy737-1
 * @version 1.1
 */
public class ValidateInfo {

    private NewValues values;
    private Boolean estado;
    private int error;

    /**
     * Inicializa variables
     */
    public ValidateInfo() {

        values = NewValues.getInstance();
        estado = false;
        error = 0;
    }

    /**
     * Valida las opciones y campos del frame para inciar recolección
     *
     * @throws IOException
     */
    public void ValidateNew() throws IOException {

        if (!"".equals(values.getNombre())) {
            if (!"".equals(values.getAutor())) {
                if (!"".equals(values.getDescripcion())) {
                    if (!"".equals(values.getRuta()) && (new File(values.getRuta()).exists()) && (new File(values.getRuta()).isFile())) {
                        error = 5;
                        estado = true;
                    } else {
                        error = 4;
                        estado = false;
                    }
                } else {
                    error = 3;
                    estado = false;
                }
            } else {
                error = 2;
                estado = false;
            }

        } else {
            error = 1;
            estado = false;
        }

    }

    /**
     *
     * @param values que contiene los valores del objeto Collector
     */
    public void setValues(NewValues values) {
        this.values = values;
    }

    /**
     *
     * @return tipo de error generado
     */
    public int getError() {
        return error;
    }

    /**
     *
     * @return false error, true correcto
     */
    public Boolean getEstado() {
        return estado;
    }
}
