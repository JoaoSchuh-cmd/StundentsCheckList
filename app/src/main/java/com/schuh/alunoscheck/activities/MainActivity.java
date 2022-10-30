package com.schuh.alunoscheck.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.schuh.alunoscheck.R;
import com.schuh.alunoscheck.adapter.AlunoListAdapter;
import com.schuh.alunoscheck.controller.AlunoController;
import com.schuh.alunoscheck.models.Aluno;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private EditText edNomeAluno;
    private ListView lvAlunos;
    private AlunoController controller;
    private ArrayList<Aluno> listaAlunos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edNomeAluno = findViewById(R.id.edNomeAluno);
        lvAlunos = findViewById(R.id.lvAlunos);

        controller = new AlunoController(this);

        atualizaListaAlunos();

    }



    public void btRefreshOnClick(View view) {
        listaAlunos = new ArrayList();
        listaAlunos = controller.retornaTodosAlunos();
        for (int i = 0; i < listaAlunos.size(); i++) {
            Aluno aluno = listaAlunos.get(i);
            aluno.setAtivo(1);
            controller.atualizarAluno(aluno);
        }
        atualizaListaAlunos();
    }

    public void btAddOnClick(View view) {
        Aluno aluno = new Aluno();
        if (validaDados()) {
            if (!listaAlunos.isEmpty())
                aluno.setId(
                        listaAlunos.get(listaAlunos.size() - 1).getId() + 1); //Sempre pega o último ID + 1
            else aluno.setId(1);
            aluno.setNome(edNomeAluno.getText().toString());
            aluno.setAtivo(1);

            if(controller.salvarAluno(aluno) > 0){
                edNomeAluno.setText("");
                atualizaListaAlunos();
            }else
                Toast.makeText(this, "Erro ao inserir Aluno", Toast.LENGTH_LONG).show();
        }
    }

    public boolean validaDados() {
        if (edNomeAluno.getText().toString().isEmpty()) {
            Toast.makeText(this, "Campo vazio!", Toast.LENGTH_LONG).show();
            return false;
        } else if (!verificaCadastros(edNomeAluno.getText().toString())) {
            Toast.makeText(this, "Aluno já cadastrado!", Toast.LENGTH_LONG).show();
            return false;
        } else
            return true;
    }

    public boolean verificaCadastros(String nome) {
        if (listaAlunos.size() > 0)
            for (int i = 0; i < listaAlunos.size(); i++) {
                if (listaAlunos.get(i).getNome().equals(nome))
                    return false;
            }

        return true;
    }

    private void atualizaListaAlunos() {
        listaAlunos = new ArrayList<>();
        listaAlunos = controller.retornaAlunosAtivos();
        AlunoListAdapter adapter = new AlunoListAdapter(
                listaAlunos, this);

        lvAlunos.setAdapter(adapter);
    }
}