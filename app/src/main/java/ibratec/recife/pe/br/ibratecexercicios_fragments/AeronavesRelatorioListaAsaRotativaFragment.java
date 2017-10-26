package ibratec.recife.pe.br.ibratecexercicios_fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class AeronavesRelatorioListaAsaRotativaFragment extends Fragment {

    public static final String TAG = "TagListaAsaRotativa";

    private static final String LISTA_AERONAVES = "ListaAeronaves";

    private AeronaveDAO aeronaveDAO;

    private ArrayList<Aeronave> listaAeronaves;
    private AeronavesAdapter listaAeronavesAdapter;


    public static AeronavesRelatorioListaAsaRotativaFragment novaInstancia() {
        return new AeronavesRelatorioListaAsaRotativaFragment();
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

        View layout = inflater.inflate(R.layout.fragment_aeronaves_relatorio_lista_asa_rotativa, container, false);

        this.montarLista(layout);

        this.pesquisar();

        return layout;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(LISTA_AERONAVES, listaAeronaves);
    }

    private void pesquisar() {

        ArrayList<Aeronave> resultado = (ArrayList<Aeronave>) aeronaveDAO.buscarAeronavesPorAsaFixa(false);
        listaAeronaves.clear();
        listaAeronaves.addAll(resultado);
        listaAeronavesAdapter.notifyDataSetChanged();
    }

    private void montarLista(View view) {

        listaAeronavesAdapter = new AeronavesAdapter(listaAeronaves, this.getContext());
        ListView listaAeronavesView = (ListView) view.findViewById(R.id.listview1);
        listaAeronavesView.setAdapter(listaAeronavesAdapter);
    }

}
