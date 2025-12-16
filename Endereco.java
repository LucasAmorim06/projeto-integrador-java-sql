package model;

public class Endereco {
    private int idEndereco;
    private String rua;
    private String bairro;
    private String cep;

    public Endereco() {}

    public Endereco(int idEndereco, String rua, String bairro, String cep) {
        this.idEndereco = idEndereco;
        this.rua = rua;
        this.bairro = bairro;
        this.cep = cep;
    }

    // Getters e Setters
    public int getIdEndereco() { return idEndereco; }
    public void setIdEndereco(int idEndereco) { this.idEndereco = idEndereco; }

    public String getRua() { return rua; }
    public void setRua(String rua) { this.rua = rua; }

    public String getBairro() { return bairro; }
    public void setBairro(String bairro) { this.bairro = bairro; }

    public String getCep() { return cep; }
    public void setCep(String cep) { this.cep = cep; }

    @Override
    public String toString() {
        return rua + ", " + bairro + " (" + cep + ")";
    }
}
