package pt.attendly.attendly;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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

        StudentsViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            name = (TextView)itemView.findViewById(R.id.txtNameStudent);
            studentNumber = (TextView)itemView.findViewById(R.id.txtStudentNumber);
            imageStudent = (ImageView)itemView.findViewById(R.id.imageStudent);
        }
    }

    List<User> studentsList;

    RVAdapter(List<User> studentsList){
        this.studentsList = studentsList;
    }

    @Override
    public int getItemCount() {
        return studentsList.size();
    }

    @Override
    public StudentsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);
        StudentsViewHolder cvh = new StudentsViewHolder(v);
        return cvh;

    }

    @Override
    public void onBindViewHolder(StudentsViewHolder holder, int position) {
        holder.name.setText(studentsList.get(position).getName());
        holder.studentNumber.setText(studentsList.get(position).getNumber());
        //holder.imageStudent.setImageBitmap(studentsList.get(position).getUrl_picture());



    }
}
