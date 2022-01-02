package com.little_bird.movieapp.model;

import java.util.List;

public class ListOfMovie {

    private String title;
    private List<Result> resultList = null;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Result> getResultList() {
        return resultList;
    }

    public void setResultList(List<Result> resultList) {
        this.resultList = resultList;
    }

    public ListOfMovie(String title, List<Result> resultList) {
        this.title = title;
        this.resultList = resultList;
    }
}
