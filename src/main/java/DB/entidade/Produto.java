package DB.entidade;

public class Produto {
    private int ID;
    private String nome;
    private String preco;
    private Categoria cat;
    private Unidade uni;

    public Produto(int ID, String nome, String preco) {
        this.ID = ID;
        this.nome = nome;
        this.preco = preco;
    }
    public Produto(String nome, String preco){
        this.nome = nome;
        this.preco = preco;
    }

    public Produto(String nome, String preco, Unidade uni,Categoria cat)
    {
        this.nome = nome;
        this.preco = preco;
        this.cat = cat;
        this.uni = uni;
    }
    public Produto(int ID,String nome, String preco, Unidade uni,Categoria cat)
    {
        this.ID=ID;
        this.nome = nome;
        this.preco = preco;
        this.cat = cat;
        this.uni = uni;
    }


    public Categoria getCat() {
        return cat;
    }

    public void setCat(Categoria cat) {
        this.cat = cat;
    }

    public Unidade getUni() {
        return uni;
    }

    public void setUni(Unidade uni) {
        this.uni = uni;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }

    @Override
    public String toString()
    {
        return getNome();
    }
}
