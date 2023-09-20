package com.sjbt.sdk.uparser.model.anc;

import java.io.Serializable;
import java.util.ArrayList;

public class AncCalRespParam extends BaseAncCalRespParam implements Serializable {

    //得到前馈（通透）的参考曲线需要传递的数据
    public ArrayList<Double> reqFreqList = new ArrayList<>();
    public ArrayList<Double> reqAmpList = new ArrayList<>();
    public int delay;
    public double objGain;
    /* minimum_phase ? */
    public int minimumPhase = 1;
}
