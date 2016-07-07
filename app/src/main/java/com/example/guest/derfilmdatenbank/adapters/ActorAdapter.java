package com.example.guest.derfilmdatenbank.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.guest.derfilmdatenbank.R;
import com.example.guest.derfilmdatenbank.models.Person;
import com.example.guest.derfilmdatenbank.ui.ActorDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Guest on 7/6/16.
 */
public class ActorAdapter extends RecyclerView.Adapter<ActorAdapter.ActorViewHolder> {
    private ArrayList<Person> mActors = new ArrayList<>();
    private Context mContext;

    public ActorAdapter(Context context, ArrayList<Person> actors) {
        mContext = context;
        mActors = actors;
    }
    @Override
    public ActorAdapter.ActorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_actor_list, parent, false);
        ActorViewHolder viewHolder = new ActorViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ActorAdapter.ActorViewHolder holder, int position) {
        holder.bindActor(mActors.get(position));
    }

    @Override
    public int getItemCount() {
        return mActors.size();
    }
    public class ActorViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Bind(R.id.actorImageView) ImageView mActorImageView;
        @Bind(R.id.actorNameTextView) TextView mNameTextView;
        @Bind(R.id.characterTextView) TextView mCharacterTextView;

        private Context mContext;

        public ActorViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
            itemView.setOnClickListener(this);

        }

        public void bindActor(Person actor) {
            if (actor.getPicture() == "null"){
                Log.d("status", "null");
                mActorImageView.setImageResource(R.drawable.actorsilloutte);
            }else{
                Picasso.with(mContext).load(actor.getPicture()).into(mActorImageView);
            }
            mNameTextView.setText(actor.getName());
            mCharacterTextView.setText(actor.getCharacter());
        }

        @Override
        public void onClick(View v) {
            int itemPosition = getLayoutPosition();
            Intent intent = new Intent(mContext, ActorDetailActivity.class);
            intent.putExtra("id", mActors.get(itemPosition).getId());
            mContext.startActivity(intent);
        }


    }

}

