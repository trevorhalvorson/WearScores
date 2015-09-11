package com.trevorhalvorson.wearscores;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by trevo on 9/10/2015.
 */
public class Week {
    private Integer w;
    private List<Game> gms = new ArrayList<Game>();
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * @return The week
     */
    public Integer getW() {
        return w;
    }

    /**
     * @param w The week
     */
    public void setW(Integer w) {
        this.w = w;
    }

    /**
     * @return The games
     */
    public List<Game> getGms() {
        return gms;
    }

    /**
     * @param gms The games
     */
    public void setGms(List<Game> gms) {
        this.gms = gms;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}