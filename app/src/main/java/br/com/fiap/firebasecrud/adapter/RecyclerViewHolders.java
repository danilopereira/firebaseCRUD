package br.com.fiap.firebasecrud.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.List;

import br.com.fiap.firebasecrud.R;
import br.com.fiap.firebasecrud.model.Carro;

public class RecyclerViewHolders extends RecyclerView.ViewHolder{
    private static final String TAG = RecyclerViewHolders.class.getSimpleName();

    public TextView tvCarro;
    public Button btApagar;
    private List<Carro> carros;

    public RecyclerViewHolders(final View itemView, final List<Carro> carros) {
        super(itemView);
        this.carros = carros;
        tvCarro = (TextView)itemView.findViewById(R.id.tvCarro);
        btApagar = (Button) itemView.findViewById(R.id.btDel);

        btApagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String taskTitle = carros.get(getAdapterPosition()).getModelo();

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

                Query query = ref.child("carros").orderByChild("modelo").equalTo(taskTitle);

                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                            appleSnapshot.getRef().removeValue();
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e(TAG, "onCancelled", databaseError.toException());
                    }
                });
            }
        });
    }
}
