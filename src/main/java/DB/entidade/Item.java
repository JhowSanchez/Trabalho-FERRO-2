package DB.entidade;

public class Item {
    private int comID;
    private int proID;
    private int quant;
    private Produto produto;

    public Item(int comID, int proID, int quant, Produto produto) {
        this.comID = comID;
        this.proID = proID;
        this.quant = quant;
        this.produto = produto;
    }

    public Item(int comId, int proId, int itQuant) {
        this.comID = comID;
        this.proID = proID;
        this.quant = quant;
    }

    public int getComID() {
        return comID;
    }

    public void setComID(int comID) {
        this.comID = comID;
    }

    public int getProID() {
        return proID;
    }

    public void setProID(int proID) {
        this.proID = proID;
    }

    public int getQuant() {
        return quant;
    }

    public void setQuant(int quant) {
        this.quant = quant;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public String getProdutoNome() {
        return produto.getNome();
    }

}
