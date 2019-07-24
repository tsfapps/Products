package com.knotlink.salseman.utils;

import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class SetSpinnerValue {

    public static void setSpinner(Spinner tSpinner, String tString){

        ArrayAdapter tArrAdapter = (ArrayAdapter) tSpinner.getAdapter(); //cast to an ArrayAdapter
        int spinnerPosition = tArrAdapter.getPosition(tString);
        tSpinner.setSelection(spinnerPosition);

    }

}
