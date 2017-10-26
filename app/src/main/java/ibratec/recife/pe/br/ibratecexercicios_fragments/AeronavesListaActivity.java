package ibratec.recife.pe.br.ibratecexercicios_fragments;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class AeronavesListaActivity extends AppCompatActivity
        implements IAeronaveCRUDRequestInterface, IAeronaveCRUDResponseInterface,
        SearchView.OnQueryTextListener, MenuItemCompat.OnActionExpandListener {//MenuItem.OnActionExpandListener {

    public static final String AERONAVE = "Aeronave";

    private static final int CADASTRAR_AERONAVE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aeronaves_lista);

        //Android toolbar*********
        //Descomentar aqui, onCreateOptionsMenu, fragment_aeronaves_listaS e styles.xml
        //Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        //************************

        this.inserirListaFragment();
        this.removerCadastroFragment();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {

            this.recarregarPesquisa(null);

        } else if (resultCode == RESULT_CANCELED) {}

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.aeronave_tool_bar_opcoes, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        //SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint(getString(R.string.hint_pesquisar));

        MenuItemCompat.setOnActionExpandListener(searchItem, this);
        //searchItem.setOnActionExpandListener(this);


        super.onCreateOptionsMenu(menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_report:
                Intent intent = new Intent(this, AeronavesRelatorioListaActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
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

    private void recarregarPesquisa(String modelo) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        AeronavesListaFragment fragmentLista = (AeronavesListaFragment) fragmentManager.
                findFragmentByTag(AeronavesListaFragment.TAG);
        fragmentLista.pesquisarPublico(modelo);
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

        this.recarregarPesquisa(null);
    }

    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        return true;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        this.recarregarPesquisa(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText == null || newText.equals("") || ((newText.length() >= 3))) {
            this.recarregarPesquisa(newText);
        }
        return true;
    }


}
