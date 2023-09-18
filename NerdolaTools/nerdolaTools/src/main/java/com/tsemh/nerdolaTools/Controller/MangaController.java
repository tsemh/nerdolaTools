package com.tsemh.nerdolaTools.Controller;

import com.tsemh.nerdolaTools.Model.entity.DownloadRequest;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


@RestController()
@RequestMapping("/download-manga")
public class MangaController {

    @PostMapping("/mangareader")
    public ResponseEntity<byte[]> downloadManga(@RequestBody DownloadRequest request) {
        String url = request.getUrl();
        request.setNameZip("Teste");
        String nameZip = request.getNameZip();

        try {
            Document document = Jsoup.connect(url).get();
            Elements imgs = document.select("img");

            ByteArrayOutputStream zipBytes = new ByteArrayOutputStream();
            try (ZipOutputStream zipOut = new ZipOutputStream(zipBytes)) {
                int imageCount = 1;

                for (Element img : imgs) {
                    String imageUrlRelative = img.attr("src");
                    byte[] imageBytes = downloadImage(imageUrlRelative, url);

                    // Adicione a imagem ao arquivo ZIP com um nome único
                    ZipEntry zipEntry = new ZipEntry("image" + imageCount + ".jpg");
                    zipOut.putNextEntry(zipEntry);
                    zipOut.write(imageBytes);
                    zipOut.closeEntry();

                    imageCount++;
                }
            }

            byte[] zipFileBytes = zipBytes.toByteArray();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "C:\\downloads\\" + nameZip + ".zip");

            return new ResponseEntity<>(zipFileBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private byte[] downloadImage(String imageUrl, String pageUrl) throws Exception {
        if (!imageUrl.startsWith("http://") && !imageUrl.startsWith("https://")) {
            URL absoluteUrl = new URL(new URL(pageUrl), imageUrl);
            imageUrl = absoluteUrl.toString();
        }

        URL url = new URL(imageUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        try (InputStream is = connection.getInputStream();
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            byte[] buffer = new byte[4096];
            int bytesRead;

            while ((bytesRead = is.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }

            return baos.toByteArray();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}