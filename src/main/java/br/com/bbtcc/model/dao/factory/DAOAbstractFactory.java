package br.com.bbtcc.model.dao.factory;

import br.com.bbtcc.model.dao.IAddressDAO;
import br.com.bbtcc.model.dao.ICommentDAO;
import br.com.bbtcc.model.dao.ITCCDAO;
import br.com.bbtcc.model.dao.IUserDAO;

public interface DAOAbstractFactory {

    public IUserDAO createUserDAO();
    public IAddressDAO createAddtressDAO();
    public ITCCDAO createTCCDAO();
    public ICommentDAO createCommentDAO();
}
