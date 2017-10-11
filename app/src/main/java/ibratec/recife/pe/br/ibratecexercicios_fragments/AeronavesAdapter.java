package ibratec.recife.pe.br.ibratecexercicios_fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import java.util.ArrayList;

import ibratec.recife.pe.br.ibratecexercicios_fragments.R;

/**
 * Created by Frederico on 26/09/2017.
 */

public class AeronavesAdapter extends BaseAdapter {

    private ArrayList<Aeronave> dataSet;
    private Context mContext;

    public AeronavesAdapter(ArrayList<Aeronave> dataSet, Context mContext) {
        this.dataSet = dataSet;
        this.mContext = mContext;
    }

    public int getCount() {
        int res = dataSet.size();
        return res;
    }

    public Aeronave getItem(int position) {
        return dataSet.get(position);
    }

    public long getItemId(int position) {
        return dataSet.get(position).getId();
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        //carregando layout
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).
                    inflate(R.layout.aeronaves_row_item, parent, false);

            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //caregando item atual
        Aeronave currentItem = (Aeronave) getItem(position);

        //carregando componentes do layout
        viewHolder.itemModelo.setText(currentItem.getModelo());
        viewHolder.itemFabricante.setText(currentItem.getFabricante());

        //retornando a view
        return convertView;
    }

    private class ViewHolder {
        TextView itemModelo;
        TextView itemFabricante;

        public ViewHolder(View view) {
            itemModelo = (TextView) view.findViewById(R.id.txtModelo);
            itemFabricante = (TextView) view.findViewById(R.id.txtFabricante);
        }
    }
}
