package com.sjbt.sdk.uparser.model.anc;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Anc参考曲线
 */
public class CurveAncReference implements Serializable {
    public ArrayList<Double> freqList = new ArrayList<>();
    public ArrayList<Double> ampList = new ArrayList<>();
    public ArrayList<Double> phaseList = new ArrayList<>();
    public ArrayList<Double> complexList = new ArrayList<>();

}
