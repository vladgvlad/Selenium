# Selenium
Este proyecto llamado Selenium automatiza la descarga de los apuntes desde la página https://nachoiborraies.github.io/java/ utilizando Selenium y Apache Commons IO. Al principio se idetifican los enlaces posibles para descargar dentro del sitio web y se procede a descargar archivos o guardar el HTML. 


Se utilizan las siguientes librerías:
- Selenium WebDriver: para controlar el navegador Chrome de manera automatizada y para extraer elementos de la página.
- Apache Commons IO / FileUtils: facilita la descarga y la escritura del contenido de la página.
- Java estándar: se usan clases (java.io.FIle; java.net.URL; java.util.List) para gestionar los archivos, urls y la estructura de datos.


Funcionamiento del código:
- Inicialización del WebDriver: se configura e inicia ChromeDriver y se accede a la URL base.
- Extracción de enlaces: se localizan todos los <a> y se guardan sus "href" en una lista para evitar problemas.
- Descarga o guardado: si el archivo tiene una extensión (pdf, zip, jpg, png, txt, exe, mp4, mp3, java, md) se descarga directamente. Si el enlace no es un archivo anterior, se obtiene el código fuente (HTML) y se guarda localmente.
- Manejo de errores: se usan pausas como Thread.sleep para cargar las páginas. Además se manejan excepciones como IOException y InterruptedException para prevenir fallos al ejecutar el programa.
- Finalización del programa: se cierran todas las instancias del navegador al descargarse todas las descargas.


Archivos descargados:
- Todos los archivos que se descargan de la página web se guardan en la carpeta llamada "downloads" que se crea automaticamente al ejecutarse el Main. La carpeta contiene copias locales en formato .html de las páginas web.


