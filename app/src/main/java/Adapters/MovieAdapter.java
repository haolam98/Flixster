package Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixster.DetailActivity;
import com.example.flixster.R;

import org.parceler.Parcels;

import java.util.List;

import Models.Movie;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{

    Context context;
    List<Movie> movies;
    public static final String TAG = "MovieAdapter";


    public MovieAdapter(Context context, List<Movie> movies) {
        Log.d("MovieAdapter", "Context: " + context.toString());
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate layout and return it inside of a view holder
        Log.d(TAG, "onCreateViewHolder");

        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie,parent,false);

        return new ViewHolder(movieView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //populate data into view through view holder

        Log.d(TAG, "onBindViewHolder.... position"+position);
        //get the movie at the position
        Movie movie = movies.get(position);

        //bind movie data into the view holder
        holder.bind(movie);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout container;
        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.item_title);
            tvOverview = itemView.findViewById(R.id.item_overview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            container = itemView.findViewById(R.id.item_container);
        }

        public void bind(final Movie movie) {

            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());


            String imageUrl;
            // if phone is in landscape mode
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            {
                imageUrl = movie.getBackdropPath();
                Log.d("MovieAdapter", "Orientation: Landscape ");
            }
            else {
                // else phone is portrait mode
                imageUrl = movie.getPosterPath();
                Log.d("MovieAdapter", "Orientation: Portrait ");
            }


            Glide.with(context).load(imageUrl).into(ivPoster);
            Log.d("MovieAdapter", "Register onClickListener for item_container ");
            //1. Register Click listener on the whole row
            tvTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //2. navigate to new activity on tap
                    //Toast.makeText(context, movie.getTitle(),Toast.LENGTH_SHORT).show();

                    Intent i = new Intent(context, DetailActivity.class);
                    //pass data to detail activity
                    i.putExtra("movie", Parcels.wrap(movie));
                    //i.putExtra("movie", );
                    context.startActivity(i);
                }
            });
//            container.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    //2. navigate to new activity on tap
//                    //Toast.makeText(context, movie.getTitle(),Toast.LENGTH_SHORT).show();
//
//                    Intent i = new Intent(context, DetailActivity.class);
//                    //pass data to detail activity
//                    i.putExtra("movie", Parcels.wrap(movie));
//                    //i.putExtra("movie", );
//                    context.startActivity(i);
//                }
//            });
            Log.d("MovieAdapter", "AFTER Register onClickListener for item_container ");
        }
    }
}
