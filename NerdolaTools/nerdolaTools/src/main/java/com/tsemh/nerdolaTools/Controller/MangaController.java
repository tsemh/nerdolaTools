package com.tsemh.nerdolaTools.Controller;

import com.tsemh.nerdolaTools.Model.entity.DownloadRequest;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController()
@RequestMapping("/manga-download")
public class MangaController {

    @PostMapping("/mangareader")
    public DownloadRequest mangaRequest(@RequestBody DownloadRequest downloadRequest) {

    }

    private void webScraping(String url) throws IOException {

    }
    private void retrieveImg(String url) throws IOException {
        try {
            Document document = Jsoup.connect(url).get();
            Elements imgElement = document.select("img");
        } catch (IOException e) {
            System.out.println("Error When recovering img tag: "+e);
        }

    }
    private void retrieveSrcOfImg(String url) throws IOException {
        try {
            for(Element imgTag : imgTags) {
                String src = imgTag.attr("src");
            }
        } catch (IOException e) {
            System.out.println(("Error when recovering src attribute: "+e));
        }
    }

}
