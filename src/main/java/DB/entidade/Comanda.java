package DB.entidade;

import java.util.List;

public class Comanda {
    private int ID;
    private int numero;
    private String Nome;
    private String DataHora;
    private Garcon garcon;
    private List<Item> item;

    public Comanda(int ID, int numero,String nome, String dataHora, Garcon garcon) {
        this.ID = ID;
        Nome = nome;
        this.numero = numero;
        this.DataHora = dataHora;
        this.garcon=garcon;
    }
    public Comanda(int ID, int numero,String nome, String dataHora, Garcon garcon,List<Item> item) {
        this.ID = ID;
        Nome = nome;
        this.numero = numero;
        this.DataHora = dataHora;
        this.garcon=garcon;
        this.item = item;
    }

    public Comanda(int numero, String nome, String dataHora, Garcon garcon) {
        Nome = nome;
        this.numero = numero;
        this.DataHora = dataHora;
        this.garcon = garcon;
        this.item = null;
    }
    public Comanda(int numero, String nome, String dataHora, Garcon garcon,List<Item> item) {
        Nome = nome;
        this.numero = numero;
        this.DataHora = dataHora;
        this.garcon = garcon;
        this.item = item;
    }

    public List<Item> getItem() {
        return item;
    }

    public void setItem(List<Item> item) {
        this.item = item;
    }

    public Garcon getGarcon() {
        return garcon;
    }

    public void setGarcon(Garcon garcon) {
        this.garcon = garcon;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getDataHora() {
        return DataHora;
    }

    public void setDataHora(String dataHora) {
        DataHora = dataHora;
    }

    @Override
    public String toString()
    {
        return getNome();
    }
}
