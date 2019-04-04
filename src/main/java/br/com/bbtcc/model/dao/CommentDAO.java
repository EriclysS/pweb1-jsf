package br.com.bbtcc.model.dao;

import br.com.bbtcc.model.beans.Commentary;
import br.com.bbtcc.model.beans.DocumentTCC;
import br.com.bbtcc.model.beans.User;
import br.com.bbtcc.model.dao.factory.DAOAbstractFactory;
import br.com.bbtcc.model.dao.factory.FactoryProducer;
import br.com.bbtcc.model.dao.generic.DataAccessException;
import br.com.bbtcc.model.jdbc.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CommentDAO implements ICommentDAO {

    private Connection connection;

    public CommentDAO() {
        connection = ConnectionFactory.getInstance().getConnection();
    }

    @Override
    public boolean add(Commentary commentary) throws DataAccessException {

        String query = "INSERT INTO comentario (id_tcc, email_usuario, comentario) VALUES(?,?,?)";

        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, commentary.getDocumentTCC().getId() );
            statement.setString(2, commentary.getUser().getEmail());
            statement.setString(3, commentary.getCommentary());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Falha ao salvar comentário");
        }
       return true;
    }

    @Override
    public Optional<Commentary> searchByCommentary(Commentary commentary) throws DataAccessException, SQLException {
        Optional<Commentary> commentary1 = Optional.empty();

            String query = "SELECT * FROM comentario WHERE id_comentario = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, commentary.getId());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                commentary1 = Optional.of(new Commentary( commentary.getDocumentTCC(), commentary.getUser(),resultSet.getString("comentario"), commentary.getId()));
            }

        return commentary1;
    }


    public List<Commentary> SearchCommentsByDocument(DocumentTCC document) throws DataAccessException, SQLException {
        List<Commentary> commentaries = new ArrayList<>();
        Commentary comment = null;

        String query = "SELECT * FROM comentario WHERE  id_tcc = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, document.getId());
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            //find author
            DAOAbstractFactory daoAbstractFactory = FactoryProducer.getFactory(FactoryProducer.FactoryType.JDBC);
            UserDAO userDAO = (UserDAO) daoAbstractFactory.createUserDAO();
            Optional<User> autor = userDAO.searchByEmail(resultSet.getString("email_usuario"));
            //--
            comment = new Commentary(document, autor.get(), resultSet.getString("comentario"),  resultSet.getInt("id_comentario"));

            commentaries.add(comment);
        }
        return commentaries;
    }

    @Override
    public boolean deleteByCommentary(Commentary commentary) throws DataAccessException, SQLException {

            String query = "DELETE FROM comentario WHERE id_comentario = ? and id_tcc=? and email_usuario=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, commentary.getId());
            statement.setInt(2, commentary.getDocumentTCC().getId() );
            statement.setString(3, commentary.getUser().getEmail());
            int rs = statement.executeUpdate();
            if(rs<1){
                return false;
            }
        return true;
    }

    @Override
    public boolean update(Commentary commentary) throws DataAccessException {
        try {
            boolean TCCExistente = this.searchByCommentary(commentary).isPresent()?true:false;
            String query = "";
            if (!TCCExistente) {
               this.add(commentary);
            } else {
                query = "UPDATE comentario SET  comentario = ? WHERE id_comentario = ? and id_tcc=? and email_usuario=?";
            }
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, commentary.getCommentary() );
            statement.setInt(2, commentary.getId());
            statement.setInt(3, commentary.getDocumentTCC().getId() );
            statement.setString(4, commentary.getUser().getEmail());
            statement.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Falha ao atualizar o comentário");
        }
        return true;
    }

    public List<Commentary> SearchMyCommentsByDocument(User user, DocumentTCC document) throws DataAccessException, SQLException {
        List<Commentary> commentaries = new ArrayList<>();
        Commentary comment = null;

        String query = "SELECT * FROM comentario WHERE  id_tcc = ? and email_usuario=?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, document.getId());
        statement.setString(2, user.getEmail());
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            comment = new Commentary(document, user, resultSet.getString("comentario"),  resultSet.getInt("id_comentario"));
            commentaries.add(comment);
        }
        return commentaries;
    }
}
