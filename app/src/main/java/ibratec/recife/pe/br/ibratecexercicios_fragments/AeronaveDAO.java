package ibratec.recife.pe.br.ibratecexercicios_fragments;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Frederico on 26/09/2017.
 */

public class AeronaveDAO {

    private static AeronaveDAO instancia;

    private AeronavesSQLHelper bdHelper;

    public static AeronaveDAO getInstancia(Context context) {
        if (instancia == null) {
            instancia = new AeronaveDAO(context);
        }
        return instancia;
    }

    private AeronaveDAO(Context context) {
        bdHelper = new AeronavesSQLHelper(context);
    }

    private long inserir(Aeronave aeronave) {

        SQLiteDatabase db = bdHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(bdHelper.TABELA_AERONAVE_COLUNA_MODELO, aeronave.getModelo());
        cv.put(bdHelper.TABELA_AERONAVE_COLUNA_FABRICANTE, aeronave.getFabricante());
        cv.put(bdHelper.TABELA_AERONAVE_COLUNA_ASA_FIXA, valorBooleano(aeronave.isAsaFixa()));
        cv.put(bdHelper.TABELA_AERONAVE_COLUNA_TREM_RETRATIL, valorBooleano(aeronave.isTremRetratil()));
        cv.put(bdHelper.TABELA_AERONAVE_COLUNA_MULTIMOTOR, valorBooleano(aeronave.isMultimotor()));
        cv.put(bdHelper.TABELA_AERONAVE_COLUNA_VELOCIDADE_CRUZEIRO, aeronave.getVelocidadeCruzeiro());
        cv.put(bdHelper.TABELA_AERONAVE_COLUNA_HANGAR, aeronave.getHangar());
        cv.put(bdHelper.TABELA_AERONAVE_COLUNA_APTO, valorBooleano(aeronave.isApto()));

        long id = db.insert(bdHelper.TABELA_AERONAVE, null, cv);

        if (id != -1) {
            aeronave.setId(id);
        }

        db.close();

        return id;
    }

    private int atualizar(Aeronave aeronave) {

        SQLiteDatabase db = bdHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(bdHelper.TABELA_AERONAVE_COLUNA_MODELO, aeronave.getModelo());
        cv.put(bdHelper.TABELA_AERONAVE_COLUNA_FABRICANTE, aeronave.getFabricante());
        cv.put(bdHelper.TABELA_AERONAVE_COLUNA_ASA_FIXA, valorBooleano(aeronave.isAsaFixa()));
        cv.put(bdHelper.TABELA_AERONAVE_COLUNA_TREM_RETRATIL, valorBooleano(aeronave.isTremRetratil()));
        cv.put(bdHelper.TABELA_AERONAVE_COLUNA_MULTIMOTOR, valorBooleano(aeronave.isMultimotor()));
        cv.put(bdHelper.TABELA_AERONAVE_COLUNA_VELOCIDADE_CRUZEIRO, aeronave.getVelocidadeCruzeiro());
        cv.put(bdHelper.TABELA_AERONAVE_COLUNA_HANGAR, aeronave.getHangar());
        cv.put(bdHelper.TABELA_AERONAVE_COLUNA_APTO, valorBooleano(aeronave.isApto()));

        int linhasAlteradas = db.update(bdHelper.TABELA_AERONAVE, cv,
                bdHelper.TABELA_AERONAVE_COLUNA_ID + " = ?",
                new String[]{String.valueOf(aeronave.getId())});
        db.close();

        return linhasAlteradas;
    }

    public void salvar(Aeronave aeronave) {
        if (aeronave.getId() == 0) {
            this.inserir(aeronave);
        } else {
            this.atualizar(aeronave);
        }
    }

    public int excluir(Aeronave aeronave) {
        SQLiteDatabase db = bdHelper.getWritableDatabase();

        int linhasExcluidas = db.delete(bdHelper.TABELA_AERONAVE,
                bdHelper.TABELA_AERONAVE_COLUNA_ID + " = ?",
                new String[]{String.valueOf(aeronave.getId())});
        db.close();

        return linhasExcluidas;
    }

    public List<Aeronave> buscarAeronavesPorModelo(String modelo) {

        List<Aeronave> res = new ArrayList<Aeronave>();

        SQLiteDatabase db = bdHelper.getReadableDatabase();

        String sql = "SELECT * FROM " + bdHelper.TABELA_AERONAVE;
        String[] args = null;

        if (modelo != null) {
            sql = sql + " WHERE " + bdHelper.TABELA_AERONAVE_COLUNA_MODELO + " LIKE ?";
            args = new String[]{"%" + modelo + "%"};
        }

        sql = sql + " ORDER BY " + bdHelper.TABELA_AERONAVE_COLUNA_MODELO;

        Cursor cursor = db.rawQuery(sql, args);
        while (cursor.moveToNext()) {

            long idCol = cursor.getLong(cursor.getColumnIndex(bdHelper.TABELA_AERONAVE_COLUNA_ID));
            String modeloCol = cursor.getString(cursor.getColumnIndex(bdHelper.TABELA_AERONAVE_COLUNA_MODELO));
            String fabCol = cursor.getString(cursor.getColumnIndex(bdHelper.TABELA_AERONAVE_COLUNA_FABRICANTE));
            int asaCol = cursor.getInt(cursor.getColumnIndex(bdHelper.TABELA_AERONAVE_COLUNA_ASA_FIXA));
            int tremCol = cursor.getInt(cursor.getColumnIndex(bdHelper.TABELA_AERONAVE_COLUNA_TREM_RETRATIL));
            int mulCol = cursor.getInt(cursor.getColumnIndex(bdHelper.TABELA_AERONAVE_COLUNA_MULTIMOTOR));
            int velCol = cursor.getInt(cursor.getColumnIndex(bdHelper.TABELA_AERONAVE_COLUNA_VELOCIDADE_CRUZEIRO));
            String hanCol = cursor.getString(cursor.getColumnIndex(bdHelper.TABELA_AERONAVE_COLUNA_HANGAR));
            int aptCol = cursor.getInt(cursor.getColumnIndex(bdHelper.TABELA_AERONAVE_COLUNA_APTO));

            Aeronave aeronave = new Aeronave();
            aeronave.setId(idCol);
            aeronave.setModelo(modeloCol);
            aeronave.setFabricante(fabCol);
            aeronave.setAsaFixa(valorInt(asaCol));
            aeronave.setTremRetratil(valorInt(tremCol));
            aeronave.setMultimotor(valorInt(mulCol));
            aeronave.setVelocidadeCruzeiro(velCol);
            aeronave.setHangar(hanCol);
            aeronave.setApto(valorInt(aptCol));

            res.add(aeronave);
        }

        return res;
    }

    private int valorBooleano(Boolean valor) {
        int res = 0;
        if (valor != null) {
            if (valor) {
                res = 1;
            }
        }
        return res;
    }

    private boolean valorInt(int valor) {
        boolean res = false;
        if (valor == 1) {
            res = true;
        }
        return res;
    }
}
