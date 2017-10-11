package ibratec.recife.pe.br.ibratecexercicios_fragments;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import ibratec.recife.pe.br.ibratecexercicios_fragments.R;

public class AeronavesLista extends AppCompatActivity {

    public static final String AERONAVE = "Aeronave";

    private static final String LISTA_AERONAVES = "ListaAeronaves";
    private static final int CADASTRAR_AERONAVE = 1;

    private EditText edtTxtModeloPesq;

    private Aeronave aeronaveWork;
    private ArrayList<Aeronave> listaAeronaves;
    private AeronavesAdapter listaAeronavesAdapter;

    private AeronaveDAO aeronaveDAO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aeronaves_lista);

        aeronaveDAO = AeronaveDAO.getInstancia(this);

        if (savedInstanceState != null) {
            listaAeronaves = (ArrayList<Aeronave>) savedInstanceState.getSerializable(LISTA_AERONAVES);
        } else {
            listaAeronaves = new ArrayList<Aeronave>();
        }

        this.montarLista();

        //inicializando componentes
        edtTxtModeloPesq = (EditText) findViewById(R.id.edtTxtModeloPesq);

        Button btPesquisarModelo = (Button) findViewById(R.id.btPesquisarModelo);
        btPesquisarModelo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pesquisar();
            }
        });

        FloatingActionButton button1 = (FloatingActionButton) findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AeronavesLista.this, AeronavesCadastro.class);
                startActivityForResult(intent, CADASTRAR_AERONAVE);
            }
        });
        ////////////////

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(LISTA_AERONAVES, listaAeronaves);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

            if (resultCode == RESULT_OK) {

                aeronaveWork = (Aeronave) data.getSerializableExtra(AERONAVE);
                pesquisar();

            } else if (resultCode == RESULT_CANCELED) {}

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        super.onCreateContextMenu(menu, v, menuInfo);

        this.getMenuInflater().inflate(R.menu.aeronave_opcoes, menu);

        MenuItem itemExcluir = menu.getItem(0);
        itemExcluir.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                aeronaveDAO.excluir(aeronaveWork);
                pesquisar();

                return true;
            }
        });

        MenuItem itemAlterar = menu.getItem(1);
        itemAlterar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                Intent intent = new Intent(AeronavesLista.this, AeronavesCadastro.class);
                intent.putExtra(AERONAVE, aeronaveWork);
                startActivityForResult(intent, CADASTRAR_AERONAVE);

                return true;
            }
        });

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        this.aeronaveWork = listaAeronavesAdapter.getItem(info.position);

    }

    private void pesquisar() {
        String modelo = null;
        if (edtTxtModeloPesq.getText() != null && !edtTxtModeloPesq.getText().toString().equals("")) {
            modelo = edtTxtModeloPesq.getText().toString();
        }

        ArrayList<Aeronave> resultado = (ArrayList<Aeronave>) aeronaveDAO.buscarAeronavesPorModelo(modelo);
        listaAeronaves.clear();
        listaAeronaves.addAll(resultado);
        listaAeronavesAdapter.notifyDataSetChanged();
    }

    private void montarLista() {
        listaAeronavesAdapter = new AeronavesAdapter(listaAeronaves, this);
        ListView listaAeronavesView = (ListView) findViewById(R.id.listview1);
        listaAeronavesView.setAdapter(listaAeronavesAdapter);

        registerForContextMenu(listaAeronavesView);
    }

}
