package com.schuh.alunoscheck.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.schuh.alunoscheck.helper.SQLiteDataHelper;
import com.schuh.alunoscheck.models.Aluno;

import java.util.ArrayList;

public class AlunoDao implements GenericDao<Aluno> {

    //Abre a conexão com a BD
    private SQLiteOpenHelper openHelper;

    //Base de Dados
    private SQLiteDatabase db;

    //nome das colunas da tabela
    private String[]colunas = {"ID", "NOME", "ATIVO"};

    //Nome da Tabela
    private String tableName = "ALUNO";

    //Contexto no qual o DAO foi chamado
    private Context context;

    private static AlunoDao instancia;

    public static AlunoDao getInstancia(Context context){
        return (instancia == null ? new AlunoDao(context) : instancia);
    }

    //Construtor
    private AlunoDao(Context context) {
        this.context = context;

        //Abrir a conexão com BD
        openHelper = new SQLiteDataHelper(this.context,
                "UNIPAR", null, 1);

        db = openHelper.getWritableDatabase();
    }

    @Override
    public long insert(Aluno obj) {

        ContentValues valores = new ContentValues();
        valores.put("ID", obj.getId());
        valores.put("NOME", obj.getNome());
        valores.put("ATIVO", obj.getAtivo());

        return db.insert(tableName, null, valores);
    }

    @Override
    public long delete(Aluno obj) {
        String[]identificador = {String.valueOf(obj.getId())};
        return db.delete(tableName,"ID = ?",identificador);
    }

    @Override
    public long update(Aluno obj) {
        ContentValues valores = new ContentValues();
        valores.put("NOME", obj.getNome());
        valores.put("ATIVO", obj.getAtivo());

        String[]identificador = {String.valueOf(obj.getId())};
        return db.update(tableName, valores,
                "ID = ?", identificador);
    }

    @Override
    public ArrayList<Aluno> getAll() {
        ArrayList<Aluno> listaAluno = new ArrayList<>();

        Cursor cursor = db.query(tableName, colunas,
                null, null, null, null,
                "ID asc");
        if(cursor.moveToFirst()){
            do{
                Aluno aluno = new Aluno();
                aluno.setId(cursor.getInt(0));
                aluno.setNome(cursor.getString(1));

                listaAluno.add(aluno);
            }while(cursor.moveToNext());
        }

        return listaAluno;
    }

    @Override
    public ArrayList<Aluno> getAtivos() {
        ArrayList<Aluno> listaAluno = new ArrayList<>();
        String[]args = {"1"};

        Cursor cursor = db.query(tableName, colunas,
                "ATIVO = ?", args, null, null,
                "ID asc");
        if(cursor.moveToFirst()){
            do{
                Aluno aluno = new Aluno();
                aluno.setId(cursor.getInt(0));
                aluno.setNome(cursor.getString(1));

                listaAluno.add(aluno);
            }while(cursor.moveToNext());
        }

        return listaAluno;
    }

    @Override
    public Aluno getById(int id) {
        String[]identificador = {String.valueOf(id)};

        Cursor cursor = db.query(tableName, colunas,
                "ID = ?", identificador,
                null, null, null);
        if(cursor.moveToFirst()){
            Aluno aluno = new Aluno();
            aluno.setId(cursor.getInt(0));
            aluno.setNome(cursor.getString(1));

            return aluno;
        }

        return null;
    }
}
