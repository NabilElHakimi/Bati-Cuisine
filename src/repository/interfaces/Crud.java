package repository.interfaces;

import java.util.List;
import java.util.Optional;

public interface Crud<T> {
    int save(Optional<T> optional) ;
    Optional<T> findById(int id);
    List<T> findAll();
    boolean delete(int id);



}
