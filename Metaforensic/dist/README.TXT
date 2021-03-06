##Objetivo

La aplicaci�n de software **Metaforensic**, tiene como objetivo realizar una auditor�a forense de metadatos en archivos electr�nicos ofim�ticos y de imagen, mediante la recopilaci�n, an�lisis y presentaci�n de los metadatos ocultos en ficheros electr�nicos, con el fin de esclarecer alg�n suceso que involucre la manipulaci�n (creaci�n, modificaci�n y acceso) de estos, en equipos de c�mputo.

##Problematica

El proyecto de software **Metaforensic** surge a partir del an�lisis de la problem�tica que presentan las organizaciones y particulares, en lo que se refiere a saber c�mo, por qui�n, cu�ndo y porqu� esta siendo manipulada su informaci�n contenida en archivos ofim�ticos y de imagen, que se encuentran alojados dentro de su infraestructura tecnol�gica.

##Funciones

###Recolector:

* Analizar los archivos electr�nicos en busca de metadatos.
* Recolectar los metadatos de los archivos analizados. 
* Respaldar la informaci�n obtenida. 

###Analizador:

* Filtrar y clasificar los metadatos recolectados. 
* Analizar los metadatos filtrados.
* Mostrar los resultados del an�lisis (parcialmente implementado) .
* Generar una l�nea de tiempo que muestre el estado de los archivos analizados con base en los metadatos obtenidos (sin implementar).

##Esquema de funcionamiento general

