package com.example.internship.controller;

import com.example.internship.model.Internship;
import com.example.internship.repository.InternshipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class RssController {

    private final InternshipRepository internshipRepository;

    @GetMapping(value = "/rss", produces = MediaType.APPLICATION_XML_VALUE)
    public String getRssFeed() {

        List<Internship> internships = internshipRepository.findAll();

        StringBuilder rss = new StringBuilder();

        rss.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        rss.append("<rss version=\"2.0\">");
        rss.append("<channel>");

        rss.append("<title>Internships Feed</title>");
        rss.append("<description>Available internships</description>");
        rss.append("<link>http://localhost:8081/rss</link>");

        rss.append("<language>en-us</language>");
        rss.append("<lastBuildDate>").append(formatDate(new Date())).append("</lastBuildDate>");

        for (Internship i : internships) {
            rss.append("<item>");
            rss.append("<title>").append(i.getTitle()).append("</title>");
            rss.append("<description>").append(i.getDescription()).append("</description>");
            rss.append("<link>http://localhost:8081/internships/")
                    .append(i.getId())
                    .append("</link>");
            rss.append("<pubDate>")
                    .append(formatDate(new Date()))
                    .append("</pubDate>");
            rss.append("</item>");
        }

        rss.append("</channel>");
        rss.append("</rss>");

        return rss.toString();
    }

    private String formatDate(Date date) {
        return new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z")
                .format(date);
    }
}