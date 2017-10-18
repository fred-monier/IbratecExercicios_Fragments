package ibratec.recife.pe.br.ibratecexercicios_fragments;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AeronavesListaActivity extends AppCompatActivity implements IAeronaveCRUDRequestInterface {

    public static final String AERONAVE = "Aeronave";

    //private static final int CADASTRAR_AERONAVE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aeronaves_lista2);

        AeronavesListaFragment fragment = AeronavesListaFragment.novaInstancia();

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.lista, fragment, AeronavesListaFragment.TAG);
        fragmentTransaction.commit();

    }

    @Override
    public void operacaoCadastar(Aeronave aeronaveWork) {
        Intent intent = new Intent(AeronavesListaActivity.this, AeronavesCadastroActivity.class);
        if (aeronaveWork != null) {
            intent.putExtra(AERONAVE, aeronaveWork);
        }
        startActivity(intent);
    }
}
