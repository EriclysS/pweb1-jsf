package br.com.bbtcc.model.dao;

import br.com.bbtcc.model.beans.DocumentTCC;
import br.com.bbtcc.model.beans.User;
import br.com.bbtcc.model.dao.factory.DAOAbstractFactory;
import br.com.bbtcc.model.dao.factory.FactoryProducer;
import br.com.bbtcc.model.jdbc.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TCCDAO implements ITCCDAO{

    private Connection connection;

    public TCCDAO() {
        connection = ConnectionFactory.getInstance().getConnection();
    }

    @Override
    public boolean add(DocumentTCC documentTCC) throws SQLException {
        String query = "INSERT INTO tcc( email_usuario, titulo, curso, descricao, arquivo_dados, arquivo_nome) VALUES (?,?,?,?,?,?)";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, documentTCC.getUser().getEmail());
        statement.setString(2, documentTCC.getTitle());
        statement.setString(3, documentTCC.getCourse());
        statement.setString(4, documentTCC.getDescription());
        statement.setBytes(5, documentTCC.getDocument());
        statement.setString(6, documentTCC.getFileName());
        statement.executeUpdate();
        return true;
    }

    @Override
    public List<DocumentTCC> searchByName(String searchString) throws SQLException {
        Optional<DocumentTCC> document = null;
        List<DocumentTCC> documents = new ArrayList<>();

        String sql = "SELECT * FROM tcc WHERE titulo LIKE '%"+searchString+"%'";
        Statement statement = connection.createStatement();
        ResultSet set = statement.executeQuery(sql);
        while (set.next()) {
            //find author
            DAOAbstractFactory daoAbstractFactory = FactoryProducer.getFactory(FactoryProducer.FactoryType.JDBC);
            UserDAO userDAO = (UserDAO) daoAbstractFactory.createUserDAO();
            Optional<User> autor = userDAO.searchByEmail(set.getString("email_usuario"));
            //--
            document = Optional.of(new DocumentTCC(set.getString("titulo"), set.getString("curso"),
                    set.getString("descricao"), autor.get(), set.getInt("id_tcc")));

            documents.add(document.get());
    }
        return documents;
    }


    public DocumentTCC searchById(int searchId) throws SQLException {
        DocumentTCC document = null;

        String sql = "SELECT * FROM tcc WHERE id_tcc = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, searchId);
        ResultSet set = statement.executeQuery();
        if(set.next()) {
            //find author
            DAOAbstractFactory daoAbstractFactory = FactoryProducer.getFactory(FactoryProducer.FactoryType.JDBC);
            UserDAO userDAO = (UserDAO) daoAbstractFactory.createUserDAO();
            Optional<User> autor = userDAO.searchByEmail(set.getString("email_usuario"));
            //--

            document = new DocumentTCC(set.getString("titulo"), set.getString("curso"), set.getString("descricao"),
                    autor.get(), set.getInt("id_tcc"));
        }
        return document;
    }

    public  List<DocumentTCC> searchByAuthor(User author) throws SQLException {

        Optional<DocumentTCC> document = null;
        List<DocumentTCC> documents = new ArrayList<>();

        String sql = "SELECT * FROM tcc WHERE email_usuario = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, author.getEmail());
        ResultSet set = statement.executeQuery();
        while (set.next()) {
            document = Optional.of(new DocumentTCC(set.getString("titulo"), set.getString("curso"), set.getString("descricao"),
                    author, set.getInt("id_tcc")));
            documents.add(document.get());
        }
        return documents;
    }


    public DocumentTCC getAttachment(int searchId) throws SQLException {
        DocumentTCC doc = null;
        String sql = "SELECT * FROM tcc WHERE id_tcc = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, searchId);
        ResultSet set = statement.executeQuery();
        if(set.next()) {
            doc = new DocumentTCC(set.getString("arquivo_nome"),set.getBytes("arquivo_dados"), searchId);
        }
       return doc;
    }

    @Override
    public boolean update(DocumentTCC documentTCC) throws SQLException {
        /*boolean documentExists = this.searchByName(documentTCC.getFileName()).isPresent() ? true : false;*/

        String query = "";
        /*if (documentExists) {*/
            query = "UPDATE tcc SET  email_usuario = ?, titulo =? , curso = ? , arquivo = ?  WHERE id = ?";
       /* } else {
            return false;
        }*/
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, documentTCC.getUser().getEmail());
        statement.setString(2, documentTCC.getTitle());
        statement.setString(3, documentTCC.getCourse());
        statement.setBytes(4, documentTCC.getDocument());
        statement.setInt(5, documentTCC.getId());

        statement.executeUpdate();
        return true;
    }

    @Override
    public boolean deleteByDocumentTCC(DocumentTCC documentTCC) throws SQLException {
        String query = "DELETE FROM tcc WHERE id_tcc = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, documentTCC.getId());
        boolean b = statement.execute();
        return b;
    }
}
