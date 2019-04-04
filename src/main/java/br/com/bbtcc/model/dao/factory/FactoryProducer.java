package br.com.bbtcc.model.dao.factory;

public class FactoryProducer {

    public enum FactoryType { JDBC }

    public static DAOAbstractFactory getFactory(FactoryType type) {
        switch(type) {
            case JDBC:
                return new DAOFactoryJDBC();
        }
        return null;
    }
}
