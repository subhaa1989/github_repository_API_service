package org.redcare.coding.controller;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.extern.slf4j.Slf4j;
import org.redcare.coding.model.Request;
import org.redcare.coding.service.PopularRepositoryService;
import org.redcare.coding.utils.DateUtils;
import org.redcare.coding.utils.JSONWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;


@RestController
@Slf4j
@Validated
public class GithubRepositoryController {

    @Autowired
    private PopularRepositoryService popularRepositoryService;

    public static final String MAX_ENTRIES = "10";
    public static final String DEFAULT_LANGUAGE = "all";
    public static final String DEFAULT_FROM_DATE = "2023-05-11";

    @GetMapping("/getrepo")
    public ResponseEntity<String> getRepositoryDetails(
            @RequestParam(value = "language", defaultValue = DEFAULT_LANGUAGE, required = false)
            @NonNull @NotEmpty String language,
            @RequestParam(value = "count", defaultValue = MAX_ENTRIES, required = false) @Max(value = 100, message = "only 100 results can be retrieved at a time, use pagination to receive next page") @Min(1) @NonNull Integer count,
            @RequestParam(value = "fromDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) throws IOException {
        if (date == null) {
            date = DateUtils.toDate(DEFAULT_FROM_DATE);
        }
        var request = Request.builder().language(language).count(count).fromDate(date).build();
        log.info("Received request {}  ", request);

        var result = popularRepositoryService.getPopularGithubRepository(request);

         log.info("Received response with status code  {}", result.getStatus());
        return ResponseEntity.of(Optional.of(JSONWrapper.getJsonResponse(result)));
    }


    @GetMapping("/get/top10")
    public ResponseEntity<String> getTop10RepositoryDetails(
            @RequestParam(value = "language", defaultValue = DEFAULT_LANGUAGE, required = false) @NonNull @NotEmpty String language,
            @RequestParam(value = "fromDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) throws IOException {
        if (date == null) {
            date = DateUtils.toDate(DEFAULT_FROM_DATE);
        }
        final int count = 10;
        var request = Request.builder().language(language).count(count).fromDate(date).build();
        log.info("Received request {}  ", request);;

        var result = popularRepositoryService.getPopularGithubRepository(request);

        log.info("Received response with status code  {}", result.getStatus());
        return ResponseEntity.of(Optional.of(JSONWrapper.getJsonResponse(result)));
    }

    @GetMapping("/get/top20")
    public ResponseEntity<String> getTop20RepositoryDetails(
            @RequestParam(value = "language", defaultValue = DEFAULT_LANGUAGE, required = false) @NonNull @NotEmpty String language,
            @RequestParam(value = "fromDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) throws IOException {
        if (date == null) {
            date = DateUtils.toDate(DEFAULT_FROM_DATE);
        }
        final int count = 20;
        var request = Request.builder().language(language).count(count).fromDate(date).build();
        log.info("Received request {}  ", request);

        var result = popularRepositoryService.getPopularGithubRepository(request);

        log.info("Received response with status code  {}", result.getStatus());
        return ResponseEntity.of(Optional.of(JSONWrapper.getJsonResponse(result)));
    }

    @GetMapping("/get/top50")
    public ResponseEntity<String> getTop50RepositoryDetails(
            @RequestParam(value = "language", defaultValue = DEFAULT_LANGUAGE, required = false) @NonNull @NotEmpty String language,
            @RequestParam(value = "fromDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) throws IOException {
        if (date == null) {
            date = DateUtils.toDate(DEFAULT_FROM_DATE);
        }
        final int count = 50;
        var request = Request.builder().language(language).count(count).fromDate(date).build();
        log.info("Received request {}  ", request);

        var result = popularRepositoryService.getPopularGithubRepository(request);

        log.info("Received response with status code  {}", result.getStatus());
        return ResponseEntity.of(Optional.of(JSONWrapper.getJsonResponse(result)));
    }
}
