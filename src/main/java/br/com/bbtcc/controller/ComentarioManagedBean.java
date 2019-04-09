package br.com.bbtcc.controller;

import br.com.bbtcc.model.beans.Commentary;
import br.com.bbtcc.model.beans.DocumentTCC;
import br.com.bbtcc.model.dao.CommentDAO;
import br.com.bbtcc.model.dao.factory.DAOAbstractFactory;
import br.com.bbtcc.model.dao.factory.FactoryProducer;
import br.com.bbtcc.model.dao.generic.DataAccessException;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import java.sql.SQLException;
import java.util.List;

@ManagedBean(name="comentarioMB")
@RequestScoped
public class ComentarioManagedBean {
    private CommentDAO commentDAO = null;
    List<Commentary> comentarios = null;
    private String novoComentario;

    @PostConstruct
    public void init() {
        DAOAbstractFactory daoAbstractFactory = FactoryProducer.getFactory(FactoryProducer.FactoryType.JDBC);
        commentDAO = (CommentDAO) daoAbstractFactory.createCommentDAO();
    }

    @ManagedProperty("#{loginMB}")
    private  LoginManagedBean loginManagedBean;

    public void buscarComentarios(int IdDocumento){
        try {
            comentarios = commentDAO.SearchCommentsByDocument(new DocumentTCC(null,null,null,null, IdDocumento));
        } catch (DataAccessException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String comentar(int IdDocumento){
        if(getLoginManagedBean().getUsuarioLogado() == null){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Efetue o login para poder comentar", "Efetue o login para poder comentar!!"));
        }else if(novoComentario == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Preencha o campo antes de enviar", "Preencha o campo antes de enviar"));
        }else{try {
                commentDAO.add(new Commentary(new DocumentTCC(null, null,null,null, IdDocumento), loginManagedBean.getUsuarioLogado(), novoComentario));
            } catch (DataAccessException e) {
                e.printStackTrace();
            }

        }
        return "";
    }

    public String deletar(int IdDocumento, int IdComentario){
        try {
            commentDAO.deleteByCommentary(new Commentary(new DocumentTCC(null, null, null, null, IdDocumento), loginManagedBean.getUsuarioLogado(), null, IdComentario));
        } catch (DataAccessException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getNovoComentario() {
        return novoComentario;
    }

    public void setNovoComentario(String novoComentario) {
        this.novoComentario = novoComentario;
    }

    public List<Commentary> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Commentary> comentarios) {
        this.comentarios = comentarios;
    }

    public LoginManagedBean getLoginManagedBean() {
        return loginManagedBean;
    }

    public void setLoginManagedBean(LoginManagedBean loginManagedBean) {
        this.loginManagedBean = loginManagedBean;
    }
}
