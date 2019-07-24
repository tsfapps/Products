package com.knotlink.salseman.utils;

import android.widget.Button;

public class ButtonBg {
    public static void setNotVisitButtonBg(Button tButtonVisit, int btnVistBg, Button tButtonNotVisit,
                                           int btnNotVisitBg, boolean visit, boolean notVisit){
        tButtonVisit.setBackgroundResource(btnVistBg);
        tButtonVisit.setEnabled(visit);
        tButtonNotVisit.setBackgroundResource(btnNotVisitBg);
        tButtonNotVisit.setEnabled(notVisit);

    }
}
