package buscaremedio.com.br.buscaremedio;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Barretta on 07/10/2017.
 */

public class RemedioList extends ArrayAdapter<Remedio> {

    private Activity context;
    private List<Remedio> remedioList;

    public RemedioList(Activity context, List<Remedio> remedioList){
        super(context, R.layout.list_layout, remedioList);
        this.context = context;
        this.remedioList = remedioList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_layout, null, true);

        TextView textViewMedicamento = (TextView) listViewItem.findViewById(R.id.textViewMedicamento);
        TextView textViewApresentação = (TextView) listViewItem.findViewById(R.id.textViewApresentação);
        TextView textViewIndicação = (TextView) listViewItem.findViewById(R.id.textViewIndicação);

        Remedio remedio= remedioList.get(position);

        textViewMedicamento.setText(remedio.getMedicamento());
        textViewApresentação.setText(remedio.getApresentacao());
        textViewIndicação.setText(remedio.getAplicacao());
        System.out.println(remedioList);

        return listViewItem;
    }
}

