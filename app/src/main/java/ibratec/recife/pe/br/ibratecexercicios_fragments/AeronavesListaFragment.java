package ibratec.recife.pe.br.ibratecexercicios_fragments;

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
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Frederico on 17/10/2017.
 */

public class AeronavesListaFragment extends Fragment {

    public static final String TAG = "TagLista";

    private static final String LISTA_AERONAVES = "ListaAeronaves";

    private Aeronave aeronaveWork;
    private AeronaveDAO aeronaveDAO;

    private ArrayList<Aeronave> listaAeronaves;
    private AeronavesAdapter listaAeronavesAdapter;

    public static AeronavesListaFragment novaInstancia() {

        return new AeronavesListaFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        this.aeronaveDAO = AeronaveDAO.getInstancia(this.getContext());

        if (savedInstanceState != null) {
            listaAeronaves = (ArrayList<Aeronave>) savedInstanceState.getSerializable(LISTA_AERONAVES);
        } else {
            listaAeronaves = new ArrayList<Aeronave>();
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.fragment_aeronaves_lista, container, false);

        this.montarLista(layout);

        FloatingActionButton button1 = (FloatingActionButton) layout.findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                IAeronaveCRUDRequestInterface act = (IAeronaveCRUDRequestInterface) getActivity();
                act.operacaoCadastar(null);
            }
        });

        return layout;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(LISTA_AERONAVES, listaAeronaves);
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
                pesquisar(null);

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

    private void pesquisar(String modelo) {

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

    public void pesquisarPublico(String modelo) {
        pesquisar(modelo);
    }

}
