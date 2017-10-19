package ibratec.recife.pe.br.ibratecexercicios_fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Frederico on 17/10/2017.
 */

public class AeronavesListaFragment extends Fragment {

    public static final String TAG = "TagLista";

    private Aeronave aeronaveWork;
    private AeronaveDAO aeronaveDAO;

    private ArrayList<Aeronave> listaAeronaves;
    private AeronavesAdapter listaAeronavesAdapter;

    private EditText edtTxtModeloPesq;

    private boolean flagMore = false;

    public static AeronavesListaFragment novaInstancia() {

        return new AeronavesListaFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        this.aeronaveDAO = AeronaveDAO.getInstancia(this.getContext());

        listaAeronaves = new ArrayList<Aeronave>();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.fragment_aeronaves_lista, container, false);

        this.montarLista(layout);

        //inicializando componentes
        edtTxtModeloPesq = (EditText) layout.findViewById(R.id.edtTxtModeloPesq);

        Button btPesquisarModelo = (Button) layout.findViewById(R.id.btPesquisarModelo);
        btPesquisarModelo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pesquisar();
            }
        });

        FloatingActionButton button1 = (FloatingActionButton) layout.findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //cadastrar
                IAeronaveCRUDRequestInterface act = (IAeronaveCRUDRequestInterface) getActivity();
                act.operacaoCadastar(null);
            }
        });
        ////////////////

        return layout;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        super.onCreateContextMenu(menu, v, menuInfo);

        this.getActivity().getMenuInflater().inflate(R.menu.aeronave_opcoes, menu);

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

                //cadastrar
                IAeronaveCRUDRequestInterface act = (IAeronaveCRUDRequestInterface) getActivity();
                act.operacaoCadastar(aeronaveWork);

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

    private void montarLista(View view) {
        listaAeronavesAdapter = new AeronavesAdapter(listaAeronaves, this.getContext());
        ListView listaAeronavesView = (ListView) view.findViewById(R.id.listview1);
        listaAeronavesView.setAdapter(listaAeronavesAdapter);

        registerForContextMenu(listaAeronavesView);
    }

    public void pesquisarPublico() {
        pesquisar();
    }

}
