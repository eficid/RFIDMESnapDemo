package com.eficid.rfidmesnap.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.eficid.rfidscanner.rfidmesnap.demo.R;

import java.util.ArrayList;

public class MainActivity extends ActionBarActivity {
    private final String ACTION_RFID_SCAN = "eficid.intent.action.RFID_SCAN";

    private final int SCAN = 0;

    private ArrayAdapter<String> tagAdapter;
    public ListView listViewInventory;
    public ArrayList<String> tagList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listViewInventory = (ListView) findViewById(R.id.inventory_list);
        tagAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tagList);
        listViewInventory.setAdapter(tagAdapter);
        tagAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putStringArrayList("TAG_LIST", (ArrayList<String>)tagList.clone());
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        tagList.addAll(savedInstanceState.getStringArrayList("TAG_LIST"));
        tagAdapter.notifyDataSetChanged();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == SCAN) {
            if (resultCode == RESULT_OK) {
                tagList.clear();
                tagList.addAll(intent.getStringArrayListExtra("RESULT"));
                tagAdapter.notifyDataSetChanged();
            } else {
                tagList.clear();
                tagAdapter.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(), R.string.result_is_not_ok, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void scan(View v) {
        try {
            Intent intent = new Intent();
            intent.setAction(ACTION_RFID_SCAN);
            startActivityForResult(intent, SCAN);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
