package com.tsemh.nerdolaTools.Controller;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;
import com.tsemh.nerdolaTools.Model.entity.DownloadRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
@RestController
@RequestMapping(value = "/manga-download", method = RequestMethod.POST)
public class MangaController {

    @PostMapping(value = "/mangareader", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<String>> mangaRequest(@RequestBody DownloadRequest downloadRequest, HttpServletResponse response) throws IOException, DocumentException {
        String url = downloadRequest.getUrl();
        List<String> imageUrls = retrieveImg(url);

        if (imageUrls.isEmpty()) {
            System.out.println("A lista de URLs de imagens está vazia.");
            return ResponseEntity.badRequest().body(imageUrls);
        }

        System.out.println("Dados retornados pelo servidor: " + imageUrls);

        return ResponseEntity.ok(imageUrls);
    }
    private List<String> retrieveImg(String url) throws IOException {
        List<String> imageUrls = new ArrayList<>();
        try {
            org.jsoup.nodes.Document document = Jsoup.connect(url).get();
            Elements imgElements = document.select("img");
            imageUrls.addAll(retrieveSrcFromImg(imgElements));
        } catch (IOException e) {
            System.out.println("Error when retrieving img tags: " + e);
        }
        return imageUrls;
    }
    private List<String> retrieveSrcFromImg(Elements imgElements) throws IOException {
        List<String> imageUrls = new ArrayList<>();
        for (Element imgTag : imgElements) {
            String src = imgTag.attr("src");
            if (!src.isEmpty() && !containsLogo(src)) {
                System.out.println("Obtained image URL: " + src);
                imageUrls.add(src);
            }
        }
        return imageUrls;
    }
    private boolean containsLogo(String src) {
        String lowerCaseSrc = src.toLowerCase();
        return lowerCaseSrc.contains("logo");
    }
    private Image retrieveImageFromSrc(String src) throws IOException, BadElementException {
        URL newSrc = new URL(src);
        return Image.getInstance(newSrc);
    }
}
