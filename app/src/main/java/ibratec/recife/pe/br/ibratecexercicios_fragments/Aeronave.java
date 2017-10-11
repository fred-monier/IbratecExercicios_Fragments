package ibratec.recife.pe.br.ibratecexercicios_fragments;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Frederico on 20/09/2017.
 */

public class Aeronave implements Serializable {

    public static final ArrayList<String> LISTA_FABRICANTE =
            new ArrayList<String>(Arrays.asList("EMBRAER", "HELIBRAS", "DORNIER", "PIPER"));
    public static final ArrayList<String> LISTA_HANGAR =
            new ArrayList<String>(Arrays.asList("HG01", "HG02", "HG03"));

    private long id;
    private String modelo;
    private boolean asaFixa;
    private boolean tremRetratil;
    private boolean multimotor;
    private boolean apto;
    private String fabricante;
    private int velocidadeCruzeiro;
    private String hangar;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public boolean isAsaFixa() {
        return asaFixa;
    }

    public void setAsaFixa(boolean asaFixa) {
        this.asaFixa = asaFixa;
    }

    public boolean isTremRetratil() {
        return tremRetratil;
    }

    public void setTremRetratil(boolean tremRetratil) {
        this.tremRetratil = tremRetratil;
    }

    public boolean isMultimotor() {
        return multimotor;
    }

    public void setMultimotor(boolean multimotor) {
        this.multimotor = multimotor;
    }

    public boolean isApto() {
        return apto;
    }

    public void setApto(boolean apto) {
        this.apto = apto;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public int getVelocidadeCruzeiro() {
        return velocidadeCruzeiro;
    }

    public void setVelocidadeCruzeiro(int velocidadeCruzeiro) {
        this.velocidadeCruzeiro = velocidadeCruzeiro;
    }

    public String getHangar() {
        return hangar;
    }

    public void setHangar(String hangar) {
        this.hangar = hangar;
    }

    @Override
    public String toString() {
        return this.modelo;
    }
}
