package com.trevorhalvorson.wearscores;

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
     * @return The hs
     */
    public Integer getHs() {
        return hs;
    }

    /**
     * @param hs The hs
     */
    public void setHs(Integer hs) {
        this.hs = hs;
    }

    /**
     * @return The d
     */
    public String getD() {
        return d;
    }

    /**
     * @param d The d
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
     * @return The vs
     */
    public Integer getVs() {
        return vs;
    }

    /**
     * @param vs The vs
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
     * @return The h
     */
    public String getH() {
        return h;
    }

    /**
     * @param h The h
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
     * @return The v
     */
    public String getV() {
        return v;
    }

    /**
     * @param v The v
     */
    public void setV(String v) {
        this.v = v;
    }

    /**
     * @return The vnn
     */
    public String getVnn() {
        return vnn;
    }

    /**
     * @param vnn The vnn
     */
    public void setVnn(String vnn) {
        this.vnn = vnn;
    }

    /**
     * @return The t
     */
    public String getT() {
        return t;
    }

    /**
     * @param t The t
     */
    public void setT(String t) {
        this.t = t;
    }

    /**
     * @return The q
     */
    public String getQ() {
        return q;
    }

    /**
     * @param q The q
     */
    public void setQ(String q) {
        this.q = q;
    }

    /**
     * @return The hnn
     */
    public String getHnn() {
        return hnn;
    }

    /**
     * @param hnn The hnn
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

}