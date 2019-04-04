package br.com.bbtcc.model.dao;

import br.com.bbtcc.model.beans.DocumentTCC;
import java.sql.SQLException;
import java.util.List;

public interface ITCCDAO {
    public boolean add(DocumentTCC documentTCC) throws SQLException;
    public List<DocumentTCC> searchByName(String searchString) throws SQLException;
    public boolean update(DocumentTCC documentTCC) throws SQLException;
    public boolean deleteByDocumentTCC(DocumentTCC documentTCC) throws SQLException;
}
