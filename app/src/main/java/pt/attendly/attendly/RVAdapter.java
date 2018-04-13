package pt.attendly.attendly;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import pt.attendly.attendly.model.User;

/**
 * Created by Paulo on 11/04/2018.
 */


public class RVAdapter extends RecyclerView.Adapter<RVAdapter.StudentsViewHolder> {

    public static class StudentsViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView name;
        TextView studentNumber;
        ImageView imageStudent;
        Button btnAdd;

        StudentsViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            name = (TextView) itemView.findViewById(R.id.txtSubject);
            studentNumber = (TextView) itemView.findViewById(R.id.txtStudentNumber);
            imageStudent = (ImageView) itemView.findViewById(R.id.imageStudent);
            btnAdd = itemView.findViewById(R.id.btnAdd);
        }
    }

    List<User> studentsList;
    String page = "";

    RVAdapter(List<User> studentsList, String page) {
        this.studentsList = studentsList;
        this.page = page;
    }

    @Override
    public int getItemCount() {
        return studentsList.size();
    }

    @Override
    public StudentsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);
        StudentsViewHolder cvh = new StudentsViewHolder(v);
        android.util.Log.d("size", String.valueOf(getItemCount()));
        return cvh;

    }

    @Override
    public void onBindViewHolder(StudentsViewHolder holder, final int position) {
        holder.name.setText(studentsList.get(position).getName());
        holder.studentNumber.setText(studentsList.get(position).getNumber());
        Picasso.get().load(studentsList.get(position).getUrl_picture()).into(holder.imageStudent);
        if (page.equals("Manager")) {
            holder.btnAdd.setText("X");
            holder.btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ManagerActivity.some(v, position);
                }
            });
        } else {
            holder.btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AddActivity.some(v, position);
                }
            });
        }


    }
}
