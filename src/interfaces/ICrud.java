package interfaces;

import java.util.List;

public interface ICrud<T> {
    
    //create
    public void salvar(T t);

    //read
    public List<T> consultarTodos();

    //read
    public T consultarPorId (int id);
    
    //update
    public void update(T t);

    //delete
    public void excluir(int id);
}
