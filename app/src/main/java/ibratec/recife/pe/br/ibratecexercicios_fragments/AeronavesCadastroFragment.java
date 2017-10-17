package ibratec.recife.pe.br.ibratecexercicios_fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * Created by Frederico on 17/10/2017.
 */

public class AeronavesCadastroFragment extends Fragment {

    public static final String TAG = "TagDetalhe";

    private static final String CRUZEIRO = "Velocidade de Cruzeiro:";

    private Aeronave aeronaveWork;
    private AeronaveDAO aeronaveDAO;

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


    public static AeronavesCadastroFragment novaInstancia(Aeronave aeronaveWork) {

        Bundle parametros = new Bundle();
        parametros.putSerializable(AeronavesLista.AERONAVE, aeronaveWork);

        AeronavesCadastroFragment aeronavesCadastroFragment = new AeronavesCadastroFragment();
        aeronavesCadastroFragment.setArguments(parametros);

        return aeronavesCadastroFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        this.aeronaveWork = (Aeronave) getArguments().getSerializable(AeronavesLista.AERONAVE);
        this.aeronaveDAO = AeronaveDAO.getInstancia(this.getContext());

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.fragment_aeronaves_cadastro, container, false);

        //Inicializando componentes
        edtTxtModelo = (EditText) layout.findViewById(R.id.edtTxtModelo);
        spFabricante = (Spinner) layout.findViewById(R.id.spFabricante);
        swtAsaFixa = (Switch) layout.findViewById(R.id.swtAsaFixa);
        chBoxTremRetrat = (CheckBox) layout.findViewById(R.id.chBoxTremRetrat);
        chBoxMultimotor = (CheckBox) layout.findViewById(R.id.chBoxMultimotor);
        txtVwVelCruzeiro = (TextView) layout.findViewById(R.id.txtVwVelCruzeiro);
        seeBarVelCruzeiro = (SeekBar) layout.findViewById(R.id.seeBarVelCruzeiro);
        rdBtHangarHA01 = (RadioButton) layout.findViewById(R.id.rdBtHangarHA01);
        rdBtHangarHA02 = (RadioButton) layout.findViewById(R.id.rdBtHangarHA02);
        rdBtHangarHA03 = (RadioButton) layout.findViewById(R.id.rdBtHangarHA03);
        tgBtDisponib = (ToggleButton) layout.findViewById(R.id.tgBtDisponib);

          //spFabricante
        this.spinnerArrayAdapter = new ArrayAdapter<String>(this.getContext(),
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

        //Incializar valores para Alteração
        this.inicializarValores();

        //Botão Cancelar
        Button btnCancelar = (Button) layout.findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //cancelar operação
                IAeronaveCRUDResponseInterface act = (IAeronaveCRUDResponseInterface) getActivity();
                act.operacaoCancelada();
            }
        });

        //Botão Salvar
        Button btnSalvar = (Button) layout.findViewById(R.id.btnSalvar);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtTxtModelo.getText() != null && !edtTxtModelo.getText().toString().equals("")) {

                    //Salvar o objeto Aeronave
                    definirValores();
                    aeronaveDAO.salvar(aeronaveWork);

                    //concluir operação
                    IAeronaveCRUDResponseInterface act = (IAeronaveCRUDResponseInterface) getActivity();
                    act.operacaoConcluida(aeronaveWork);

                } else {
                    Toast toast = Toast.makeText(getContext(), "Você deve informar um Modelo", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        return layout;
    }

    private void inicializarValores() {

        if (this.aeronaveWork != null) {

            edtTxtModelo.setText(this.aeronaveWork.getModelo());

            if (this.aeronaveWork.getFabricante() != null) {
                int pos = this.spinnerArrayAdapter.getPosition(this.aeronaveWork.getFabricante());
                spFabricante.setSelection(pos);
            }

            swtAsaFixa.setChecked(this.aeronaveWork.isAsaFixa());
            chBoxTremRetrat.setChecked(this.aeronaveWork.isTremRetratil());
            chBoxMultimotor.setChecked(this.aeronaveWork.isMultimotor());
            seeBarVelCruzeiro.setProgress(this.aeronaveWork.getVelocidadeCruzeiro());

            if (this.aeronaveWork.getHangar() != null) {
                if (this.aeronaveWork.getHangar().equals(Aeronave.LISTA_HANGAR.get(0))) {
                    rdBtHangarHA01.setChecked(true);
                } else if (this.aeronaveWork.getHangar().equals(Aeronave.LISTA_HANGAR.get(1))) {
                    rdBtHangarHA02.setChecked(true);
                } else {
                    rdBtHangarHA03.setChecked(true);
                }
            }

            tgBtDisponib.setChecked(this.aeronaveWork.isApto());

        } else {

            this.aeronaveWork = new Aeronave();
        }

        txtVwVelCruzeiro.setText(CRUZEIRO + " " + seeBarVelCruzeiro.getProgress() + " km/h");

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
