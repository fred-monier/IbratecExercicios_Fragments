package ibratec.recife.pe.br.ibratecexercicios_fragments;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class AeronavesListaActivity extends AppCompatActivity implements IAeronaveCRUDRequestInterface,
        IAeronaveCRUDResponseInterface {

    public static final String AERONAVE = "Aeronave";

    private static final int CADASTRAR_AERONAVE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aeronaves_lista);

        this.inserirListaFragment();
        this.removerCadastroFragment();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {

            FragmentManager fragmentManager = getSupportFragmentManager();
            AeronavesListaFragment fragmentLista = (AeronavesListaFragment) fragmentManager.
                    findFragmentByTag(AeronavesListaFragment.TAG);
            fragmentLista.pesquisarPublico();

        } else if (resultCode == RESULT_CANCELED) {}

    }

    private boolean isLandscape() {
        return getResources().getBoolean(R.bool.land);
    }

    private void inserirListaFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        AeronavesListaFragment fragment = (AeronavesListaFragment) fragmentManager.
                findFragmentByTag(AeronavesListaFragment.TAG);

        if (fragment == null) {
            fragment = AeronavesListaFragment.novaInstancia();

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.lista, fragment, AeronavesListaFragment.TAG);
            fragmentTransaction.commit();
        }

    }

    private void inserirCadastroFragment(Aeronave aeronaveWork) {

        AeronavesCadastroFragment fragment = AeronavesCadastroFragment.novaInstancia(aeronaveWork);

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.cadastro, fragment, AeronavesCadastroFragment.TAG);
        fragmentTransaction.commit();
    }

    private void removerCadastroFragment() {

        FragmentManager fragmentManager = getSupportFragmentManager();
        AeronavesCadastroFragment fragment = (AeronavesCadastroFragment) fragmentManager.
                findFragmentByTag(AeronavesCadastroFragment.TAG);

        if ( fragment != null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(fragment);
            fragmentTransaction.commit();
        }
    }

    @Override
    public void operacaoCadastar(Aeronave aeronaveWork) {
        if(isLandscape()) {

            this.inserirCadastroFragment(aeronaveWork);
        } else {

            Intent intent = new Intent(AeronavesListaActivity.this, AeronavesCadastroActivity.class);
            if (aeronaveWork != null) {
                intent.putExtra(AERONAVE, aeronaveWork);
            }
            startActivityForResult(intent, CADASTRAR_AERONAVE);
        }
    }

    @Override
    public void operacaoCancelada() {
        this.removerCadastroFragment();
    }

    @Override
    public void operacaoConcluida() {

        this.removerCadastroFragment();

        Toast toast = Toast.makeText(this, "Salvo com sucesso", Toast.LENGTH_SHORT);
        toast.show();

        FragmentManager fragmentManager = getSupportFragmentManager();
        AeronavesListaFragment fragmentLista = (AeronavesListaFragment) fragmentManager.
                findFragmentByTag(AeronavesListaFragment.TAG);
        fragmentLista.pesquisarPublico();
    }
}
