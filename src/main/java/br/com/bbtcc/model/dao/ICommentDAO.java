package br.com.bbtcc.model.dao;

import br.com.bbtcc.model.beans.Commentary;
import br.com.bbtcc.model.dao.generic.DataAccessException;
import br.com.bbtcc.model.beans.Commentary;
import br.com.bbtcc.model.beans.DocumentTCC;
import br.com.bbtcc.model.dao.generic.DataAccessException;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ICommentDAO {
    public boolean add(Commentary commentary) throws DataAccessException;
    public Optional<Commentary> searchByCommentary(Commentary commentary) throws DataAccessException, SQLException;
    public List<Commentary> SearchCommentsByDocument(DocumentTCC document) throws DataAccessException, SQLException;
    public boolean deleteByCommentary(Commentary commentary) throws DataAccessException, SQLException;
    public boolean update(Commentary commentary) throws DataAccessException;
}
