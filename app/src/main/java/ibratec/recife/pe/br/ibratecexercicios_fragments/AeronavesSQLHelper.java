package ibratec.recife.pe.br.ibratecexercicios_fragments;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Frederico on 26/09/2017.
 */

public class AeronavesSQLHelper extends SQLiteOpenHelper {

    private static final String NOME_BANCO = "DB_AERONAVES";
    private static final int VERSAO_BANCO = '1';

    public static final String TABELA_AERONAVE = "AERONAVES";
    public static final String TABELA_AERONAVE_COLUNA_ID = "ID";
    public static final String TABELA_AERONAVE_COLUNA_MODELO = "MODELO";
    public static final String TABELA_AERONAVE_COLUNA_FABRICANTE = "FABRICANTE";
    public static final String TABELA_AERONAVE_COLUNA_ASA_FIXA = "ASA_FIXA";
    public static final String TABELA_AERONAVE_COLUNA_TREM_RETRATIL = "TREM_RETRATIL";
    public static final String TABELA_AERONAVE_COLUNA_MULTIMOTOR = "MULTIMOTOR";
    public static final String TABELA_AERONAVE_COLUNA_VELOCIDADE_CRUZEIRO = "VELOCIDADE_CRUZEIRO";
    public static final String TABELA_AERONAVE_COLUNA_APTO = "APTO";
    public static final String TABELA_AERONAVE_COLUNA_HANGAR = "HANGAR";


    public AeronavesSQLHelper(Context context) {
        super(context, NOME_BANCO, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABELA_AERONAVE + " (" +
                TABELA_AERONAVE_COLUNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TABELA_AERONAVE_COLUNA_MODELO + " TEXT NOT NULL, " +
                TABELA_AERONAVE_COLUNA_FABRICANTE + " TEXT, " +
                TABELA_AERONAVE_COLUNA_ASA_FIXA + " INTEGER, " +
                TABELA_AERONAVE_COLUNA_TREM_RETRATIL + " INTEGER, " +
                TABELA_AERONAVE_COLUNA_MULTIMOTOR + " INTEGER, " +
                TABELA_AERONAVE_COLUNA_VELOCIDADE_CRUZEIRO + " INTEGER, " +
                TABELA_AERONAVE_COLUNA_APTO + " INTEGER, " +
                TABELA_AERONAVE_COLUNA_HANGAR + " TEXT)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE " + TABELA_AERONAVE;
        db.execSQL(sql);
        onCreate(db);
    }
}
