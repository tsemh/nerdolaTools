package com.tsemh.nerdolaTools.Model.entity;

import lombok.Getter;

@Getter
public class DownloadRequest {
    private String url;
    private String nameZip;
    public void setUrl(String url) { this.url = url; }
    public void setNameZip(String nameZip) { this.nameZip = nameZip; }
}

