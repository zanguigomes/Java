/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.aiec.projetoFinal.controle;

import br.aiec.projetoFinal.conexao.ModuloConexao;
import br.aiec.projetoFinal.modelo.ModeloUsuario;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Zangui
 */
public class ControleUsuario {
    ModuloConexao conex = new ModuloConexao();
    ModuloConexao conexDpto = new ModuloConexao();
    ModeloUsuario user = new ModeloUsuario();
    int codDpto;
    String dptoNome;
    //Verifica se usuário já existe no banco de dados

    //Gravando dados do usuário no BD
    public void gravarUsuario(ModeloUsuario user){
        buscaDepartamentoId(user.getDepartamento());
        conex.conexao();
        try {
            PreparedStatement pst = conex.con.prepareStatement("INSERT INTO usuarios (userNome,userEmail,userLogin,userSenha,userNivel,userDptoId,userStatus,userCadNotas) VALUES (?,?,?,?,?,?,?,?)");
            pst.setString(1, user.getNome());
            pst.setString(2, user.getEmail());
            pst.setString(3, user.getLogin());
            pst.setString(4, user.getSenha());
            pst.setString(5, user.getNivel());
            pst.setInt(6, codDpto);
            pst.setString(7, user.getStatus());
            pst.setString(8, user.getNotas());
            pst.execute();
            
            JOptionPane.showMessageDialog(null, "O usuário { "+ user.getNome()+" } foi cadastrado com sucesso");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro: não foi possível cadastrar o usuário { "+ user.getNome()+" } "+ex.getLocalizedMessage());
        }
        conex.desconetar();
    }
    //Método busca cod Departamento
    public void buscaDepartamentoId(String nomeDpto){
        conex.conexao();
        try {
            conex.executaSQL("SELECT * FROM departamentos WHERE dptoNome = '"+nomeDpto+"'");
            conex.res.first();
            codDpto = conex.res.getInt("dptoId");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Não foi possível selecionar o Codigo do Departamento"+e.getMessage());
        }
        conex.desconetar();
    }
    
    //Editanto Usuário
    public void editarUsuario(ModeloUsuario user){
        buscaDepartamentoId(user.getDepartamento());
        conex.conexao();
        PreparedStatement pst;
        try {
            pst = conex.con.prepareStatement("UPDATE usuarios SET userNome=?, userEmail = ?, userLogin = ?, userSenha = ?, userNivel = ?, userDptoId = ?, userStatus = ?, userCadNotas = ? WHERE userId = ?");
            pst.setString(1, user.getNome());
            pst.setString(2, user.getEmail());
            pst.setString(3, user.getLogin());
            pst.setString(4, user.getSenha());
            pst.setString(5, user.getNivel());
            pst.setInt(6, codDpto);
            pst.setString(7, user.getStatus());
            pst.setString(8, user.getNotas());
            pst.setInt(9, user.getCodigo());
            pst.execute();
            
            JOptionPane.showMessageDialog(null, "O usuário { "+ user.getNome()+" } foi actualizado com sucesso");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro: não foi possível alterar os dados do usuário/n"+ex.getMessage());
        }
        
        conex.desconetar();
    }
    //Excluindo Usuario do BD
    public void eliminaUsuario(ModeloUsuario user){
        conex.conexao();
        PreparedStatement pst;
        try {
            pst = conex.con.prepareStatement("DELETE FROM usuarios WHERE userId = ?");
            pst.setInt(1, user.getCodigo());
            pst.execute();
            JOptionPane.showMessageDialog(null, "Usuário eliminado com sucesso");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro: não foi possível eliminar este usuário");
        }
        
        conex.desconetar();
    }
    //Pesquisar Usuario no BD
    public ModeloUsuario pesquisaUsuario(ModeloUsuario bsqUser){
        //buscaDepartamentoId(user.getDepartamento());
        conex.conexao();
        try {
            conex.executaSQL("SELECT * FROM usuarios WHERE userNome LIKE '%" + bsqUser.getPesquisar() +"%' OR userLogin LIKE '%"+bsqUser.getPesquisar()+"'");
            conex.res.first();
            buscaNomeDepartamento(conex.res.getInt("userDptoId"));
            bsqUser.setCodigo(conex.res.getInt("userId"));
            bsqUser.setLogin(conex.res.getString("userLogin"));
            bsqUser.setSenha(conex.res.getString("userSenha"));
            bsqUser.setNome(conex.res.getString("userNome"));
            bsqUser.setEmail(conex.res.getString("userEmail"));
            bsqUser.setNivel(conex.res.getString("userNivel"));
            bsqUser.setStatus(conex.res.getString("userStatus"));
            bsqUser.setDepartamento(dptoNome);
            bsqUser.setNotas(conex.res.getString("userCadNotas"));
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro: usuário não encontrado.");
        }
        conex.desconetar();
        return bsqUser;
    }
    //Método busca o nome do Departamento
    public void buscaNomeDepartamento(int cod){
        conexDpto.conexao();        
        try {
            conexDpto.executaSQL("SELECT * FROM departamentos WHERE dptoId = '"+cod+"'");
            conexDpto.res.first();
            dptoNome = conexDpto.res.getString("dptoNome");
        } catch (SQLException ex) {
            Logger.getLogger(ControleUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        conexDpto.desconetar();
    }
}
