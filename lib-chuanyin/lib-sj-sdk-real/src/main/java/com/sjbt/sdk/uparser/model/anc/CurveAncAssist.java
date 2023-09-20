package com.sjbt.sdk.uparser.model.anc;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 4条辅助曲线
 */
public class CurveAncAssist implements Serializable {
    public ArrayList<Double> freqList = new ArrayList<>();

    public ArrayList<Double> ampFFPPList = new ArrayList<>();
    public ArrayList<Double> ampFFSPList = new ArrayList<>();
    public ArrayList<Double> ampFFFBList = new ArrayList<>();
    public ArrayList<Double> ampFBSBList = new ArrayList<>(); //暂不用

    public ArrayList<Double> phaseFFPPList = new ArrayList<>();
    public ArrayList<Double> phaseFFSPList = new ArrayList<>();
    public ArrayList<Double> phaseFFFBList = new ArrayList<>();
    public ArrayList<Double> phaseFBSBList = new ArrayList<>(); //暂不用
}
