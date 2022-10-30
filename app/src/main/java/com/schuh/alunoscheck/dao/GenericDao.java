package com.schuh.alunoscheck.dao;

import java.util.ArrayList;

public interface GenericDao<Objeto>{
    long insert(Objeto obj);
    long delete(Objeto obj);
    long update(Objeto obj);
    ArrayList<Objeto> getAll();
    ArrayList<Objeto> getAtivos();
    Objeto getById(int id);
}
