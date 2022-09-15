package chuc.nang.chung;

import java.util.List;

import enity.Mess;

public interface CRUD<T> {
   List<T> getLists(T t);
   Mess save(T t);
   Mess update(T t);
   Mess delete(int id);
}
