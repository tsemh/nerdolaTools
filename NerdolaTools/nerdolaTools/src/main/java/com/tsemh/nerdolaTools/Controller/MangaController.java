package com.tsemh.nerdolaTools.Controller;

import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;
import com.tsemh.nerdolaTools.Model.entity.DownloadRequest;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.*;
import java.net.URL;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


@RestController()
@RequestMapping("/manga-download")
public class MangaController {

    DownloadRequest downloadrequest;

    @PostMapping("/mangareader")
    public DownloadRequest mangaRequest(@RequestBody DownloadRequest downloadRequest) throws IOException {
        String url = downloadrequest.getUrl();
    }

    private void webScraping(String url) throws IOException {
        Elements imgElements = retrieveImg(url);
        retrieveSrcFromImg(url, imgElements);
    }

    private Elements retrieveImg(String url) throws IOException {
        try {
            Document document = Jsoup.connect(url).get();
            return document.select("img");
        } catch (IOException e) {
            System.out.println("Error When recovering img tag: " + e);
            return new Elements();
        }
    }
    private void retrieveSrcFromImg(String url, Elements imgElement) throws IOException {
        for(Element imgTag : imgElement) {
            String src = imgTag.attr("src");
        }
    }
    private Image retrieveImageFromSrc(String src) {
        try {
            Url newSrc = new url(src);
            return Image.getInstance(newSrc);
        } catch (IOException e) {
            System.out.println("Error when accessing image URL: " + e);
            return null;
        }
    }

    private void pdfCreator() throws FileNotFoundException {

    }

}
