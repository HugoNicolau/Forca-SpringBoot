package com.jogodaforca.forca.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jogodaforca.forca.dto.RankingDTO;
import com.jogodaforca.forca.service.RankingService;

@RestController
@RequestMapping("/api/ranking")
public class RankingController {
    
    @Autowired
    private RankingService rankingService;
    
    @GetMapping
    public ResponseEntity<List<RankingDTO>> getRanking() {
        return ResponseEntity.ok(rankingService.obterRanking());
    }
}