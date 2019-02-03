package kiky.beam.lilly.th.ac.rmutk.fruitqr;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailServiceFragment extends Fragment {

    private  String nameString, dateString, amountString, unitString, imageString;


    public DetailServiceFragment() {
        // Required empty public constructor
    }


    public static DetailServiceFragment detailServiceInstance(String name, String date, String amount, String unit, String image) {
        //รับค่ามาจาก Activity
        DetailServiceFragment detailServiceFragment = new DetailServiceFragment();
        Bundle bundle = new Bundle();
        bundle.putString("Name", name);
        bundle.putString("Date", date);
        bundle.putString("Amount", amount);
        bundle.putString("Unit", unit);
        bundle.putString("Image", image);
        detailServiceFragment.setArguments(bundle);
        return  detailServiceFragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        nameString = getArguments().getString("Name");
        dateString = getArguments().getString("Date");
        amountString = getArguments().getString("Amount");
        unitString = getArguments().getString("Unit");
        imageString = getArguments().getString("Image");

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
