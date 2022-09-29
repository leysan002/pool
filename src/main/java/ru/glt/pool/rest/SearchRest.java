package ru.glt.pool.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.glt.pool.search.SearchConditions;
import ru.glt.pool.search.SearchResult;
import ru.glt.pool.search.SearchService;

import java.util.List;

@RestController
public class SearchRest {

    @Autowired
    private SearchService searchService;

    @PostMapping(path = "search")
    public ResponseEntity<List<SearchResult>> search(@RequestBody SearchConditions conditions) {
        try {
            return new ResponseEntity(searchService.search(conditions), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
