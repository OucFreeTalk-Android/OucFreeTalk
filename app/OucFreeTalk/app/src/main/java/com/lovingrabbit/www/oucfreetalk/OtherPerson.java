package com.lovingrabbit.www.oucfreetalk;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lovingrabbit.www.oucfreetalk.personAdapter.Person;
import com.lovingrabbit.www.oucfreetalk.personAdapter.PersonAdapter;
import com.lovingrabbit.www.oucfreetalk.untils.GetPostAysncTaskLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OtherPerson extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>{
    String userId,nikename,intro;
    RecyclerView recyclerView;
    LoaderManager loaderManager;
    TextView person_user,introd;
    LinearLayout noPerson_Post;
    PersonAdapter adapter;
    List<Person> personList = new ArrayList<Person>();
    private String GET_PERSON_POST_URL = "http://47.93.222.179/oucfreetalk/getPostPerson?id=";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.other_person);
        Intent intent =getIntent();
        userId = intent.getStringExtra("owner");

        recyclerView = (RecyclerView) findViewById(R.id.other_person_rcy);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new PersonAdapter(personList);
        recyclerView.setAdapter(adapter);
        GET_PERSON_POST_URL = GET_PERSON_POST_URL +userId;

        person_user = (TextView) findViewById(R.id.other_person_username);
        introd = (TextView) findViewById(R.id.other_person_description);
        noPerson_Post = (LinearLayout) findViewById(R.id.other_person_noNet);
        loaderManager = getLoaderManager();
        loaderManager.initLoader(1,null,OtherPerson.this);

    }
    public void update(String get_result) throws JSONException {
        Log.e("result", get_result);
        JSONObject jsonObject = new JSONObject(get_result);
        int allpage = jsonObject.getInt("allpage");
        if (allpage == 0) {
            noPerson_Post.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
        nikename = jsonObject.getString("nikename");
        intro = jsonObject.getString("intro");
        JSONArray searchJson = jsonObject.getJSONArray("search");
        for (int i = allpage - 1; i >= 0; i--) {
            JSONObject talk = searchJson.getJSONObject(i);
            String title = talk.getString("title");
            String context = talk.getString("content");
            int id = talk.getInt("id");
            String owner = talk.getString("owner");
            String time = talk.getString("createtime");
            Person person = new Person(R.drawable.nav_icon, R.drawable.apple, title, nikename, context, id, owner, time, intro);
            personList.add(person);
        }
        person_user.setText(nikename);
        introd.setText(intro);
        SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("nikename", nikename);
        editor.commit();
    }
    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        return new GetPostAysncTaskLoader(this,GET_PERSON_POST_URL);
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        personList.clear();
        try {
            update(data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }
}
