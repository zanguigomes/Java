/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.aiec.projetoFinal.controle;

import br.aiec.projetoFinal.conexao.ModuloConexao;
import br.aiec.projetoFinal.modelo.ModeloRegisto;
import br.aiec.projetoFinal.visao.TelaPrincipal;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Zangui
 */
public class ControleRegisto {
    ModuloConexao conex = new ModuloConexao();
    ModuloConexao conexDpto = new ModuloConexao();
    ModuloConexao conexCat = new ModuloConexao();
    ModuloConexao conexUser = new ModuloConexao();
    ModeloRegisto user = new ModeloRegisto();

    int codDpto;    
    int codCat;
       
    String dptoNome;
    String catNome;
    String userNome;
    
    //Gravando dados do usuário no BD
    public void gravarRegisto(ModeloRegisto record){
        buscaDepartamentoId(record.getDepartamento());
        buscaCategoriaId(record.getCategoria());
        String logado = TelaPrincipal.lblUserLogadoId.getText();
        
        conex.conexao();
        try {
            PreparedStatement pst = conex.con.prepareStatement("INSERT INTO registos (regCod,regCatId,regNome,regDptoId,regDataEmissao,regAnoEmissao,regSecreto,regRetencao,regSuporte,regLocalizacao,regUrl,regCadPor,regNotas) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)");
            pst.setString(1, record.getCodigo());
            pst.setInt(2, codCat);
            pst.setString(3, record.getNome());
            pst.setInt(4, codDpto);
            pst.setString(5, record.getDataEmissao());
            pst.setString(6, record.getDataEmissao().substring(0, 4));
            pst.setString(7, record.getSecreto());
            pst.setString(8, record.getRetencao());            
            pst.setString(9, record.getSuporte());
            pst.setString(10, record.getLocalizacao());
            pst.setString(11, record.getUrl());
            pst.setInt(12, Integer.parseInt(logado));
            pst.setString(13, record.getNotas());
            pst.execute();
            
            JOptionPane.showMessageDialog(null, "O registo { "+ record.getCodigo()+" } foi cadastrado com sucesso");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro: não foi possível cadastrar o registo { "+ record.getCodigo()+" } \n"+ex.getMessage());
        }
        conex.desconetar();
    }
    //Editanto Registo
    public void editarRegisto(ModeloRegisto record){
        buscaDepartamentoId(record.getDepartamento());
        buscaCategoriaId(record.getCategoria());
        String logado = TelaPrincipal.lblUserLogadoId.getText();
        conex.conexao();
        PreparedStatement pst;
        try {
            pst = conex.con.prepareStatement("UPDATE registos SET regCod=?,regCatId=?,regNome=?,regDptoId=?,regDataEmissao=?,regAnoEmissao=?,regSecreto=?,regRetencao=?,regSuporte=?,regLocalizacao=?,regUrl=?,regCadPor=?,regNotas=? WHERE regId = ?");
            pst.setString(1, record.getCodigo());
            pst.setInt(2, codCat);
            pst.setString(3, record.getNome());
            pst.setInt(4, codDpto);
            pst.setString(5, record.getDataEmissao());
            pst.setString(6, record.getDataEmissao().substring(0, 4));
            pst.setString(7, record.getSecreto());
            pst.setString(8, record.getRetencao());            
            pst.setString(9, record.getSuporte());
            pst.setString(10, record.getLocalizacao());
            pst.setString(11, record.getUrl());
            pst.setInt(12, Integer.parseInt(logado));
            pst.setString(13, record.getNotas());
            pst.setInt(14, record.getId());
            pst.execute();
            
            JOptionPane.showMessageDialog(null, "O Registo { "+ record.getNome()+" } foi actualizado com sucesso");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro: não foi possível alterar os dados do registo/n"+ex.getMessage());
        }
        
        conex.desconetar();
    }
    //Método busca cod Departamento
    public void buscaDepartamentoId(String nomeDpto){
        conexDpto.conexao();
        try {
            conexDpto.executaSQL("SELECT * FROM departamentos WHERE dptoNome = '"+nomeDpto+"'");
            conexDpto.res.first();
            codDpto = conexDpto.res.getInt("dptoId");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Não foi possível selecionar o Codigo do Departamento"+e.getMessage());
        }
        conexDpto.desconetar();
    }
    //Método busca cod Categorias
    public void buscaCategoriaId(String nomeCat){
        conexCat.conexao();
        try {
            conexCat.executaSQL("SELECT * FROM categorias WHERE catNome = '"+nomeCat+"'");
            conexCat.res.first();
            codCat = conexCat.res.getInt("catId");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Não foi possível selecionar o Codigo da Categoria"+e.getMessage());
        }
        conexCat.desconetar();
    }        
    
