package com.sjbt.sdk.uparser.model.eq;

import java.io.Serializable;

public class EqConfigPoint implements Serializable {


    /* filter type shelf low, shelf high, peak/notch */
    private int filter_type;
    /* center frequency(peak/notch) or crossover frequency(shelving filter )*/
    private int fc;
    /* Gain -12~12 dB, Q3*/
    private float G;
    /* Qfactor, 0< ~ <=16, Q10*/
    private float Q_factor;
    /* state */
    private boolean disable;

    private int itemIndex;

    public EqConfigPoint() {
        this.filter_type = 0;
        this.fc = 0;
        this.G = 0;
        this.Q_factor = 0;
        this.disable = false;

        this.itemIndex = 0;
    }

    public int getFilter_type() {
        return filter_type;
    }

    public void setFilter_type(int filter_type) {
        this.filter_type = filter_type;
    }

    public int getFc() {
        return fc;
    }

    public void setFc(int fc) {
        this.fc = fc;
    }

    public float getG() {
        return G;
    }

    public void setG(float g) {
        G = g;
    }

    public float getQ_factor() {
        return Q_factor;
    }

    public void setQ_factor(float q_factor) {
        Q_factor = q_factor;
    }

    public boolean isDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }

    public int getItemIndex() {
        return itemIndex;
    }

    public void setItemIndex(int itemIndex) {
        this.itemIndex = itemIndex;
    }

    @Override
    public String toString() {
        return "EqConfigPoint{" +
                "filter_type=" + filter_type +
                ", fc=" + fc +
                ", G=" + G +
                ", Q_factor=" + Q_factor +
                ", disable=" + disable +
                ", itemIndex=" + itemIndex +
                '}';
    }
}
