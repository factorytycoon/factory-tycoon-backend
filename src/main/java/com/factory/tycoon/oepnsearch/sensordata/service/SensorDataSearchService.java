package com.factory.tycoon.oepnsearch.sensordata.service;

import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.opensearch._types.query_dsl.Query;
import org.opensearch.client.opensearch.core.SearchRequest;
import org.opensearch.client.opensearch.core.SearchResponse;
import org.opensearch.client.opensearch.core.search.Hit;
import org.opensearch.client.json.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.factory.tycoon.oepnsearch.sensordata.document.SensorDataDocument;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SensorDataSearchService {

    @Autowired
    private OpenSearchClient openSearchClient;

    public List<SensorDataDocument> searchDailySensorData(LocalDate date) throws IOException {
        String indexName = "sensor-data"; // 인덱스 이름

        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(23, 59, 59, 999999999);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        Query query = Query.of(q -> q
                .range(r -> r
                        .field("@timestamp")
                        .gte(JsonData.of(startOfDay.format(formatter)))
                        .lte(JsonData.of(endOfDay.format(formatter)))
                )
        );

        SearchRequest searchRequest = SearchRequest.of(s -> s
                .index(indexName)
                .query(query)
                .size(10000) // 하루치 데이터, 필요에 따라 조정
        );

        SearchResponse<SensorDataDocument> searchResponse = openSearchClient.search(searchRequest, SensorDataDocument.class);

        return searchResponse.hits().hits().stream()
                .map(Hit::source)
                .collect(Collectors.toList());
    }
}
