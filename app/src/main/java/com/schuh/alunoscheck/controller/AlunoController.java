package com.schuh.alunoscheck.controller;

import android.content.Context;

import com.schuh.alunoscheck.dao.AlunoDao;
import com.schuh.alunoscheck.models.Aluno;

import java.util.ArrayList;

public class AlunoController {

    private Context context;

    public AlunoController(Context context) {
        this.context = context;
    }

    public ArrayList<Aluno> retornaAlunosAtivos() { return AlunoDao.getInstancia(context).getAtivos(); }

    public ArrayList<Aluno> retornaTodosAlunos() {return AlunoDao.getInstancia(context).getAll(); }

    public long atualizarAluno (Aluno aluno) {return AlunoDao.getInstancia(context).update(aluno); }

    public long salvarAluno(Aluno aluno){
        return AlunoDao.getInstancia(context).insert(aluno);
    }

    public long excluirAluno(Aluno aluno) { return AlunoDao.getInstancia(context).delete(aluno); }

}
