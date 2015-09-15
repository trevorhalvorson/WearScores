package com.trevorhalvorson.wearscores;

import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by trevo on 9/10/2015.
 */
public class Game implements Serializable {

    private Integer hs;
    private String d;
    private Integer gsis;
    private Integer vs;
    private Integer eid;
    private String h;
    private String ga;
    private Integer rz;
    private String v;
    private String vnn;
    private String t;
    private String q;
    private String hnn;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * @return The home team score
     */
    public Integer getHs() {
        return hs;
    }

    /**
     * @param hs The home team score
     */
    public void setHs(Integer hs) {
        this.hs = hs;
    }

    /**
     * @return The day of the week
     */
    public String getD() {
        return d;
    }

    /**
     * @param d The day of the week
     */
    public void setD(String d) {
        this.d = d;
    }

    /**
     * @return The gsis
     */
    public Integer getGsis() {
        return gsis;
    }

    /**
     * @param gsis The gsis
     */
    public void setGsis(Integer gsis) {
        this.gsis = gsis;
    }

    /**
     * @return The visiting team score
     */
    public Integer getVs() {
        return vs;
    }

    /**
     * @param vs The visiting team score
     */
    public void setVs(Integer vs) {
        this.vs = vs;
    }

    /**
     * @return The eid
     */
    public Integer getEid() {
        return eid;
    }

    /**
     * @param eid The eid
     */
    public void setEid(Integer eid) {
        this.eid = eid;
    }

    /**
     * @return The home team abbreviation
     */
    public String getH() {
        return h;
    }

    /**
     * @param h The home team abbreviation
     */
    public void setH(String h) {
        this.h = h;
    }

    /**
     * @return The ga
     */
    public String getGa() {
        return ga;
    }

    /**
     * @param ga The ga
     */
    public void setGa(String ga) {
        this.ga = ga;
    }

    /**
     * @return The rz
     */
    public Integer getRz() {
        return rz;
    }

    /**
     * @param rz The rz
     */
    public void setRz(Integer rz) {
        this.rz = rz;
    }

    /**
     * @return The visiting team abbreviation
     */
    public String getV() {
        return v;
    }

    /**
     * @param v The visiting team abbreviation
     */
    public void setV(String v) {
        this.v = v;
    }

    /**
     * @return The visiting team mascot
     */
    public String getVnn() {
        return vnn;
    }

    /**
     * @param vnn The visiting team mascot
     */
    public void setVnn(String vnn) {
        this.vnn = vnn;
    }

    /**
     * @return The time
     */
    public String getT() {
        return t;
    }

    /**
     * @param t The time
     */
    public void setT(String t) {
        this.t = t;
    }

    /**
     * @return The quarter (F - finished, FO - finished overtime, P - pending, 1 - Q1, etc.)
     */
    public String getQ() {
        return q;
    }

    /**
     * @param q The quarter (F - finished, FO - finished overtime, P - pending, 1 - Q1, etc.)
     */
    public void setQ(String q) {
        this.q = q;
    }

    /**
     * @return The home team mascot
     */
    public String getHnn() {
        return hnn;
    }

    /**
     * @param hnn The home team mascot
     */
    public void setHnn(String hnn) {
        this.hnn = hnn;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public PutDataRequest toPutDataRequest() {
        PutDataMapRequest request = PutDataMapRequest.create("/game/");
        DataMap dataMap = request.getDataMap();
        dataMap.putString("game", d);
        return request.asPutDataRequest();
    }
}