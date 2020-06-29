/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.aiec.projetoFinal.controle;

import br.aiec.projetoFinal.conexao.ModuloConexao;
import br.aiec.projetoFinal.modelo.ModeloDepartamento;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * 
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class ControleDepartamento {
    ModuloConexao conex = new ModuloConexao();
    ModuloConexao conexBsq = new ModuloConexao();
    ModuloConexao conexBsq2 = new ModuloConexao();
    ModuloConexao conexDir = new ModuloConexao();
    ModeloDepartamento dpto = new ModeloDepartamento();
    
    int dptoDirectId;
    String dptoDirectNome;
    //Gravar novo Departamento
    public void gravarDepartamento(ModeloDepartamento dpt){
       buscaDirectorDptoId(dpt.getDirect());
        
        conex.conexao();
        try {
            PreparedStatement pst = conex.con.prepareStatement("INSERT INTO departamentos (dptoCod,dptoNome,dptoDiret,dptoDesc) VALUES (?,?,?,?)");
            pst.setString(1, dpt.getCod());
            pst.setString(2, dpt.getNome());
            pst.setInt(3, dptoDirectId);
            pst.setString(4, dpt.getNotas());
            pst.execute();
            
            JOptionPane.showMessageDialog(null, "Departamento gravado com sucesso!");
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro: não foi possível gravar o Departamento");
        }
        conex.desconetar();
    }
    //Editar Departamento
    public void editarDepartamento(ModeloDepartamento pdt){
        conex.conexao();
        try {
            PreparedStatement pst = conex.con.prepareStatement("UPDATE departamentos SET dptoCod=?, dptoNome=?,dptoDiret=?,dptoDesc=? WHERE dptoId=?");
            pst.setString(1, dpto.getCod());
            pst.setString(2, dpto.getNome());
            pst.setInt(3, dptoDirectId);
            pst.setString(4, dpto.getNotas());
            pst.setInt(5, dpto.getId());
            pst.execute();
            
            JOptionPane.showMessageDialog(null, "Departamento editado com suscesso");
                    
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro: Não foi possível editar o departamento"+ex.getMessage());
        }
        conex.desconetar();
    }
    
    //Método busca nome do Director do Departamento
    public void buscaDirectorDptoId(String nome){
        conexBsq.conexao();        
        try {
            conexBsq.executaSQL("SELECT * FROM usuarios WHERE userNome = '"+nome+"'");
            conexBsq.res.first();
            dptoDirectId = conexBsq.res.getInt("userId");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possível seleccionar um Director para este Departamento\n"+ex.getMessage());
        }
        conexBsq.desconetar();
    }
    //Pesquisar Departamentos no BD
    public ModeloDepartamento pesquisaDepartamento(ModeloDepartamento bsqDepto){
        //String logado = TelaPrincipal.lblUserLogadoId.getText();
        conexBsq2.conexao();
        try {
            conexBsq2.executaSQL("SELECT * FROM departamentos WHERE lower(dptoCod) LIKE '%" + bsqDepto.getPesquisar() +"%' OR lower(dptoNome) LIKE '%" + bsqDepto.getPesquisar() +"%'");
            conexBsq2.res.first();
            buscaNomeDirector(conexBsq2.res.getInt("dptoDiret"));
            bsqDepto.setId(conexBsq2.res.getInt("dptoId"));
            bsqDepto.setNome(conexBsq2.res.getString("dptoNome"));
            bsqDepto.setCod(conexBsq2.res.getString("dptoCod"));
            bsqDepto.setDirect(dptoDirectNome);
            bsqDepto.setNotas(conexBsq2.res.getString("dptoDesc"));
                       
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro: departamento não encontrado\n"+ex);
        }
        conexBsq2.desconetar();
        return bsqDepto;
    }
    //Método busca o nome do Director
    public void buscaNomeDirector(int cod){
        conexDir.conexao();        
        try {
            conexDir.executaSQL("SELECT * FROM usuarios WHERE userId = '"+cod+"'");
            conexDir.res.first();
            dptoDirectNome = conexDir.res.getString("userNome");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro: Não foi possível encontrar o usuário\n"+ex.getMessage());
        }
        conexDir.desconetar();
    }
//Método para eliminar Departamento
    public void eliminarDepartamento(ModeloDepartamento dpt){
        conex.conexao();
        PreparedStatement pst;
        try {
            pst = conex.con.prepareStatement("DELETE FROM departamentos WHERE dptoId = ?");
            pst.setInt(1, dpt.getId());
            pst.execute();
            JOptionPane.showMessageDialog(null, "Departamento eliminado com sucesso");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro: não foi possível eliminar este departamento");
        }
        conex.desconetar();
    }
}
