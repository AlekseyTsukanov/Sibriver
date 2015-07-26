package ru.alex.sibrivertest.main;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import ru.alex.sibrivertest.R;
import ru.alex.sibrivertest.fragments.VPInflater;


public class MainActivity extends ActionBarActivity implements AdapterView.OnItemSelectedListener {

    private Toolbar toolbar;
    private Spinner spinnerNav;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        spinnerNav = (Spinner)findViewById(R.id.spinnerNav);
        ArrayAdapter<CharSequence> taskAdapter = ArrayAdapter.createFromResource(context, R.array.items, R.layout.custom_spinner);
        taskAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerNav.setAdapter(taskAdapter);
        spinnerNav.setOnItemSelectedListener(this);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = new VPInflater();
        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_settings){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast toast = null;
        String title = "";
        switch (position){
            case 0:
                title = spinnerNav.getSelectedItem().toString();
                break;
            case 1:
                title = spinnerNav.getSelectedItem().toString();
                break;
            case 2:
                title = spinnerNav.getSelectedItem().toString();
                break;
            case 3:
                title = spinnerNav.getSelectedItem().toString();
                break;
            case 4:
                title = spinnerNav.getSelectedItem().toString();
                break;
        }
        toast = Toast.makeText(context, title, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
