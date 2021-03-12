package com.example.kursovaya.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataModel {

    @SerializedName("page")
    @Expose
    public Integer page;
    
    @SerializedName("total_results")
    @Expose
    public Integer totalResults;
    
    @SerializedName("total_pages")
    @Expose
    public Integer totalPages;
    
    @SerializedName("results")
    @Expose
    public List<Result> results = null;


    public List<Result> getResults() {
        return results;
    }
}
