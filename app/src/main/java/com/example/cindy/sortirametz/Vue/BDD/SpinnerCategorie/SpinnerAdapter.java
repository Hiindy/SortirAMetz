package com.example.cindy.sortirametz.Vue.BDD.SpinnerCategorie;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cindy.sortirametz.BDD.Categorie;
import com.example.cindy.sortirametz.R;

import java.util.ArrayList;

/**
 * Created by Cindy on 26/01/2018.
 */

public class SpinnerAdapter extends ArrayAdapter<Categorie> {
    int groupid;
    Activity context;
    ArrayList<Categorie> listeCategorie;
    LayoutInflater inflater;
    public SpinnerAdapter(Activity context, int groupid, int id, ArrayList<Categorie>
            listeCategorie){
        super(context,id,listeCategorie);
        this.listeCategorie=listeCategorie;
        inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.groupid=groupid;
    }

    public View getView(int position, View convertView, ViewGroup parent ){
        View itemView=inflater.inflate(groupid,parent,false);
        ImageView imageView=(ImageView)itemView.findViewById(R.id.img);
        imageView.setImageResource(listeCategorie.get(position).getImage());
        TextView textView=(TextView)itemView.findViewById(R.id.txt);
        textView.setText(listeCategorie.get(position).getLibelle());
        return itemView;
    }

    public View getDropDownView(int position, View convertView, ViewGroup
            parent){
        return getView(position,convertView,parent);

    }
}