package DB.entidade;

public class Garcon {
    private int id;
    private String nome;
    private String CPF;
    private String CEP;
    private String Endereco;
    private String Cidade;
    private String UF;
    private String Fone;

    public Garcon(int id, String nome, String CPF, String CEP, String endereco, String cidade, String UF, String fone) {
        this.id = id;
        this.nome = nome;
        this.CPF = CPF;
        this.CEP = CEP;
        Cidade = cidade;
        Endereco = endereco;
        this.UF = UF;
        Fone = fone;
    }
    public Garcon(String nome, String cpf, String endereco, String cidade, String uf, String fone, String cep) {
        this(0,nome,cpf,cep,endereco,cidade,uf,fone);
    }

    public Garcon() {
        this(0,"","","","","","","");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }


    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public String getCEP() {
        return CEP;
    }

    public void setCEP(String CEP) {
        this.CEP = CEP;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return Endereco;
    }

    public void setEndereco(String endereco) {
        Endereco = endereco;
    }

    public String getCidade() {
        return Cidade;
    }

    public void setCidade(String cidade) {
        Cidade = cidade;
    }

    public String getUF() {
        return UF;
    }

    public void setUF(String UF) {
        this.UF = UF;
    }

    public String getFone() {
        return Fone;
    }

    public void setFone(String fone) {
        Fone = fone;
    }

    @Override
    public String toString()
    {
        return nome;
    }
}