![image](http://i.imgur.com/7yfWHl4.png)

##Requerimientos de hardware y software

**Metaforensic** se distribuye en dos mod�los por separado **Recolector** y **Analizador**, ambos modulos se pueden encontrar en dos tipos de ejecutables: jar y exe, los ejecutables en formato jar son ideales para funcionar en equipos con bajos recursos de hardware o para recolecci�n de archivos con un peso que no exceda los 68 MB aproximadamente, al igual que para el an�lisis de archivos .afa que no exceda el mismo l�mite respectivamente. En el caso de los ejectutables en formato exe el l�mite de recolecci�n o an�lisis es de archivos que tengan un peso aproximado de hasta 1 GB.

Est� aplicaci�n fue probada en equipos que re�nen las siguientes caracter�sticas: 

###Hardware:

* 1 GB de RAM (ejecutables jar), 3GB de RAM (ejecutables exe).
* Microprocesadores Intel Celeron a 1.7 GHz, AMD E-240 a 1.5 GHz e Intel Pentium 4 HT a 3.2 Ghz.

###Software

* Windows 7 (todas las versiones) 32 y 64 bits, Windows Vista (todas las versiones) 32 y 64 bits y Windows XP Professional SP3 a 32 bits.
* JRE ver. 1.7.0_10 (m�nimo) (http://www.oracle.com/technetwork/es/java/javase/downloads/index.html).
* Parche JCE para JRE (http://www.oracle.com/technetwork/java/javase/downloads/jce-6-download-429243.html).
* Para el uso del analizador se requiere Mysql 5.5.28 (http://dev.mysql.com/downloads/mysql/).

####Notas:

* El analizador se distribuye con el script para la creaci�n de la base de datos en Mysql y un archivo .ini para configurar los parametros de conexi�n a la base de datos. 
* Algunos antivirus y firewalls pueden detectar tanto al recolector como el analizador como falsos positivos.

##Instalaci�n

Para la instalaci�n se deben cubrir los requisitos mencionados en el apartado de [**Requerimientos de hardware y software**](https://github.com/andy737/Metaforensic-Recolector/edit/master/README.md#requerimientos-de-hardware-y-software).

El **Recolector** es una aplicaci�n portable que no requiere de instalaci�n. [Descarga](https://github.com/andy737/Metaforensic-Recolector/)  
El **Analizador** es una aplicaci�n portable sin embargo requiere permanecer en un host que contenga la base de datos creada con el script que se distribuye con el ejecutable. [Descarga](https://github.com/andy737/Metaforensic-Analizador/)

#####Importante

* La instalaci�n y creaci�n de la base de datos, JRE y aplicaci�n del parche JCE no se mencionar� ya que en la red abundan tutoriales sobre el tema.

##Uso

Para hacer uso de la aplicaci�n **Metaforensic [Recolector]**   

1.- Las aplicaciones (.jar y .exe) pueden ser ejecutadas normalmente y tambi�n como administrador si el sistema as� lo requiere (.exe solamente).  

2.- Al ejecutar la aplicaci�n se desplegara una ventana que consta de 5 secciones:

* Configuraci�n de recolecci�n
* Configuraci�n archivo de salida
* Verificaci�n de integridad
* Tipos de archivos 
* Acciones a realizar  
  
3.- Configuraci�n de recolecci�n: Selecci�n de una ruta para la b�squeda y recolecci�n de metadatos en archivos.  

4.- Configuraci�n de archivo: Selecci�n de una ruta para guardar el archivo que contendr� la recolecci�n de metadatos y el archivo log.  

5.- Verificaci�n de integridad: Selecci�n del algoritmo (checksum) para comprobar la integridad de los archivos sometidos a recolecci�n.  

6.- Tipos de archivos: Selecci�n de uno o varios tipos de archivos a buscar dentro de la ruta seleccionada anteriormente, con el fin de recolectar sus metadatos.  

7.- Acciones a realizar: 

* Recolectar: comienza la recolecci�n de metadatos sobre los archivos elegidos y solicitara ingresar un password (para cifrar el archivo final con el prop�sito de preservar la integridad de este).  
* Limpiar: limpia los campos que fueron llenados o seleccionados.  
* Salir: finaliza la ejecuci�n de la aplicaci�n.  

8.- La acci�n de recolecci�n puede durar varios minutos, al t�rmino de esta se mostrara un dialogo que solicita confirmar si se desea guardar o no una copia sin cifrar del archivo generado (opcional).  

9.- Al t�rmino de la recolecci�n se habr�n creado 2 archivos (.log y .afa) o 3 archivos (.log, .afa y S/E), se crear�n 3 archivos solo si se a optado por guardar una copia sin cifrar del archivo generado durante la recolecci�n.  

Nota: La recolecci�n de metadatos puede ser cancelada en cualquier momento, sin que los archivos procesados sufran da�o alguno, esto se realiza pulsando sobre el icono X (rojo), sin embargo en caso de necesitar abortar abruptamente la recolecci�n y finalizar la ejecuci�n de la aplicaci�n al mismo tiempo (opci�n "Panic" icono de ojo), se podr�an provocar da�os sobre los archivos que se encuentren en proceso de recolecci�n.

Para hacer uso de la aplicaci�n **Metaforensic [Analizador]**  

1.- Las aplicaciones (.jar y .exe) pueden ser ejecutadas normalmente y tambi�n como administrador si el sistema as� lo requiere (.exe solamente). 

2.- Al ejecutar la aplicaci�n se desplegara una ventana que solicitar� introducir la contrase�a para el acceso a la base de datos.  

3.- La conexi�n a la base de datos puede tardar un lapso tiempo que var�a dependiendo del sistema en donde se ejecute el servidor Mysql, al establecerse la conexi�n la ventana principal del analizador se desplegara, esta consta de 4 acciones a realizar (Nuevo, Abrir, Consulta y Eliminar), cada una con sus respectivas acciones propias. 

4.- Nuevo: Se debe ingresar todos los datos solicitados y lo m�s importante cargar el archivo (.afa) el cual fue generado con la aplicaci�n **Metaforensic [Recolector]**.  

5.- Abrir: Se podr�n visualizar los datos de proyecto antes de ser abierto (ruta donde se cargo el archivo .afa, el id del proyecto, fecha y hora en que fue cargado, entre otros), al dar clic sobre el bot�n "Abrir" se solicitara la contrase�a con la cual fue cifrado el archivo .afa y ser� desplegada una nueva ventana.    

5.1.- En esa nueva ventana podremos ver todos los archivos recolectados o bien uno en espec�fico/individual, al igual ver los usuarios encontrados por cada archivo (ya sea por creaci�n o modificaci�n), los tiempos de creaci�n, ultima modificaci�n y ultimo acceso, por ultimo se tiene la opci�n de guardar la visualizaci�n de cada an�lisis o filtrado.  

6.- Consulta: Se debe seleccionar un proyecto o la opci�n eventos, si se selecciona la opci�n de mostrar todo y luego la opci�n eventos nos mostrar� los eventos de todos los archivos, si se selecciona la opci�n mostrar todo sin combinar con la opci�n eventos se mostrar�n todos los proyectos almacenados y finalmente si se selecciona un proyecto individual se mostrar�n sus detalles y si se selecciona un proyecto y la opci�n eventos se mostraran los eventos espec�ficos de ese proyecto.  

7.- Eliminar: Esta opci�n elimina el proyecto seleccionado.

##Creadores

* TSU en TIC (�rea Sistemas Inform�ticos) **Andr�s de Jes�s Hern�ndez Mart�nez**    
* TSU en TIC (�rea Sistemas Inform�ticos) **Idania Aquino Cruz**

(Estudiantes de la Licenciatura en Ingenier�a en TIC en la **UTTECAMAC**)

##Creditos
API utilizada para crear y verificar checksum de los archivos analizados por el "Recoletor":  

* Jacksum ver. 1.7.0 (http://www.jonelo.de/java/jacksum/)  
* API bajo la licencia GNU (General Public License)

API utilizada para extracci�n de metadatos de archivos a trav�s del "Recolector":  

* Apache Tika ver. 1.3 (http://tika.apache.org/1.3/index.html)  
* API bajo la licencia Apache License  

##Licencia

La aplicaci�n de software **Metaforensic** es distribuida bajo la licencia GNU (General Public License) ver. 3  
* Para mayor informaci�n sobre la licencia dirigirse a http://www.gnu.org/licenses/gpl.html

##Disclaimer

Tanto el **Analizador** como el **Recolector** que constituyen al proyecto **Metaforensic** contienen algoritmos creados por terceros, por lo cu�l los creadores de dicho proyecto no se hacen responsables por el mal funcionamiento de estos o de la propia aplicaci�n, o por alg�n da�o que pudiera ser causado a los archivos o equipos electr�nicos durante el funcionamiento de las aplicaciones denominadas **Metaforesic [Recolector]** y **Metaforensic [Analizador]**. 

##Bugs

Favor de reportar cua