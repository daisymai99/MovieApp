package com.little_bird.movieapp.model;

import java.util.List;

// topic of movie
public class AllCategory {
    private String categoryTitle;
    private int categoryId;
    private List<CategoryItem> mItemList= null;

    public List<CategoryItem> getmItemList() {
        return mItemList;
    }

    public void setmItemList(List<CategoryItem> mItemList) {
        this.mItemList = mItemList;
    }

    public AllCategory(String categoryTitle, int categoryId, List<CategoryItem> mItemList) {
        this.categoryTitle = categoryTitle;
        this.categoryId = categoryId;
        this.mItemList = mItemList;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}
