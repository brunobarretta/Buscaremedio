package buscaremedio.com.br.buscaremedio;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Barretta on 09/11/2017.
 */

public class FarmaciaList extends ArrayAdapter<Farmacia> {

    private Activity context;
    private List<Farmacia> farmaciaList;

    public FarmaciaList(Activity context, List<Farmacia> farmaciaList){
        super(context, R.layout.list_layout, farmaciaList);
        this.context = context;
        this.farmaciaList = farmaciaList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_layout, null, true);

        TextView textViewTitulo = (TextView) listViewItem.findViewById(R.id.textViewMedicamento);
        TextView textViewEndereco = (TextView) listViewItem.findViewById(R.id.textViewApresentação);
        TextView textViewTelefone = (TextView) listViewItem.findViewById(R.id.textViewIndicação);

        Farmacia farmacia= farmaciaList.get(position);

        textViewTitulo.setText(farmacia.getSnippet());
        textViewEndereco.setText(farmacia.getEndereco());
        textViewTelefone.setText(farmacia.getTelefone());


        System.out.println(farmaciaList);

        return listViewItem;
    }
}
