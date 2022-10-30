package com.schuh.alunoscheck.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.schuh.alunoscheck.R;
import com.schuh.alunoscheck.controller.AlunoController;
import com.schuh.alunoscheck.models.Aluno;

import java.util.ArrayList;

public class AlunoListAdapter extends BaseAdapter {

    private ArrayList<Aluno> listaAluno;
    private Context context;
    public AlunoListAdapter alunoListAdapter;
    private AlunoController controller;
    private AlertDialog alertDialog;

    public AlunoListAdapter(ArrayList<Aluno> listaAluno, Context context) {
        this.listaAluno = listaAluno;
        this.context = context;
        alunoListAdapter = this;
        controller = new AlunoController(context);
    }

    @Override
    public int getCount() {
        return listaAluno.size();
    }

    @Override
    public Object getItem(int i) {
        return listaAluno.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            view = LayoutInflater.from(context)
                    .inflate(R.layout.aluno_list_item, viewGroup,
                            false);
        }
        TextView tvAluno = view.findViewById(R.id.tvAluno);
        ImageButton btRemover = view.findViewById(R.id.btRemover);

        Aluno aluno = listaAluno.get(i);
        tvAluno.setText(String.valueOf(aluno.getNome()));

        tvAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(aluno);
            }
        });

        btRemover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inativaAluno(aluno);
            }
        });

        return view;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void showDialog(Aluno aluno) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        //Não deixar ele fechar quando clica fora dele
        builder.setCancelable(false);

        builder.setTitle("Excluir");
        builder.setIcon(context.getResources().
                getDrawable(R.drawable.ic_remove, context.getTheme()));
        builder.setMessage("Deseja remover " + aluno.getNome() + "?");
        builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (controller.excluirAluno(aluno) > 0) {
                    listaAluno.remove(aluno);
                    alunoListAdapter.notifyDataSetChanged();
                    Toast.makeText(context, "Aluno excluído!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, "Erro ao excluir aluno!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialog.dismiss();
            }
        });

        alertDialog = builder.create();
        alertDialog.show();
    }

    public void inativaAluno(Aluno aluno) {
        aluno.setAtivo(0);
        controller.atualizarAluno(aluno);
        listaAluno.remove(aluno);
        alunoListAdapter.notifyDataSetChanged();
    }
}
