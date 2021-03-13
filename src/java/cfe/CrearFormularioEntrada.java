/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cfe;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/**
 *
 * @author CIELOUNOEE
 */
@WebService(serviceName = "CrearFormularioEntrada")
public class CrearFormularioEntrada {

    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "insertarFormulario")
    public String procesarXml(@WebParam(name = "xml") String xml) throws JDOMException {
        //Se crea un SAXBuilder para poder parsear el archivo
        String rs = "";
        File archivo = null;
        SAXBuilder builder = new SAXBuilder();

        try {
            //Crear un objeto File se encarga de crear o abrir acceso a un archivo que se especifica en su constructor
            archivo = new File("F:/FME/FormularioEntradaTest.xml");
            //Crear objeto FileWriter que sera el que nos ayude a escribir sobre archivo
            FileWriter escribir = new FileWriter(archivo);
            //Escribimos en el archivo con el metodo write 
            escribir.write(xml + "\r\n");
            //Cerramos la conexion
            escribir.close();
        } //Si existe un problema al escribir cae aqui
        catch (Exception e) {
            rs = "<br> ERROR AL CREAR ARCHIVO XML EN DISCO." + e;
        }

        File archivoXml = new File(archivo.getPath());

        try {
            String usuario = "", clave = "", tr = "", nitTercero = "", docTransporte = "", docExportacion = "", regImportacion = "", numFactura = "", observaciones = "", sp = "", codEmpaque = "", codTransporte = "", codBandera = "", paisOrigen = "", paisCompra = "", paisDestino = "", paisProcedencia = "", codArticulo="";
            double pBruto = 0, pNeto = 0, nBultos = 0, tasaCambio = 0, flete = 0, seguro = 0, otrosGastos = 0, cantidad=0, precio=0;

            //Se crea el documento a traves del archivo
            Document document = (Document) builder.build(archivoXml);
            //Se obtiene la raiz 'tables'
            Element rootNode = document.getRootElement();
            //Se obtiene la lista de hijos de la raiz 'tables'
            //List list = rootNode.getChildren("tabla");
            List list = rootNode.getChildren("info");
            //Se recorre la lista de hijos de 'tables'
            rs = rs + "<br> --- " + list.size();

            for (int i = 0; i < list.size(); i++) {
                //Se obtiene el elemento 'tabla'
                Element arbol = (Element) list.get(i);

                //Se obtiene el atributo 'nombre' que esta en el tag 'tabla'
                String rama = arbol.getAttributeValue("nombre");

                System.out.println("Tabla: " + rama);
                rs = rs + "<br> Tabla: " + rama;

                //Se obtiene la lista de hijos del tag 'tabla'
                List lista_campos = arbol.getChildren();

                System.out.println("\tNombre\t\tTipo\t\tValor");

                //Se recorre la lista de campos
                for (int j = 0; j < lista_campos.size(); j++) {
                    //Se obtiene el elemento 'campo'
                    Element campo = (Element) lista_campos.get(j);

                    //Se obtienen los valores que estan entre los tags '<campo></campo>'
                    //Se obtiene el valor que esta entre los tags '<nombre></nombre>'
                    if (rama.equalsIgnoreCase("login")) {
                        usuario = campo.getChildTextTrim("usuario");

                        //Se obtiene el valor que esta entre los tags '<tipo></tipo>'
                        clave = campo.getChildTextTrim("clave");

                        rs = rs + "<br>" + usuario + "\t\t" + clave;
                    }

                    if (rama.equalsIgnoreCase("inicio")) {
                        tr = campo.getChildTextTrim("transaccion");
                        rs = rs + "<br>" + tr;
                    }

                    if (rama.equalsIgnoreCase("general")) {
                        nitTercero = campo.getChildTextTrim("nittercero");
                        docTransporte = campo.getChildTextTrim("doctransporte");
                        docExportacion = campo.getChildTextTrim("docexportacion");
                        regImportacion = campo.getChildTextTrim("regimportacion");
                        numFactura = campo.getChildTextTrim("numfactura");
                        observaciones = campo.getChildTextTrim("observaciones");

                        rs = rs + "<br>" + nitTercero + "\t\t" + docTransporte + "\t\t" + docExportacion + "\t\t" + regImportacion + "\t\t" + numFactura + "\t\t" + observaciones;
                    }

                    if (rama.equalsIgnoreCase("subpartida")) {

                        if (campo.getAttributeValue("nombre").equalsIgnoreCase("infosp")) {
                            sp = campo.getChildTextTrim("codsubpartida");
                            codEmpaque = campo.getChildTextTrim("codempaque");
                            pBruto = Double.parseDouble(campo.getChildTextTrim("pesobruto"));
                            pNeto = Double.parseDouble(campo.getChildTextTrim("pesoneto"));
                            nBultos = Double.parseDouble(campo.getChildTextTrim("nbultos"));
                            codTransporte = campo.getChildTextTrim("codtransporte");
                            codBandera = campo.getChildTextTrim("codbandera");
                            paisOrigen = campo.getChildTextTrim("codpaisorigen");
                            paisCompra = campo.getChildTextTrim("codpaiscompra");
                            paisDestino = campo.getChildTextTrim("codpaisdestino");
                            paisProcedencia = campo.getChildTextTrim("codpaisprocedencia");
                            tasaCambio = Double.parseDouble(campo.getChildTextTrim("tasacambio"));
                            flete = Double.parseDouble(campo.getChildTextTrim("flete"));
                            seguro = Double.parseDouble(campo.getChildTextTrim("seguro"));
                            otrosGastos = Double.parseDouble(campo.getChildTextTrim("otrosgastos"));
                            
                            rs = rs + "<br>" + sp + "\t\t" + codEmpaque + "\t\t" + pBruto + "\t\t" + pNeto + "\t\t" + nBultos + "\t\t" + codTransporte + "\t\t" + codBandera+ "\t\t" + paisOrigen + "\t\t" + paisCompra + "\t\t" + paisDestino + "\t\t" + paisProcedencia + "\t\t" + codTransporte + "\t\t" + tasaCambio + "\t\t" + flete + "\t\t" + seguro + "\t\t" + otrosGastos;
                        }

                        if (campo.getAttributeValue("nombre").equalsIgnoreCase("infoart")) {
                            codArticulo = campo.getChildTextTrim("codarticulo");
                            cantidad = Double.parseDouble(campo.getChildTextTrim("cantidad"));
                            precio = Double.parseDouble(campo.getChildTextTrim("precio"));
                            
                            rs = rs + "<br>" + codArticulo + "\t\t" +cantidad + "\t\t" +precio;
                        }
                        
                        //rs = rs + "<br>" + nitTercero + "\t\t" + docTransporte + "\t\t" + docExportacion + "\t\t" + regImportacion + "\t\t" + numFactura + "\t\t" + observaciones;
                    }
                }
            }
        } catch (IOException | JDOMException io) {
            System.out.println(io.getMessage());
        }

        return rs;
    }
}
