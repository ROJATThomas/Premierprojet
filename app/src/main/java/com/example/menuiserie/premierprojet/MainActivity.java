package com.example.menuiserie.premierprojet;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import org.json.JSONArray;
import org.json.JSONException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    public static final String BIERS_UPDATE = "com.octip.cours.inf4042_11.BIERS_UPDATE";

    private JSONArray js;

    private RecyclerView rv;

    public RecyclerView getRv(){
        return rv;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2main);
        GetBiersServices.startActionBears(this);
        //
        IntentFilter intentFilter = new  IntentFilter(BIERS_UPDATE);
        Toast.makeText(MainActivity.this,getString(R.string.app_name),Toast.LENGTH_LONG).show();

        LocalBroadcastManager.getInstance(this).registerReceiver(new BierUpdate(),intentFilter);
        Toast.makeText(MainActivity.this,getString(R.string.finish),Toast.LENGTH_LONG).show();
        setContentView(R.layout.activitymain);

        rv = (RecyclerView) findViewById(R.id.rv_biere);
        rv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

            rv.setAdapter(new BiersAdapter(getBiersFromFile()));


    }

    public JSONArray getBiersFromFile(){

        try{
            InputStream is = new FileInputStream(getCacheDir()+"/"+"bieres.json");
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            return new JSONArray(new String(buffer, "UTF-8"));//construction du tableau


        }catch(IOException e) {
            e.printStackTrace();
            return new JSONArray();

        }catch(JSONException e ){
            e.printStackTrace();
            return new JSONArray();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.aa :
                Toast.makeText(MainActivity.this,getString(R.string.String),Toast.LENGTH_LONG).show();
                break;

            case R.id.bb :
                NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(this).setSmallIcon(android.R.drawable.sym_def_app_icon).setContentTitle("My notification")
                                .setContentText("hello");
                NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
                manager.notify(0, mBuilder.build());
                break;

            case R.id.cc :

                GetBiersServices.startActionBears(getApplicationContext());

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public class BierUpdate extends BroadcastReceiver {


        @Override
        public void onReceive(Context context, Intent intent){
            Toast.makeText(context,context.getString(R.string.dl),Toast.LENGTH_LONG).show();
            ((BiersAdapter) rv.getAdapter()).setNewBiere(getBiersFromFile());
        }
    }
}
