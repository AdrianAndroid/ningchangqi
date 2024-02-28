package com.dongnaoedu.pagingserver.servlet;

/**
 * @author ningchuanqi
 * @description
 */
public class Movie {

    private int id;
    //电影名称
    private String title;
    //封面图片路径
    private String cover;
    //电影评分
    private String rate;

    public Movie() {
    }

    public Movie(int id, String title, String cover, String rate) {
        this.id = id;
        this.title = title;
        this.cover = cover;
        this.rate = rate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", cover='" + cover + '\'' +
                ", rate='" + rate + '\'' +
                '}';
    }
}
