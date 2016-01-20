package com.yw.gamebar;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UpdateConfig;
import com.wandoujia.ads.sdk.Ads;
import com.yw.mylibrary.EuclidActivity;
import com.yw.mylibrary.EuclidListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends EuclidActivity {

    public static  String BASE_URL = "http://shld.applinzi.com/yw/";
    public static String URl = BASE_URL+ "shld/shld.html";
//    public static String URl = "http://192.168.201.31:8080/shld/yw/shld.html";
    public static String URl2 = BASE_URL + "ppl/hdppl.html";
    public static String URl3 = BASE_URL+ "yxtx/yxtx.html";
    private static final String APP_ID = "100036902";
    private static final String SECRET_KEY = "c1b5edfc7677e29796c428067d80f07c";
    private static final String BANNER = "451473c51f254907682078b08a4db5d8";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UmengUpdateAgent.update(this);
        UpdateConfig.setDebug(false);
//        setContentView(R.layout.activity_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        new AsyncTask<Void,Void,Boolean>(){

            @Override
            protected Boolean doInBackground(Void... params) {
                try{


                    Ads.init(MainActivity.this, APP_ID, SECRET_KEY);

                    return true;
                }catch (Exception e){

                }
                return false;
            }

            @Override
            protected void onPostExecute(Boolean success) {
                final ViewGroup container = (ViewGroup) findViewById(R.id.banner_container);

                if (success) {
                    /**
                     * pre load
                     */
                    Ads.preLoad(BANNER, Ads.AdFormat.banner);


                    /**
                     * add ad views
                     */
                    View bannerView = Ads.createBannerView(MainActivity.this, BANNER);
                    container.addView(bannerView, new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    ));


                } else {
                    TextView errorMsg = new TextView(MainActivity.this);
                    errorMsg.setText("init failed");
                    container.addView(errorMsg);
                }
            }
        }.execute();

        mButtonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "Oh hi!", Toast.LENGTH_SHORT).show();
                if(URLProfile .equals(URl2)){

                    Intent browserIntent= new Intent(Intent.ACTION_VIEW, Uri.parse(URLProfile));
                    startActivity(browserIntent);
                }else{

                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this,GameWebActivity.class);
                    intent.putExtra("play_url",URLProfile);
                    startActivity(intent);
                }

            }

        });
    }



    @Override
    protected BaseAdapter getAdapter() {
        Map<String, Object> profileMap;
        List<Map<String, Object>> profilesList = new ArrayList<>();

        int[] avatars = {
                R.drawable.icon_ppl,
                R.drawable.icon_yxtx,
                R.drawable.icon

                };
        String[] names = getResources().getStringArray(R.array.array_names);

        String[] shortDes = getResources().getStringArray(R.array.array_lorem_ipsum_short);
        String[] fullDes = getResources().getStringArray(R.array.array_lorem_ipsum_long);
        String[] urlarrs = {URl2,URl3,URl};

        for (int i = 0; i < avatars.length; i++) {
            profileMap = new HashMap<>();
            profileMap.put(EuclidListAdapter.KEY_AVATAR, avatars[i]);
            profileMap.put(EuclidListAdapter.KEY_NAME, names[i]);
            profileMap.put(EuclidListAdapter.KEY_DESCRIPTION_SHORT,shortDes[i]);
            profileMap.put(EuclidListAdapter.KEY_DESCRIPTION_FULL,fullDes[i]);
            profileMap.put(EuclidListAdapter.KEY_URL,urlarrs[i]);
            profilesList.add(profileMap);
        }

        return new EuclidListAdapter(this, R.layout.list_item, profilesList);
    }

}
