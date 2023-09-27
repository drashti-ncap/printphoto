package com.mobile.cover.photo.editor.back.maker.Pagination.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TopRatedMovies {

//    @SerializedName("page")
//    @Expose
//    private Integer page;
//    @SerializedName("results")
//    @Expose
//    private List<Result> results = new ArrayList<Result>();
//    @SerializedName("total_results")
//    @Expose
//    private Integer totalResults;
//    @SerializedName("total_pages")
//    @Expose
//    private Integer totalPages;
//
//    /**
//     *
//     * @return
//     * The page
//     */
//    public Integer getPage() {
//        return page;
//    }
//
//    /**
//     *
//     * @param page
//     * The page
//     */
//    public void setPage(Integer page) {
//        this.page = page;
//    }
//
//    /**
//     *
//     * @return
//     * The results
//     */
//    public List<Result> getResults() {
//        return results;
//    }
//
//    /**
//     *
//     * @param results
//     * The results
//     */
//    public void setResults(List<Result> results) {
//        this.results = results;
//    }
//
//    /**
//     *
//     * @return
//     * The totalResults
//     */
//    public Integer getTotalResults() {
//        return totalResults;
//    }
//
//    /**
//     *
//     * @param totalResults
//     * The total_results
//     */
//    public void setTotalResults(Integer totalResults) {
//        this.totalResults = totalResults;
//    }
//
//    /**
//     *
//     * @return
//     * The totalPages
//     */
//    public Integer getTotalPages() {
//        return totalPages;
//    }
//
//    /**
//     *
//     * @param totalPages
//     * The total_pages
//     */
//    public void setTotalPages(Integer totalPages) {
//        this.totalPages = totalPages;
//    }

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("total_pages")
    @Expose
    private Integer totalPages;
    @SerializedName("result")
    @Expose
    private List<Result> result = null;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

}
