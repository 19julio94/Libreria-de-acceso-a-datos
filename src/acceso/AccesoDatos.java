package acceso;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author manugallegomanuel88
 */
public class AccesoDatos {
    private Connection conexion = null;
    /**
     * Hay que darle de valor la direccion de nuestro host.
     */
    private String host;
    /**
     * Hay que darle de valor el nombre de la BD.
     */
    private String baseDatos;
    /**
     * Hay que darle de valor un nombre de usuario de la BD.
     */
    private String user;
    /**
     * Haay que darle de valor el passwor del usuario.
     */
    private String pass;

    /**
     * Hay que introducir los parametros que vamos necesarios para hacer la conexión con la base de datos.
     * @param host Hay que darle de valor la direccion de nuestro host.
     * @param baseDatos  Hay que darle de valor el nombre de la BD.
     * @param user Hay que darle de valor un nombre de usuario de la BD.
     * @param pass  Haay que darle de valor el passwor del usuario.
     */
    public AccesoDatos(String host, String baseDatos, String user, String pass) {
        this.host = host;
        this.baseDatos = baseDatos;
        this.user = user;
        this.pass = pass;
    }

    /**
     * Crea una consulta para hacer un delete con los parametros que se le introduce.
     * @param deleteFrom Nombre de la tabla en la BD donde queremos hacer el delete.
     * @param condicion Campo por el que buscamos para borrar una fila. Preferible que sea una primary key.
     * @param vCondicion Valor que le daremos a "condicion".
     */
    public void delete(String deleteFrom, String condicion, String vCondicion){
        try {
            conexion = DriverManager.getConnection("jdbc:mysql://"+host+"/"+baseDatos, user, pass);
            String query = ("delete from "+deleteFrom+" where "+condicion+"='"+vCondicion+"'");
            System.out.println(query);
            PreparedStatement preparedStmt = conexion.prepareStatement(query);
            preparedStmt.execute();
            System.out.println("Borrado realizado");
        } catch (SQLException ex) {
            System.out.println("Error de borrado: " + ex.getMessage());
        }
    }
    /**
     * Crea una consulta para hacer un insert con los parametros que se le introduce. 
     * El primer parametro es el nombre de la tabla de la BD sobre la que queremos hacer el insert. 
     * El primer String "campo" es el nombre del campo que queremos introducir en la tabla de la BD, el segundo String "campo" es el valor del anterior String "campo" que introduciremos.
     * Por cada String "campo" que añadiremos tendremos que añladir otro mas con su valor.
     * @param insertFrom Nombre de la tabla en la BD donde queremos hacer el insert.
     * @param campo Campos que añadiremos a la tabla de la BD. Primer String nombre del campo, segundo String valor del campo.
     */
    public void insert (String insertFrom, String... campo) {
        try {
            conexion = DriverManager.getConnection("jdbc:mysql://"+host+"/"+baseDatos, user, pass);
            String query = " insert into " + insertFrom + " (" +campo[0];
            for (int i=2; i<campo.length; i=i+2){
                query+= ", " + campo[i];
            }
            query += ") values (?";
            for (int i=2; i<campo.length; i=i+2){
                query += ", ?";
            }
            query += ")";
            PreparedStatement preparedStmt = conexion.prepareStatement(query);
            int a=1;
            for(int i=1; i<campo.length; i=i+2){
                preparedStmt.setString (a, campo[i]);
                a++;
            }
            preparedStmt.execute();
            System.out.println("Insercion realizada");
        } catch (SQLException ex) {
            System.out.println("Error de inserción: " + ex.getMessage());
        }
    }
}
