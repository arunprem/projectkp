package com.keralapolice.projectk.admin.rank.vo;

import javax.validation.constraints.NotBlank;

public class RankVo {

    @NotBlank
    private String id;

    @NotBlank
    private String postDesc;

    @NotBlank
    private String catId;
    @NotBlank
    private String rankId;

    @NotBlank
    private String status;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPostDesc() {
        return postDesc;
    }

    public void setPostDesc(String postDesc) {
        this.postDesc = postDesc;
    }

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    public String getRankId() {
        return rankId;
    }

    public void setRankId(String rankId) {
        this.rankId = rankId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
