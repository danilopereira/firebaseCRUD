package br.com.fiap.firebasecrud.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.com.fiap.firebasecrud.R;
import br.com.fiap.firebasecrud.model.Carro;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolders> {
    private List<Carro> carros;
    protected Context context;

    public RecyclerViewAdapter(Context context, List<Carro> carros) {
        this.carros = carros;
        this.context = context;
    }

    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerViewHolders viewHolder = null;
        View layoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.carro_item, parent, false);
        viewHolder = new RecyclerViewHolders(layoutView, carros);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolders holder, int position) {
        holder.tvCarro.setText(carros.get(position).getModelo());
    }

    @Override
    public int getItemCount() {
        return this.carros.size();
    }
}