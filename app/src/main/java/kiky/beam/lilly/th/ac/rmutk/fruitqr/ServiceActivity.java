package kiky.beam.lilly.th.ac.rmutk.fruitqr;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONObject;

public class ServiceActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle; //ทำการเชื่อม  toolbar กับ ActionBarDrawerToggle
    private  String idString,nameUserString, typeUserString; //เพิ่มตัวแปร

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        getUser();// Method ดึงUser มาทำงาน

    } //Main Method เมธรอดหลัก

    private void showList() { //ทำงานหลังจากดึงค่า
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.contentServiceFragment, ShowListFragment.showListInstance(Integer.parseInt(typeUserString))).commit();

    }

    private void createToobar() {
        Toolbar toolbar = findViewById(R.id.toobarService);
        setSupportActionBar(toolbar);
        getSupportActionBar().setSubtitle(nameUserString + " " + showType(typeUserString)); // ดึงค่าชื่อของ User และ สถานะ มาบทแถบ Toobar


        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_action_hamberger); //ทำแถบเมณูเป็น Hamberger

//        Create Hamberger Icon
        drawerLayout = findViewById(R.id.layoutDrawerLayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(ServiceActivity.this, drawerLayout, R.string.open,R.string.close); //ต้องใส่ค่า open และ close ใน string
    }

    private String showType(String typeUserString) {

        String[] strings = {"", "Admin", "Farmer", "Product", "Customer"};
        try {

            int index = Integer.parseInt(typeUserString.trim());//trim() ตัดช่องว่างข้างหน้ากับตัดข้างหลัง
            return strings[index];

        } catch (Exception e) {
            e.printStackTrace();
            return  ""; //ป้องกันการเออเร่อ
        }
    }

    //ดึงค่าชื่อของ User มาบทแถบ Toobar
    private void getUser() {
        idString = getIntent().getStringExtra("id"); //ส่งค่ามาจาก MainFragment
        try {

            Myconstant myconstant = new Myconstant();
            GetDataWhereOneColumn getDataWhereOneColumn = new GetDataWhereOneColumn(ServiceActivity.this);
            getDataWhereOneColumn.execute("id", idString, myconstant.getUrlGetUserWhereId());
            String json = getDataWhereOneColumn.get();

            JSONArray jsonArray = new JSONArray(json);
            JSONObject jsonObject = jsonArray.getJSONObject(0);

            nameUserString = jsonObject.getString("Name");
            typeUserString = jsonObject.getString("TypeUser");

//        Create Toobar

            createToobar();

            showList(); //ต้องโหลดค่าอื่น ๆ ก่อน ถ้าทำพร้อม getUser จะเกิดค่าว่างเปล่า จึงมาอยู่ใน เมธอด getUser()


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_service,menu);

        return super.onCreateOptionsMenu(menu);
    }

    //ทำแถบเมณูที่เปิดออกมาจากด้านซ้ายมือ
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.itemQR){
            Intent intent = new Intent(ServiceActivity.this, QRActivity.class);
            intent.putExtra("Login", false);
            startActivity(intent);

        }

        if(item.getItemId() == R.id.itemExit){
            Log.d("7janv1","you click");
            finish();
        }
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }    //ทำแถบเมณูถึงนี่

} //Main Class
