package com.example.projet0406;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projet0406.models.Survey;

import java.util.List;

public class SurveyAdapter extends RecyclerView.Adapter<SurveyAdapter.SurveyViewHolder> {

    private List<Survey> surveyList;

    public SurveyAdapter(List<Survey> surveyList) {
        this.surveyList = surveyList;
    }

    @NonNull
    @Override
    public SurveyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_survey, parent, false);
        return new SurveyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SurveyViewHolder holder, int position) {
        Survey survey = surveyList.get(position);
        holder.titleTextView.setText(survey.getTitle());
        holder.descriptionTextView.setText(survey.getDescription());

        // Ouvrir RepondreSondageActivity quand on clique sur un item
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), RepondreSondageActivity.class);
            intent.putExtra("sondage_id", survey.getIdSurvey());
            intent.putExtra("sondage_titre", survey.getTitle());
            intent.putExtra("sondage_description", survey.getDescription());
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return surveyList.size();
    }

    public static class SurveyViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView, descriptionTextView;

        public SurveyViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
        }
    }
}