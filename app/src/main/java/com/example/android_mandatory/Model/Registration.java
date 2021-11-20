package com.example.android_mandatory.Model;

import java.util.Date;

public class Registration {
    Date registrationDate;
    long intensity;


    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public long getIntensity() {
        return intensity;
    }

    public void setIntensity(long intensity) {
        this.intensity = intensity;
    }
}
