package pe.soaint.prueba.problem;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Demo {
	private static boolean logToFile;
    private static boolean logToConsole;
    private static boolean logMessage;
    private static boolean logWarning;
    private static boolean logError;
    private static boolean logToDatabase;
    
    //ESTA VARIABLE NO SE ESTA UTILIZANDO
    private boolean initialized;
    //EL MAP NO TIENE LA ESPECIFICACION DE TIPO EN <>
    private static Map dbParams;
        
    private static Logger logger;

    /*
    	SI SE ESTA USANDO ESTA CLASE COMO UTILITARIO Y TODOS LOS METODOS SON ESTADICOS
    	EL CONSTRUCTIOR DEBE SER PRIVADO Y NO DEBE INSTANCIARSE
    */
    public Demo(boolean logToFileParam, boolean logToConsoleParam, boolean logToDatabaseParam,
                boolean logMessageParam, boolean logWarningParam, boolean logErrorParam, Map dbParamsMap) {
        logger = Logger.getLogger("MyLog");
        logError = logErrorParam;
        logMessage = logMessageParam;
        logWarning = logWarningParam;
        logToDatabase = logToDatabaseParam;
        logToFile = logToFileParam;
        logToConsole = logToConsoleParam;
        dbParams = dbParamsMap;
    }

    //EL metodo "LogMessage" debe empezar con miniscula "logMessage"
    //Los boolean "message, warning, error" se deben convertir en en un solo parametro Integer, para un mejor control
    //USAR UN DTO
    public static void LogMessage(String messageText, boolean message, boolean warning, boolean error) throws Exception {
        
    	//EL TRIM PUEDE GENERAR ERROR SI LLEGA NULO messageText
    	messageText.trim();
        if (messageText == null || messageText.length() == 0) {
            return;
        }
        if (!logToConsole && !logToFile && !logToDatabase) {
        	//USER EXCEPTION PROPIOS PARA EL PROYECTO
            throw new Exception("Invalid configuration");
        }
        if ((!logError && !logMessage && !logWarning) || (!message && !warning && !error)) {
            throw new Exception("Error or Warning or Message must be specified");
        }

        //LA CONEXION A LA DB DEBE ESTAR EN UNA CLASE
        Connection connection = null;
        
        //SI SE USA UN FRAMEWORK COMO SPRING NO ES NECESARIO CREAR LA LECTURA DEL PROPERTIES
        Properties connectionProps = new Properties();
        connectionProps.put("user", dbParams.get("userName"));
        connectionProps.put("password", dbParams.get("password"));

        //PARA EL MANEJO DE STRING CON DEMASIADAS CONCATENACIONES SE USA StringBuilder
        connection = DriverManager.getConnection("jdbc:" + dbParams.get("dbms") + "://" + dbParams.get("serverName")
                + ":" + dbParams.get("portNumber") + "/", connectionProps);

        
        //PARA ESTE TIPO DE CASOS ES MEJOR MAPEAR LAS VARIABLES EN UN ENUM
        int t = 0;
        if (message && logMessage) {
            t = 1;
        }

        if (error && logError) {
            t = 2;
        }

        if (warning && logWarning) {
            t = 3;
        }
        //ES MEJOR USAR prepareStatement PARA EVITAR LA INYECCION SQL
        Statement stmt = connection.createStatement();

        String l = null;
        File logFile = new File(dbParams.get("logFileFolder") + "/logFile.txt");
        if (!logFile.exists()) {
            logFile.createNewFile();
        }

        FileHandler fh = new FileHandler(dbParams.get("logFileFolder") + "/logFile.txt");
        ConsoleHandler ch = new ConsoleHandler();

        if (error && logError) {
        	//Usar LocalDate en vez de Date
            l = l + "error " + DateFormat.getDateInstance(DateFormat.LONG).format(new Date()) + messageText;
        }

        if (warning && logWarning) {
            l = l + "warning " +DateFormat.getDateInstance(DateFormat.LONG).format(new Date()) + messageText;
        }

        if (message && logMessage) {
            l = l + "message " +DateFormat.getDateInstance(DateFormat.LONG).format(new Date()) + messageText;
        }

        if(logToFile) {
            logger.addHandler(fh);
            logger.log(Level.INFO, messageText);
        }

        if(logToConsole) {
            logger.addHandler(ch);
            logger.log(Level.INFO, messageText);
        }

        if(logToDatabase) {
        	//PARA EL MANEJO DE STRING CON DEMASIADAS CONCATENACIONES SE USA StringBuilder
            stmt.executeUpdate("insert into Log_Values('" + message + "', " + String.valueOf(t) + ")");
        }
    }
}
