package com.example.taskflow;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.content.SharedPreferences;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import android.os.Environment;
import java.io.File;
import java.io.InputStream;
import android.database.sqlite.SQLiteDatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private EditText editTaskTitle;
    private EditText editTaskDescription;
    private DatabaseHelper databaseHelper;
    private Button btnSaveTask;
    private TextView textTaskResult;

    private ImageSwitcher imageSwitcher;
    private Button btnAnterior;
    private Button btnProximo;
    private GridView gridMenu;

    private int[] banners = {
            R.drawable.banner1,
            R.drawable.banner2,
            R.drawable.banner3
    };

    private int currentBanner = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        inicializarComponentes();
        configurarImageSwitcher();
        configurarGridView();
        configurarEventos();
        configurarMenuContexto();
    }

    private void inicializarComponentes() {

        editTaskTitle = findViewById(R.id.editTaskTitle);
        editTaskDescription = findViewById(R.id.editTaskDescription);
        btnSaveTask = findViewById(R.id.btnSaveTask);
        textTaskResult = findViewById(R.id.textTaskResult);

        imageSwitcher = findViewById(R.id.imageSwitcher);
        btnAnterior = findViewById(R.id.btnAnterior);
        btnProximo = findViewById(R.id.btnProximo);

        gridMenu = findViewById(R.id.gridMenu);
    }

    private void configurarImageSwitcher() {

        imageSwitcher.setFactory(() -> {

            ImageView imageView = new ImageView(this);

            imageView.setLayoutParams(
                    new ImageSwitcher.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                    )
            );

            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

            return imageView;
        });

        imageSwitcher.setInAnimation(
                AnimationUtils.loadAnimation(
                        this,
                        android.R.anim.fade_in
                )
        );

        imageSwitcher.setOutAnimation(
                AnimationUtils.loadAnimation(
                        this,
                        android.R.anim.fade_out
                )
        );

        imageSwitcher.setImageResource(
                banners[currentBanner]
        );
    }

    private void configurarGridView() {

        gridMenu.setAdapter(
                new GridAdapter(this)
        );
    }

    private void configurarEventos() {

        btnSaveTask.setOnClickListener(
                v -> salvarTarefa()
        );

        btnAnterior.setOnClickListener(
                v -> mostrarBannerAnterior()
        );

        btnProximo.setOnClickListener(
                v -> mostrarProximoBanner()
        );
    }

    private void configurarMenuContexto() {

        registerForContextMenu(textTaskResult);
    }

    private void mostrarProximoBanner() {

        currentBanner++;

        if (currentBanner >= banners.length) {
            currentBanner = 0;
        }

        imageSwitcher.setImageResource(
                banners[currentBanner]
        );
    }

    private void mostrarBannerAnterior() {

        currentBanner--;

        if (currentBanner < 0) {
            currentBanner = banners.length - 1;
        }

        imageSwitcher.setImageResource(
                banners[currentBanner]
        );
    }

    private void salvarTarefa() {

        String titulo =
                editTaskTitle.getText()
                        .toString()
                        .trim();

        String descricao =
                editTaskDescription.getText()
                        .toString()
                        .trim();

        if (titulo.isEmpty()) {

            Toast.makeText(
                    this,
                    "Informe o nome da tarefa.",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        SharedPreferences preferences =
                getSharedPreferences("TaskFlowPrefs", MODE_PRIVATE);

        SharedPreferences.Editor editor =
                preferences.edit();

        editor.putString("ultima_tarefa_titulo", titulo);
        editor.putString("ultima_tarefa_descricao", descricao);

        editor.apply();

        salvarTarefaEmArquivo(titulo, descricao);
        databaseHelper.inserirTarefa(
                titulo,
                descricao
        );

        textTaskResult.setText(
                "Tarefa cadastrada:\n\n" +
                        "Título: " + titulo + "\n" +
                        "Descrição: " + descricao
        );

        Toast.makeText(
                this,
                "Tarefa salva com sucesso!",
                Toast.LENGTH_SHORT
        ).show();

        editTaskTitle.setText("");
        editTaskDescription.setText("");
    }

    // Menu de opções
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(
                R.menu.main_menu,
                menu
        );

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.menuAtualizar) {

            textTaskResult.setText("");

            Toast.makeText(
                    this,
                    "Tela atualizada.",
                    Toast.LENGTH_SHORT
            ).show();

            return true;
        }

        if (item.getItemId() == R.id.menuSobre) {

            Toast.makeText(
                    this,
                    "TaskFlow - Gerenciamento de tarefas",
                    Toast.LENGTH_LONG
            ).show();

            return true;
        }
        if (item.getItemId() == R.id.menuAjuda) {

            Intent intent = new Intent(
                    this,
                    HelpActivity.class
            );

            startActivity(intent);

            return true;
        }
        if (item.getItemId() == R.id.menuLerArquivo) {

            lerTarefaDoArquivo();

            return true;
        }

        if (item.getItemId() == R.id.menuExportar) {

            String titulo = editTaskTitle.getText().toString().trim();
            String descricao = editTaskDescription.getText().toString().trim();

            SharedPreferences preferences =
                    getSharedPreferences(
                            "TaskFlowPrefs",
                            MODE_PRIVATE
                    );

            titulo = preferences.getString(
                    "ultima_tarefa_titulo",
                    titulo
            );

            descricao = preferences.getString(
                    "ultima_tarefa_descricao",
                    descricao
            );

            if (titulo.isEmpty()) {

                Toast.makeText(
                        this,
                        "Nenhuma tarefa disponível para exportar.",
                        Toast.LENGTH_SHORT
                ).show();

                return true;
            }

            salvarTarefaNoArmazenamentoExterno(
                    titulo,
                    descricao
            );

            return true;
        }
        if (item.getItemId() == R.id.menuInformacoes) {

            lerInformacoesTaskFlow();

            return true;
        }
        if (item.getItemId() == R.id.menuTarefasBanco) {

            String tarefas =
                    databaseHelper.listarTarefas();

            textTaskResult.setText(
                    "Tarefas armazenadas no banco:\n\n" +
                            tarefas
            );

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Cria o menu de contexto
    @Override
    public void onCreateContextMenu(
            android.view.ContextMenu menu,
            View v,
            android.view.ContextMenu.ContextMenuInfo menuInfo) {

        super.onCreateContextMenu(menu, v, menuInfo);

        menu.setHeaderTitle("Ações da tarefa");

        menu.add(0, 1, 0, "Editar tarefa");
        menu.add(0, 2, 1, "Excluir tarefa");
    }

    // Trata as ações do menu de contexto
    @Override
    public boolean onContextItemSelected(MenuItem item) {

        if (item.getItemId() == 1) {

            Toast.makeText(
                    this,
                    "Editar tarefa selecionado.",
                    Toast.LENGTH_SHORT
            ).show();

            return true;
        }

        if (item.getItemId() == 2) {

            textTaskResult.setText("");

            Toast.makeText(
                    this,
                    "Tarefa excluída.",
                    Toast.LENGTH_SHORT
            ).show();

            return true;
        }

        return super.onContextItemSelected(item);
    }
    private void salvarTarefaEmArquivo(String titulo, String descricao) {

        String conteudo =
                "Título: " + titulo + "\n" +
                        "Descrição: " + descricao;

        try {
            FileOutputStream fos =
                    openFileOutput("taskflow_task.txt", MODE_PRIVATE);

            fos.write(conteudo.getBytes());

            fos.close();

            Toast.makeText(
                    this,
                    "Tarefa salva em arquivo interno.",
                    Toast.LENGTH_SHORT
            ).show();

        } catch (IOException e) {

            Toast.makeText(
                    this,
                    "Erro ao salvar arquivo.",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    private void lerTarefaDoArquivo() {

        try {
            FileInputStream fis =
                    openFileInput("taskflow_task.txt");

            InputStreamReader reader =
                    new InputStreamReader(fis);

            StringBuilder conteudo =
                    new StringBuilder();

            char[] buffer = new char[1024];
            int quantidadeLida;

            while ((quantidadeLida = reader.read(buffer)) != -1) {
                conteudo.append(
                        buffer,
                        0,
                        quantidadeLida
                );
            }

            reader.close();
            fis.close();

            textTaskResult.setText(
                    "Conteúdo do arquivo:\n\n" +
                            conteudo.toString()
            );

            Toast.makeText(
                    this,
                    "Arquivo lido com sucesso.",
                    Toast.LENGTH_SHORT
            ).show();

        } catch (IOException e) {

            Toast.makeText(
                    this,
                    "Nenhum arquivo de tarefa encontrado.",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
    private void salvarTarefaNoArmazenamentoExterno(
            String titulo,
            String descricao) {

        try {

            // Obtém o diretório de armazenamento externo
            File armazenamentoExterno =
                    Environment.getExternalStorageDirectory();

            // Usa uma pasta específica do aplicativo dentro
            // do armazenamento externo privado do app
            File pastaTaskFlow =
                    new File(
                            getExternalFilesDir(
                                    Environment.DIRECTORY_DOCUMENTS
                            ),
                            "TaskFlow"
                    );

            if (!pastaTaskFlow.exists()) {
                pastaTaskFlow.mkdirs();
            }

            File arquivo =
                    new File(
                            pastaTaskFlow,
                            "TaskFlow_tarefa.txt"
                    );

            String conteudo =
                    "TaskFlow - Tarefa\n\n" +
                            "Título: " + titulo + "\n" +
                            "Descrição: " + descricao;

            FileOutputStream fos =
                    new FileOutputStream(arquivo);

            fos.write(conteudo.getBytes());

            fos.close();

            Toast.makeText(
                    this,
                    "Tarefa exportada para o armazenamento externo.",
                    Toast.LENGTH_LONG
            ).show();

        } catch (IOException e) {

            Toast.makeText(
                    this,
                    "Erro ao exportar: " + e.getMessage(),
                    Toast.LENGTH_LONG
            ).show();
        }
    }
    private void lerInformacoesTaskFlow() {

        try {

            InputStream inputStream =
                    getResources().openRawResource(
                            R.raw.taskflow_info
                    );

            InputStreamReader reader =
                    new InputStreamReader(inputStream);

            StringBuilder conteudo =
                    new StringBuilder();

            char[] buffer = new char[1024];
            int quantidadeLida;

            while ((quantidadeLida = reader.read(buffer)) != -1) {

                conteudo.append(
                        buffer,
                        0,
                        quantidadeLida
                );
            }

            reader.close();
            inputStream.close();

            textTaskResult.setText(
                    conteudo.toString()
            );

            Toast.makeText(
                    this,
                    "Informações carregadas.",
                    Toast.LENGTH_SHORT
            ).show();

        } catch (Exception e) {

            Toast.makeText(
                    this,
                    "Erro ao carregar informações.",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
}