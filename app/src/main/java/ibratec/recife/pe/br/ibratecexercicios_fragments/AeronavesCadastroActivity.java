package ibratec.recife.pe.br.ibratecexercicios_fragments;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class AeronavesCadastroActivity extends AppCompatActivity implements IAeronaveCRUDResponseInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aeronaves_cadastro);

        Aeronave aeronave = (Aeronave) getIntent().getSerializableExtra(AeronavesListaActivity.AERONAVE);
        AeronavesCadastroFragment fragment = AeronavesCadastroFragment.novaInstancia(aeronave);

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.cadastro, fragment, AeronavesCadastroFragment.TAG);
        fragmentTransaction.commit();

    }

    @Override
    public void operacaoCancelada() {
        finish();
    }

    @Override
    public void operacaoConcluida(Aeronave aeronaveWork) {
        Toast toast = Toast.makeText(this, "Salvo com sucesso", Toast.LENGTH_SHORT);
        toast.show();
        finish();
    }
}
