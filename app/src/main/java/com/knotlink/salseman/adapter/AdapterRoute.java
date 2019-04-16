package com.knotlink.salseman.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.knotlink.salseman.R;
import com.knotlink.salseman.activity.MapsActivity;
import com.knotlink.salseman.fragment.FragRouteActivity;
import com.knotlink.salseman.model.ModelShopList;
import com.knotlink.salseman.utils.Constant;

import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterRoute extends RecyclerView.Adapter<AdapterRoute.RouteViewHolder> {
    private Context tContext;
    private FragmentManager tFragmentManager;
    private List<ModelShopList> tModels;
    public AdapterRoute(Context tContext, FragmentManager tFragmentManager, List<ModelShopList> tModels) {
        this.tContext = tContext;
        this.tFragmentManager = tFragmentManager;
        this.tModels = tModels;
    }
    @NonNull
    @Override
    public RouteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.frag_route_item, viewGroup,false);
        return new RouteViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull final RouteViewHolder routeViewHolder, final int i) {
        final ModelShopList tModel = tModels.get(i);
        routeViewHolder.tvRoutePhoneNo.setText("Contact Number : "+tModel.getContactNo());
        routeViewHolder.tvRouteShopName.setText("Shop Name : "+tModel.getShopName());
//        ArrayList<String> arrayList = new ArrayList<>();
//        arrayList.add(String.valueOf(tModel.getInvoiceNo().indexOf(i)));
        final String strShopLocation = tModel.getShopAddress();
        routeViewHolder.btnRouteVisit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                routeViewHolder.btnRouteVisit.setBackgroundResource(R.drawable.bg_simple_green);
                routeViewHolder.btnRouteVisit.setBackgroundResource(R.drawable.bg_simple_transparent);
                tFragmentManager.beginTransaction().replace(R.id.container_main, FragRouteActivity.newInstance(tModels, i)).addToBackStack(null).commit();
            }
        });
        routeViewHolder.btnRouteNotVisit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                routeViewHolder.btnRouteNotVisit.setBackgroundResource(R.drawable.bg_simple_red);
                routeViewHolder.btnRouteVisit.setBackgroundResource(R.drawable.bg_simple_transparent);
                AlertDialog.Builder alert = new AlertDialog.Builder(tContext);

                final EditText edittext = new EditText(tContext);
                edittext.setBackgroundResource(R.drawable.bg_et);
                //  edittext.setMinLines(4);
                edittext.setSingleLine();
                FrameLayout container = new FrameLayout(tContext);
                FrameLayout.LayoutParams params = new  FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.leftMargin = tContext.getResources().getDimensionPixelSize(R.dimen.margin_top);
                params.rightMargin = tContext.getResources().getDimensionPixelSize(R.dimen.margin_top);
                params.topMargin = tContext.getResources().getDimensionPixelSize(R.dimen.margin_top);
                params.bottomMargin = tContext.getResources().getDimensionPixelSize(R.dimen.margin_top);
                edittext.setLayoutParams(params);
                container.addView(edittext);
                alert.setTitle("Mention the reason");
                alert.setView(container);
                alert.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //What ever you want to do with the value
                       // Editable YouEditTextValue = edittext.getText();
                        //OR
                        String strRemark = edittext.getText().toString();
                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // what ever you want to do with No option.
                    }
                });

                alert.show();
            }
        });
        routeViewHolder.ivRouteLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(tContext, MapsActivity.class);
                intent.putExtra(Constant.SHOP_LOCATION, strShopLocation);
                tContext.startActivity(intent);
            }
        });
        routeViewHolder.ivRouteDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(tContext)
                        .setTitle(tModel.getShopName())
                        .setMessage("Contact Name : "+tModel.getContactName()+"\n\n"+
                        "Contact No : "+tModel.getContactNo()+"\n\n"+
                         "Email Id : "+ tModel.getEmail()+"\n\n"+
                        "Address : "+tModel.getShopAddress())
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .create()
                        .show();
            }
        });
    }
    @Override
    public int getItemCount() {
        return tModels.size();
    }
     class RouteViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_route_phone_no)
        TextView tvRoutePhoneNo;
        @BindView(R.id.tv_route_shop_name)
        TextView tvRouteShopName;
        @BindView(R.id.btn_route_visit)
        Button btnRouteVisit;
        @BindView(R.id.btn_route_notVisit)
        Button btnRouteNotVisit;
        @BindView(R.id.iv_route_location)
        ImageView ivRouteLocation;
        @BindView(R.id.iv_route_details)
        ImageView ivRouteDetails;
        RouteViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
