package kiky.beam.lilly.th.ac.rmutk.fruitqr;


import android.os.Binder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
public class DetailFragment extends Fragment {

    private  String qrCode, nameString, imageString, amountString, unitString, dateString, idShopString, nameShopString, addressString, phoneString;

    public DetailFragment() {
        // Required empty public constructor
    }

    public static DetailFragment detailFragment(String resuliQR) {

        DetailFragment detailFragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("QRcode", resuliQR);
        detailFragment.setArguments(bundle);
        return detailFragment;



    }

    //รับค่า QR เพื่อจะเอาไป where
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        showView();


    } //Main Method

    private void showView() {
        qrCode = getArguments().getString("QRcode");
        if (!qrCode.isEmpty()) {

//            Have QR codeValue
            try {

                Myconstant myconstant = new Myconstant();
                GetDataWhereOneColumn getDataWhereOneColumn = new GetDataWhereOneColumn(getActivity());
                getDataWhereOneColumn.execute("QR", qrCode, myconstant.getUrlGetDataWhereQR());

                String json = getDataWhereOneColumn.get();
                Log.d("2FebV1", "json ==> " + json);

                JSONArray jsonArray = new JSONArray(json);
                JSONObject jsonObject = jsonArray.getJSONObject(0);

                nameString = jsonObject.getString("Name");
                imageString = jsonObject.getString("Image");
                idShopString = jsonObject.getString("idUser");
                amountString = jsonObject.getString("Amount");
                unitString = jsonObject.getString("Unit");

                TextView nameTextView = getView().findViewById(R.id.txtname);
                nameTextView.setText(nameString);

                ImageView imageView = getView().findViewById(R.id.imageDetail);
                Picasso.get().load(imageString).into(imageView);






            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

}