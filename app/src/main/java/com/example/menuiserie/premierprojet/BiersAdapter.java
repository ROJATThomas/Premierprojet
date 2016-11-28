package com.example.menuiserie.premierprojet;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

 public class BiersAdapter extends RecyclerView.Adapter<BiersAdapter.BierHolder> {

    private View view;

    public View getView(){
        return view;
    }

    public class BierHolder extends RecyclerView.ViewHolder {

        public TextView name;

        public BierHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.rv_bier_element_name);
        }
    }

    public JSONArray biers;
    private JSONObject o;

    public BiersAdapter(JSONArray js){
        this.biers=js;
    }

    public void setNewBiere( JSONArray biers){
        this.biers=biers;
        notifyDataSetChanged();
    }

    public JSONArray getBiers(){
        return biers;
    }

    @Override
    public BiersAdapter.BierHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MainActivity act = new MainActivity();
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_bier_element,parent,false);
        BierHolder bh = new BierHolder(view);
        return bh;
    }

    @Override
    public void onBindViewHolder(BiersAdapter.BierHolder holder, int position) {
        try {

            o = (JSONObject) biers.get(position);

            holder.name.setText( o.getString("name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return biers.length();
    }

}
