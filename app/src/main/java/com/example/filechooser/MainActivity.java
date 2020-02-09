package com.example.filechooser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager linearLayoutManager;
    private RecyclerView.LayoutManager gridLayoutManager;
    private RecyclerView.LayoutManager layoutManager;
    private File here;
    private TextView pointer;
    private ImageView toggle;

    private final String TAG = "Naver";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // File[]를 통으로 넘겨주는게 좋다.
        // 굳이 처리하여 제한된 정보만 넘겨주는 것보다 통으로 넘기는게 좋음.

        linearLayoutManager = new LinearLayoutManager(this);
        gridLayoutManager = new GridLayoutManager(this,3);
        layoutManager = linearLayoutManager;

        initView();

        setRecyclerData(Environment.getRootDirectory().toString());
    }

    private void initView(){
        toggle = findViewById(R.id.main_layout_change);
        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(layoutManager == linearLayoutManager){
                    toggle.setActivated(true);
                    layoutManager = gridLayoutManager;
                    mAdapter.setLayoutType(ItemType.LAYOUT_GRID);
                }
                else{
                    toggle.setActivated(false);
                    layoutManager = linearLayoutManager;
                    mAdapter.setLayoutType(ItemType.LAYOUT_LINEAR);
                }
                recyclerView.setLayoutManager(layoutManager);

            }
        });

        //상단 백버튼
        findViewById(R.id.goBack).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                goBack();
            }
        });

        //탐색 위치
        pointer = findViewById(R.id.pointer);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void goBack() {
        if(here.getAbsolutePath().equals(Environment.getRootDirectory().toString())){
            finish();
        }else{
            File parentFile = here.getParentFile();
            String pointerTextNow = pointer.getText().toString();
            pointer.setText(pointerTextNow.substring(0, pointerTextNow.length() - (here.getName().length() + 3)));
            here = parentFile;
            mAdapter.setDataList(parentFile.listFiles());
        }
    }

    private void setRecyclerData(String filePath){
        File[]  files = new File(filePath).listFiles();
        mAdapter = new RecyclerAdapter();
        recyclerView.setAdapter(mAdapter);
        mAdapter.setDataList(files);
        mAdapter.setOnItemClickListener(itemClickListener);
    }

    private RecyclerAdapter.OnItemClickListener itemClickListener
            = new RecyclerAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(File data) {
            if (data.isDirectory() && data.listFiles() != null){
                File[] files = data.listFiles();
                here = data;
                pointer.append(" > "+here.getName());
                mAdapter.setDataList(files);
            }
        }
    };

    //기존의 백버튼
    @Override
    public void onBackPressed() {
        goBack();
    }
}
