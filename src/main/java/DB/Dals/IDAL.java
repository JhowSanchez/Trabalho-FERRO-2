package DB.Dals;

import java.util.List;

public interface IDAL <T>{
    public boolean gravar(T entidade);
    public boolean alterar(T entidade);
    public boolean apagar(T entidade);
    public T get(int id);
    public List<T> get(String filtro);
    public List<T> get();
}