    //Pesquisar Registos no BD
    public ModeloRegisto pesquisaRegisto(ModeloRegisto bsqRecord){
        //String logado = TelaPrincipal.lblUserLogadoId.getText();
        conex.conexao();
        try {
            conex.executaSQL("SELECT * FROM registos WHERE lower(regCod) LIKE '%" + bsqRecord.getPesquisar() +"%' OR lower(regNome) LIKE '%" + bsqRecord.getPesquisar() +"%'");
            conex.res.first();
            buscaNomeCategoria(conex.res.getInt("regCatId"));
            buscaNomeDepartamento(conex.res.getInt("regDptoId"));
            usuarioQueCadastraRegisto(conex.res.getInt("regCadPor"));            
            bsqRecord.setId(conex.res.getInt("regId"));
            bsqRecord.setCodigo(conex.res.getString("regId"));
            bsqRecord.setCategoria(catNome);
            bsqRecord.setDepartamento(dptoNome);
            bsqRecord.setNome(conex.res.getString("regNome"));
            bsqRecord.setDataEmissao(conex.res.getString("regDataEmissao"));
            bsqRecord.setSecreto(conex.res.getString("regSecreto"));
            bsqRecord.setRetencao(conex.res.getString("regRetencao"));
            bsqRecord.setSuporte(conex.res.getString("regSuporte"));
            bsqRecord.setLocalizacao(conex.res.getString("regLocalizacao"));
            bsqRecord.setUrl(conex.res.getString("regUrl"));           
            bsqRecord.setUserCad(userNome);            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro: registo não encontrado\n"+ex);
        }
        conex.desconetar();
        return bsqRecord;
    }
    //Método busca o nome da Categoria
    public void buscaNomeCategoria(int cod){
        conexCat.conexao();        
        try {
            conexCat.executaSQL("SELECT * FROM categorias WHERE catId = '"+cod+"'");
            conexCat.res.first();
            catNome = conexCat.res.getString("catNome");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro: Não foi possível encontrar a categoria\n"+ex);
        }
        conexCat.desconetar();
    }
    
    //Método busca o nome do Departamento
    public void buscaNomeDepartamento(int cod){
        conexDpto.conexao();        
        try {
            conexDpto.executaSQL("SELECT * FROM departamentos WHERE dptoId = '"+cod+"'");
            conexDpto.res.first();
            dptoNome = conexDpto.res.getString("dptoNome");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro: Não foi possível encontrar o departamento\n"+ex);
        }
        conexDpto.desconetar();
    }
    //Método busca nome do Usurio que cadastrou o Registo
    public void usuarioQueCadastraRegisto(int cod){
        conexUser.conexao();        
        try {
            conexUser.executaSQL("SELECT * FROM usuarios WHERE userId = '"+cod+"'");
            conexUser.res.first();
            userNome = conexUser.res.getString("userNome");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possível encontrar o nome do usuário que cadastrou o registo\n"+ex);
        }
        conexUser.desconetar();
    }
    //Excluindo Registo do BD
    public void eliminarRegisto(ModeloRegisto record){
        conex.conexao();
        PreparedStatement pst;
        try {
            pst = conex.con.prepareStatement("DELETE FROM registos WHERE regId = ?");
            pst.setInt(1, record.getId());
            pst.execute();
            JOptionPane.showMessageDialog(null, "Registo eliminado com sucesso");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro: não foi possível eliminar o registo");
        }        
        conex.desconetar();
    }
}
