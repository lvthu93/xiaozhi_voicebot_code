package defpackage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import info.dourok.voicebot.R;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/* renamed from: h0  reason: default package */
public final class h0 extends RecyclerView.Adapter<a> {
    public final List<i0> a;
    public final SimpleDateFormat b = new SimpleDateFormat("HH:mm", Locale.getDefault());

    /* renamed from: h0$a */
    public class a extends RecyclerView.ViewHolder {
        public final TextView a;
        public final TextView b;

        public a(View view) {
            super(view);
            this.a = (TextView) view.findViewById(R.id.textViewContent);
            this.b = (TextView) view.findViewById(R.id.textViewTime);
        }
    }

    public h0(ArrayList arrayList) {
        this.a = arrayList;
    }

    public final int getItemCount() {
        return this.a.size();
    }

    public final int getItemViewType(int i) {
        int ordinal = this.a.get(i).c.ordinal();
        if (ordinal == 0) {
            return 1;
        }
        if (ordinal != 1) {
            return 3;
        }
        return 2;
    }

    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        a aVar = (a) viewHolder;
        i0 i0Var = this.a.get(i);
        aVar.getClass();
        aVar.a.setText(i0Var.a);
        TextView textView = aVar.b;
        if (textView != null) {
            textView.setText(h0.this.b.format(new Date(i0Var.b)));
        }
    }

    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater from = LayoutInflater.from(viewGroup.getContext());
        if (i == 1) {
            view = from.inflate(R.layout.item_message_user, viewGroup, false);
        } else if (i == 2) {
            view = from.inflate(R.layout.item_message_server, viewGroup, false);
        } else if (i != 3) {
            view = from.inflate(R.layout.item_message_system, viewGroup, false);
        } else {
            view = from.inflate(R.layout.item_message_system, viewGroup, false);
        }
        return new a(view);
    }
}
