package pt.attendly.attendly;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import pt.attendly.attendly.firebase.manageData;
import pt.attendly.attendly.model.Log;
import pt.attendly.attendly.model.Subject;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.historycard, parent, false);
        HistoryAdapter.HistoryViewHolder cvh = new HistoryAdapter.HistoryViewHolder(v);
        return cvh;
    }

    @Override
    public void onBindViewHolder(HistoryViewHolder holder, int position) {

        String subjectName = "";

        for(Subject sj : manageData.subjects)
        {
            if(sj.getId() == logs.get(position).getId_subject())
            {
                subjectName = sj.getName();
            }
        }
        holder.txtSubject.setText(subjectName);
        String[] date = logs.get(position).getDate().split(" ");
        holder.txtDate.setText(date[0]);

        if(logs.get(position).getPresence() == 1)
        {
            holder.ivPresence.setImageResource(R.color.presenceGreen);
        }
        else
        {
            holder.ivPresence.setImageResource(R.color.presenceRed);
        }

    }

    List<Log> logs;

    HistoryAdapter(List<Log> logs) {
        this.logs = logs;
    }

    @Override
    public int getItemCount() {
        return logs.size();
    }

    public static class HistoryViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView txtSubject;
        TextView txtDate;
        ImageView ivPresence;

        HistoryViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            txtSubject = itemView.findViewById(R.id.txtSubject);
            txtDate = (TextView) itemView.findViewById(R.id.txtDate);
            ivPresence = (ImageView) itemView.findViewById(R.id.ivPresence);
        }
    }
}
