package br.com.bbtcc.model.jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Classe de conexão com o banco de dados via JDBC
 *
 */

public class ConnectionFactory {
    /**
     * Parâmetros da conexão que são carregados posteriormente via leitura do arquivo de propriedades
     */
    private static String url;
    private static String password;
    private static String user;
    private static String driver;
    private static ConnectionFactory instance = null;

    private ConnectionFactory() {
    }

    public static ConnectionFactory getInstance() {
        if (instance == null) {
            synchronized (ConnectionFactory.class) {
                if (instance == null) {
                    instance = new ConnectionFactory();
                }
            }
        }
        return instance;
    }


    /**
     * Log
     */
    private static final Logger logger = Logger.getLogger(ConnectionFactory.class.getName());

    /**
     * Leitura do arquivo de properiedades (config.properties) contido em src/main/resources
     * Objetivo é evitar adicionar informações de banco (login, senha) dentro do código
     */
    static {

        Properties properties = new Properties();
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties");
        try {
            properties.load(inputStream);
            inputStream.close();
            url = properties.getProperty("database.url");
            user = properties.getProperty("database.user");
            password = properties.getProperty("database.password");
            driver = properties.getProperty("database.driver");
        } catch (IOException e) {
            logger.severe("Falha ao carregar arquivo de configuração");
        }

    }

    /**
     * Recupera conexão com o banco de dados.
     * O método é estático para não criar uma conexão a cada vez que é utilizado
     * @throws ConnectionException
     */
    public Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            throw new ConnectionException("Driver não encontrado");
        } catch (SQLException e) {
            throw new ConnectionException("Falha ao conectar com o banco de dados");
        }
        return connection;
    }

}
