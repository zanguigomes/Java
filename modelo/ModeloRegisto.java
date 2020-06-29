/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.aiec.projetoFinal.modelo;

/**
 * 
 * @author Nelito Gomes
 */
public class ModeloRegisto {
        private int Id;
        private String codigo;
        private String Categoria;
        private String Nome;
        private String Departamento;
        private String DataEmissao;
        private String AnoEmissao;
        private String Secreto;
        private String Retencao;
        private String Suporte;
        private String Localizacao;
        private String Url;
        private String UserCad;
        private String DataCad;
        private String Notas;
        private String Pesquisar;
        /**
     * @return the Id
     */
    public int getId() {
        return Id;
    }

    /**
     * @param Id the Id to set
     */
    public void setId(int Id) {
        this.Id = Id;
    }
    /**
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the Categoria
     */
    public String getCategoria() {
        return Categoria;
    }

    /**
     * @param Categoria the Categoria to set
     */
    public void setCategoria(String Categoria) {
        this.Categoria = Categoria;
    }

    /**
     * @return the Nome
     */
    public String getNome() {
        return Nome;
    }

    /**
     * @param Nome the Nome to set
     */
    public void setNome(String Nome) {
        this.Nome = Nome;
    }

    /**
     * @return the Departamento
     */
    public String getDepartamento() {
        return Departamento;
    }

    /**
     * @param Departamento the Departamento to set
     */
    public void setDepartamento(String Departamento) {
        this.Departamento = Departamento;
    }

    /**
     * @return the DataEmissao
     */
    public String getDataEmissao() {
        return DataEmissao;
    }

    /**
     * @param DataEmissao the DataEmissao to set
     */
    public void setDataEmissao(String DataEmissao) {
        this.DataEmissao = DataEmissao;
    }
    
    /**
     * @return the Secreto
     */
    public String getSecreto() {
        return Secreto;
    }

    /**
     * @param Secreto the Secreto to set
     */
    public void setSecreto(String Secreto) {
        this.Secreto = Secreto;
    }

    /**
     * @return the Retencao
     */
    public String getRetencao() {
        return Retencao;
    }

    /**
     * @param Retencao the Retencao to set
     */
    public void setRetencao(String Retencao) {
        this.Retencao = Retencao;
    }

    /**
     * @return the Suporte
     */
    public String getSuporte() {
        return Suporte;
    }

    /**
     * @param Suporte the Suporte to set
     */
    public void setSuporte(String Suporte) {
        this.Suporte = Suporte;
    }

    /**
     * @return the Localizacao
     */
    public String getLocalizacao() {
        return Localizacao;
    }

    /**
     * @param Localizacao the Localizacao to set
     */
    public void setLocalizacao(String Localizacao) {
        this.Localizacao = Localizacao;
    }

    /**
     * @return the Url
     */
    public String getUrl() {
        return Url;
    }

    /**
     * @param Url the Url to set
     */
    public void setUrl(String Url) {
        this.Url = Url;
    }

    /**
     * @return the UserCad
     */
    public String getUserCad() {
        return UserCad;
    }

    /**
     * @param UserCad the UserCad to set
     */
    public void setUserCad(String UserCad) {
        this.UserCad = UserCad;
    }

/**
     * @return the DataCad
     */
    public String getDataCad() {
        return DataCad;
    }

    /**
     * @param DataCad the DataCad to set
     */
    public void setDataCad(String DataCad) {
        this.DataCad = DataCad;
    }

    /**
     * @return the Notas
     */
    public String getNotas() {
        return Notas;
    }

    /**
     * @param Notas the Notas to set
     */
    public void setNotas(String Notas) {
        this.Notas = Notas;
    }

    private static class Int {

        public Int() {
        }
    }
    /**
     * @return the Pesquisar
     */
    public String getPesquisar() {
        return Pesquisar;
    }

    /**
     * @param Pesquisar the Pesquisar to set
     */
    public void setPesquisar(String Pesquisar) {
        this.Pesquisar = Pesquisar;
    }
}
