package br.com.bbtcc.model.dao.factory;

import br.com.bbtcc.model.dao.*;

public class DAOFactoryJDBC implements DAOAbstractFactory {

    @Override
    public IUserDAO createUserDAO() {
        return new UserDAO();
    }

    @Override
    public IAddressDAO createAddtressDAO() {
        return new AddressDAO();
    }

    @Override
    public ITCCDAO createTCCDAO() {
        return new TCCDAO();
    }

    @Override
    public ICommentDAO createCommentDAO() {
        return new CommentDAO();
    }
}
