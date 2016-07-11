/*
 * table.java : Класс для работы с SQL таблицей. 
 * 
 * В качестве базы данных используется HSQLDB — реляционная СУБД с открытым 
 * исходным кодом. 
 */

package trans;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class table {
	private static Connection connection = null;
	
	/* load_driver(): Загрузка драйвера для работы с СУБД */
	public static void load_driver() {
		try {
			Class.forName("org.hsqldb.jdbcDriver");
		} catch (ClassNotFoundException e) {
			System.out.println("Драйвер не найден!!!");
			e.printStackTrace();
		}
	}
	
	/* db_connect(): Подключение к базе данных */
	public static void db_connect() {
		try {
			String path     = "dbreq/";
			String dbname   = "requests";
			String strConn  = "jdbc:hsqldb:file:" + path + dbname;
			String login 	= "joe";
			String password = "password";
		
			connection = DriverManager.getConnection(strConn, login, password);
		} catch (SQLException e) {
			System.out.println("Соединение не установлено!!!");
			e.printStackTrace();
		}
	}
	
	/* db_disconnect(): Отключение от базы данных */
	public static void db_disconnect() {
		try {
			Statement stm = connection.createStatement();
			String    sql = "SHUTDOWN";
			
			stm.execute(sql);  
		} catch (SQLException e) {
			e.printStackTrace();
		}  
	}
	
	/* tlb_create(): Создание таблици в базе данных */
	public static void tbl_create() {
		try {
			Statement stm = connection.createStatement();   
			String    sql = "CREATE TABLE logTbl ("
					+ "id      IDENTITY, "
					+ "date    VARCHAR(20), "
					+ "fromlng VARCHAR(10), "
					+ "tolng   VARCHAR(10), "
					+ "intxt   VARCHAR(20), "
					+ "outtxt  VARCHAR(20))";  
			
			stm.executeUpdate(sql);
		} catch (SQLException e) {
		}
	}
	
	/* tlb_add(): Добавление данных в таблицу */
	public static void tbl_add(String _flng, String _tlng, String _itxt,
			String _otxt) {
		Date             date  = new Date();
		SimpleDateFormat dform = new SimpleDateFormat("dd.MM.yyyy-hh:mm");
				
		try {
			Statement stm = connection.createStatement();
			String    sql = "INSERT INTO logTbl "
					+ "(date, fromlng, tolng, intxt, outtxt) "
					+ "VALUES('" + dform.format(date).toString() + "',"
					+        "'" + _flng                         + "',"
					+        "'" + _tlng                         + "',"
					+        "'" + _itxt                         + "',"
					+        "'" + _otxt                         + "')";

			stm.executeUpdate(sql);
		} catch (SQLException e) {   
			e.printStackTrace();
		}		
	}
	
	/* tbl_print(): Вывод таблицы данных */
	public static void tbl_print() {
		try {
			Statement stm  = connection.createStatement();
			String    sql  = "SELECT id, date, fromlng, tolng, intxt, outtxt"
					+ " FROM logTbl";
			ResultSet rSet = stm.executeQuery(sql);
			    
			while (rSet.next()) {
				System.out.println(rSet.getInt(1)    + " "
								 + rSet.getString(2) + " "
								 + rSet.getString(3) + " "
								 + rSet.getString(4) + " "
								 + rSet.getString(5) + " "
								 + rSet.getString(6) + " ");
			}
		} catch (SQLException e) {   
			e.printStackTrace();
		}     
	}
}
