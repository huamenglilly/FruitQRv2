package kiky.beam.lilly.th.ac.rmutk.fruitqr;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailServiceFragment extends Fragment {

    private  String nameString, dateString, amountString, unitString, imageString, idUserString;


    public DetailServiceFragment() {
        // Required empty public constructor
    }


    public static DetailServiceFragment detailServiceInstance(String name, String date, String amount, String unit, String image, String idUser) {
        //รับค่ามาจาก Activity
        DetailServiceFragment detailServiceFragment = new DetailServiceFragment();
        Bundle bundle = new Bundle();
        bundle.putString("Name", name);
        bundle.putString("Date", date);
        bundle.putString("Amount", amount);
        bundle.putString("Unit", unit);
        bundle.putString("Image", image);
        bundle.putString("idUser", idUser);
        detailServiceFragment.setArguments(bundle);
        return  detailServiceFragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        showView();

        createToolbar();

        showShop();


    }

    private void showShop() { //เรียกค่าของ ชื่อร้าน ที่อยู่ เบอร์

        Myconstant myconstant = new Myconstant();

        try {

            GetDataWhereOneColumn getDataWhereOneColumn1 = new GetDataWhereOneColumn(getActivity());
            getDataWhereOneColumn1.execute("id", idUserString, myconstant.getUrlGetUserWhereId());
            String json2 = getDataWhereOneColumn1.get();
            Log.d("2FebV2", "json2 ==>" + json2);

            JSONArray jsonArray1 = new JSONArray(json2);
            JSONObject jsonObject1 = jsonArray1.getJSONObject(0);

            TextView nameShopTextView = getView().findViewById(R.id.txtShop);
            nameShopTextView.setText(jsonObject1.getString("Name"));

            TextView addressTextView = getView().findViewById(R.id.txtaddress);
            addressTextView.setText(jsonObject1.getString("Address"));

            TextView phoneTextView = getView().findViewById(R.id.txtphone);
            phoneTextView.setText(jsonObject1.getString("Phone"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createToolbar() {
        Toolbar toolbar = getView().findViewById(R.id.toolabarDetail);
        ((DetailServiceActivity)getActivity()).setSupportActionBar(toolbar);
        ((DetailServiceActivity)getActivity()).getSupportActionBar().setTitle("Detail");
        ((DetailServiceActivity)getActivity()).getSupportActionBar().setHomeButtonEnabled(true); //ทำปุ่มลูกศรย้อนกลับ
        ((DetailServiceActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true); //ทำให้ลูกศรมันมีวาปๆ
        toolbar.setNavigationOnClickListener(new View.OnClickListener() { //ทำปุ่มย้อนกลับไปหน้าที่เริ่มคือ QR
            @Override
            public void onClick(View v) {

            getActivity().finish();
            }
        });

    }

    private void showView() {
        nameString = getArguments().getString("Name");
        dateString = getArguments().getString("Date");
        amountString = getArguments().getString("Amount");
        unitString = getArguments().getString("Unit");
        imageString = getArguments().getString("Image");
        idUserString = getArguments().getString("idUser");

        //โชว์
        TextView nameTextView = getView().findViewById(R.id.txtname);
        nameTextView.setText(nameString);

        TextView dateTextView = getView().findViewById(R.id.txtdate);
        dateTextView.setText(dateString);

        TextView amountTextView = getView().findViewById(R.id.txtamount);
        amountTextView.setText(amountString);

        TextView unitTextView = getView().findViewById(R.id.txtunit);
        unitTextView.setText(unitString);

        ImageView imageView = getView().findViewById(R.id.imageDetail);
        Picasso.get().load(imageString).into(imageView);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

}
