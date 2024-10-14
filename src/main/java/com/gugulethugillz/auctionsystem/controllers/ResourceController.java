package com.gugulethugillz.auctionsystem.controllers;

import com.gugulethugillz.auctionsystem.common.AuctionSystemProperties;
import com.gugulethugillz.auctionsystem.common.Utils;
import com.gugulethugillz.auctionsystem.storage.StorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/resources")
@RequiredArgsConstructor
public class ResourceController {
    private final AuctionSystemProperties props;
    private final ResourceLoader resourceLoader;
    private final StorageService storageService;

    @GetMapping("/images/{imageName}")
    private ResponseEntity<Resource> getUserImageFromFileSystem(@PathVariable("imageName") String imageName) {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-type", Utils.guessMimeType(imageName));
        final String images = props.getImages();
        final String fullPath = "file:" + images + "/" + imageName;
        Resource resource = resourceLoader.getResource(fullPath);
        return new ResponseEntity<>(resource, httpHeaders, HttpStatus.OK);
    }
}
