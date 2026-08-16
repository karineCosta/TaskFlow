package com.example.taskflow;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME =
            "TaskFlow.db";

    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_TAREFAS =
            "tarefas";

    public DatabaseHelper(Context context) {

        super(
                context,
                DATABASE_NAME,
                null,
                DATABASE_VERSION
        );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql =
                "CREATE TABLE " + TABLE_TAREFAS + " (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "titulo TEXT NOT NULL, " +
                        "descricao TEXT" +
                        ")";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(
            SQLiteDatabase db,
            int oldVersion,
            int newVersion) {

        db.execSQL(
                "DROP TABLE IF EXISTS " +
                        TABLE_TAREFAS
        );

        onCreate(db);
    }

    public long inserirTarefa(
            String titulo,
            String descricao) {

        SQLiteDatabase db =
                this.getWritableDatabase();

        ContentValues values =
                new ContentValues();

        values.put("titulo", titulo);
        values.put("descricao", descricao);

        long resultado =
                db.insert(
                        TABLE_TAREFAS,
                        null,
                        values
                );

        db.close();

        return resultado;
    }
    public String listarTarefas() {

        SQLiteDatabase db =
                this.getReadableDatabase();

        android.database.Cursor cursor =
                db.rawQuery(
                        "SELECT titulo, descricao FROM tarefas",
                        null
                );

        StringBuilder resultado =
                new StringBuilder();

        if (cursor.getCount() == 0) {

            resultado.append(
                    "Nenhuma tarefa cadastrada."
            );

        } else {

            int numero = 1;

            while (cursor.moveToNext()) {

                String titulo =
                        cursor.getString(0);

                String descricao =
                        cursor.getString(1);

                resultado.append(
                                numero
                        ).append(". ")
                        .append(titulo)
                        .append("\n");

                resultado.append(
                                "Descrição: "
                        ).append(descricao)
                        .append("\n\n");

                numero++;
            }
        }

        cursor.close();
        db.close();

        return resultado.toString();
    }
}