package com.zhu.qinxiang.bombtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zhu.qinxiang.bombtest.data.Person;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class MainActivity extends AppCompatActivity {

    private EditText mNameEdt;
    private EditText mAddressEdt;
    private Button mLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 初始化Bomb
        Bmob.initialize(this, "bc314b35e7ec060ebd303a0376add80e");

        mNameEdt = findViewById(R.id.name);
        mAddressEdt = findViewById(R.id.address);
        mLogin = findViewById(R.id.login);
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mNameEdt.getText().toString();
                String address = mAddressEdt.getText().toString();
                addPerson(name, address);
            }
        });
        findViewById(R.id.query).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queryPerson("0fb5466f11");
            }
        });

        findViewById(R.id.alter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address = mAddressEdt.getText().toString();
                alterPerson("0fb5466f11", address);
            }
        });


    }

    private void addPerson(String name, String address) {
        Person p2 = new Person();
        p2.setName(name);
        p2.setAddress(address);
        p2.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if(e==null){
                    toast("添加数据成功，返回objectId为："+objectId);
                }else{
                    toast("创建数据失败：" + e.getMessage());
                }
            }
        });
    }

    private void queryPerson(final String objectId) {
        BmobQuery<Person> bmobQuery = new BmobQuery<Person>();
        bmobQuery.getObject(objectId, new QueryListener<Person>() {
            @Override
            public void done(Person object,BmobException e) {
                if(e==null){
                    toast("查询成功" + object.getName() + object.getAddress());
                }else{
                    toast("查询失败：" + e.getMessage());
                }
            }
        });
    }

    private void alterPerson(String objectId, String address) {
        final Person p2 = new Person();
        p2.setAddress(address);
        p2.update(objectId, new UpdateListener() {

            @Override
            public void done(BmobException e) {
                if(e==null){
                    toast("更新成功:" + p2.getUpdatedAt());
                }else{
                    toast("更新失败：" + e.getMessage());
                }
            }

        });
    }

    private void deletePerson(String objectId) {
        final Person p2 = new Person();
        p2.setObjectId(objectId);
        p2.delete(new UpdateListener() {

            @Override
            public void done(BmobException e) {
                if(e==null){
                    toast("删除成功:"+p2.getUpdatedAt());
                }else{
                    toast("删除失败：" + e.getMessage());
                }
            }

        });
    }

    private void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
