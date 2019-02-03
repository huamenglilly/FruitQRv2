package kiky.beam.lilly.th.ac.rmutk.fruitqr;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShowListFragment extends Fragment {

    //ตัวแปร
    private int typeUserAnInt;
    public ShowListFragment() {
        // Required empty public constructor

    }

    public  static  ShowListFragment showListInstance(int index) {//คอยรับค่า admin farmer
        ShowListFragment showListFragment = new ShowListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("TypeUser", index);
        showListFragment.setArguments(bundle);
        return  showListFragment;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        typeUserAnInt = getArguments().getInt("TypeUser", 0); //ถ้ารับค่าไม่ได้ให้ใส่เลข 0 มาแทน
        Log.d("3FebV1","typeUser ==> " + typeUserAnInt);

        RecyclerView recyclerView = getView().findViewById(R.id.recyclerShowList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        Myconstant myconstant = new Myconstant();//ประกาศตัวแปรนอก switch
        final ArrayList<String> nameStringArrayList = new ArrayList<>();
        final ArrayList<String> dateStringArrayList = new ArrayList<>();
        final ArrayList<String> amountStringArrayList = new ArrayList<>();
        final ArrayList<String> unitStringArrayList = new ArrayList<>();
        final ArrayList<String> imageStringArrayList = new ArrayList<>();

        //user
        final ArrayList<String> idUserStringArrayList = new ArrayList<>();



        switch (typeUserAnInt) {
            case 1:
                try {
                    GetAllDataThread getAllDataThread = new GetAllDataThread(getActivity());
                    getAllDataThread.execute(myconstant.getUrlGetDetail());
                    String json = getAllDataThread.get();
                    Log.d("3FebV1", "json ==> " + json);

                    JSONArray jsonArray = new JSONArray(json);
                    for (int i = 0; i < jsonArray.length(); i += 1) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        nameStringArrayList.add(jsonObject.getString("Name"));
                        dateStringArrayList.add(jsonObject.getString("Date"));
                        amountStringArrayList.add(jsonObject.getString("Amount"));
                        unitStringArrayList.add(jsonObject.getString("Unit"));
                        imageStringArrayList.add(jsonObject.getString("Image"));
                        idUserStringArrayList.add(jsonObject.getString("idUser"));

                    }

                    ShowListAdapter showListAdapter = new ShowListAdapter(getActivity()//ถ้าอยากรู้ให้ทำอะไรในวงเล็บให้กด ctrl+P
                            , nameStringArrayList, dateStringArrayList, amountStringArrayList, unitStringArrayList, imageStringArrayList,
                            new OnClickItem() {
                        @Override
                        public void onClickItem(View view, int position) {
                            //โยนค่าไปอีกหน้าเลย
                            Intent intent = new Intent(getActivity(), DetailServiceActivity.class);
                            intent.putExtra("Name",nameStringArrayList.get(position));
                            intent.putExtra("Date",dateStringArrayList.get(position));
                            intent.putExtra("Amount",amountStringArrayList.get(position));
                            intent.putExtra("Unit",unitStringArrayList.get(position));
                            intent.putExtra("Image",imageStringArrayList.get(position));

                            intent.putExtra("idUser", idUserStringArrayList.get(position));
                            startActivity(intent);



                        }
                    });

                    recyclerView.setAdapter(showListAdapter);

                } catch (Exception e) {
                    e.printStackTrace();
                }


                break;
        }

    } //Main Mathod เมธอดหลัก

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_show_list, container, false);
    }

}
