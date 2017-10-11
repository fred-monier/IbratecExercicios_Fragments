package ibratec.recife.pe.br.ibratecexercicios_fragments;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.w3c.dom.Text;

import ibratec.recife.pe.br.ibratecexercicios_fragments.R;

public class AeronavesCadastro extends AppCompatActivity {

    private static final String CRUZEIRO = "Velocidade de Cruzeiro:";

    private ArrayAdapter<String> spinnerArrayAdapter;

    private EditText edtTxtModelo;
    private Spinner spFabricante;
    private Switch swtAsaFixa;
    private CheckBox chBoxTremRetrat;
    private CheckBox chBoxMultimotor;
    private TextView txtVwVelCruzeiro;
    private SeekBar seeBarVelCruzeiro;
    private RadioButton rdBtHangarHA01;
    private RadioButton rdBtHangarHA02;
    private RadioButton rdBtHangarHA03;
    private ToggleButton tgBtDisponib;

    private Aeronave aeronaveWork;

    private AeronaveDAO aeronaveDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aeronaves_cadastro);

        aeronaveDAO = AeronaveDAO.getInstancia(this);

        //Inicializando componentes
        edtTxtModelo = (EditText) findViewById(R.id.edtTxtModelo);
        spFabricante = (Spinner) findViewById(R.id.spFabricante);
        swtAsaFixa = (Switch) findViewById(R.id.swtAsaFixa);
        chBoxTremRetrat = (CheckBox) findViewById(R.id.chBoxTremRetrat);
        chBoxMultimotor = (CheckBox) findViewById(R.id.chBoxMultimotor);
        txtVwVelCruzeiro = (TextView) findViewById(R.id.txtVwVelCruzeiro);
        seeBarVelCruzeiro = (SeekBar) findViewById(R.id.seeBarVelCruzeiro);
        rdBtHangarHA01 = (RadioButton) findViewById(R.id.rdBtHangarHA01);
        rdBtHangarHA02 = (RadioButton) findViewById(R.id.rdBtHangarHA02);
        rdBtHangarHA03 = (RadioButton) findViewById(R.id.rdBtHangarHA03);
        tgBtDisponib = (ToggleButton) findViewById(R.id.tgBtDisponib);

        //spFabricante
        this.spinnerArrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, Aeronave.LISTA_FABRICANTE);
        this.spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spFabricante.setAdapter(this.spinnerArrayAdapter);

        //seeBarVelCruzeiro
        seeBarVelCruzeiro.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                txtVwVelCruzeiro.setText(CRUZEIRO + " " + progressChangedValue + " km/h");
            }
        });

        //Incializandoo valores para Alteração
        this.inicializandoValores((Aeronave) getIntent().getSerializableExtra(AeronavesLista.AERONAVE));

        //txtVwVelCruzeiro
        txtVwVelCruzeiro.setText(CRUZEIRO + " " + seeBarVelCruzeiro.getProgress() + " km/h");

        //Botão Cancelar
        Button btnCancelar = (Button) findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                setResult(Activity.RESULT_CANCELED, intent);
                finish();
            }
        });

        //Botão Salvar
        Button btnSalvar = (Button) findViewById(R.id.btnSalvar);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtTxtModelo.getText() != null && !edtTxtModelo.getText().toString().equals("")) {

                    //Salvar o objeto Aeronave
                    definirValores();
                    aeronaveDAO.salvar(aeronaveWork);
                    Intent intent = getIntent();
                    intent.putExtra(AeronavesLista.AERONAVE, aeronaveWork);
                    setResult(Activity.RESULT_OK, intent);

                    Toast toast = Toast.makeText(getApplicationContext(), "Salvo com sucesso", Toast.LENGTH_SHORT);
                    toast.show();

                    finish();
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Você deve informar um Modelo", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

    }

    private void inicializandoValores(Aeronave aer) {

        if (aer != null) {

            this.aeronaveWork = aer;

            edtTxtModelo.setText(aer.getModelo());

            if (aer.getFabricante() != null) {
                int pos = this.spinnerArrayAdapter.getPosition(aer.getFabricante());
                spFabricante.setSelection(pos);
            }

            swtAsaFixa.setChecked(aer.isAsaFixa());
            chBoxTremRetrat.setChecked(aer.isTremRetratil());
            chBoxMultimotor.setChecked(aer.isMultimotor());
            seeBarVelCruzeiro.setProgress(aer.getVelocidadeCruzeiro());

            if (aer.getHangar() != null) {
                if (aer.getHangar().equals(Aeronave.LISTA_HANGAR.get(0))) {
                    rdBtHangarHA01.setChecked(true);
                } else if (aer.getHangar().equals(Aeronave.LISTA_HANGAR.get(1))) {
                    rdBtHangarHA02.setChecked(true);
                } else {
                    rdBtHangarHA03.setChecked(true);
                }
            }

            tgBtDisponib.setChecked(aer.isApto());

        } else {

            this.aeronaveWork = new Aeronave();
        }

    }

    private void definirValores() {

        this.aeronaveWork.setModelo(this.edtTxtModelo.getText().toString());
        this.aeronaveWork.setFabricante(this.spFabricante.getSelectedItem().toString());
        this.aeronaveWork.setAsaFixa(this.swtAsaFixa.isChecked());
        this.aeronaveWork.setTremRetratil(this.chBoxTremRetrat.isChecked());
        this.aeronaveWork.setMultimotor(this.chBoxMultimotor.isChecked());
        this.aeronaveWork.setVelocidadeCruzeiro(this.seeBarVelCruzeiro.getProgress());

        String hangar;
        if (rdBtHangarHA01.isChecked()) {
            hangar = Aeronave.LISTA_HANGAR.get(0);
        } else if (rdBtHangarHA02.isChecked()) {
            hangar = Aeronave.LISTA_HANGAR.get(1);
        } else {
            hangar = Aeronave.LISTA_HANGAR.get(2);
        }
        this.aeronaveWork.setHangar(hangar);

        this.aeronaveWork.setApto(this.tgBtDisponib.isChecked());
    }

}
