package com.alterhub.alterhubbackend.controller;

import com.alterhub.alterhubbackend.service.impl.AlteredSyncService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Flux;

import java.io.IOException;

@RestController
@RequestMapping("/api/sync")
@RequiredArgsConstructor
@Slf4j
public class AlteredController {

    private final AlteredSyncService syncService;

    /**
     * Endpoint pour synchroniser toutes les cartes Altered
     */
    @GetMapping(value = "/altered", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter syncAlteredCardsSse() {
        SseEmitter emitter = new SseEmitter(0L); // pas de timeout
        new Thread(() -> {
            try {
                syncService.syncAllCardsByFaction(emitter);
                emitter.send(SseEmitter.event().data("Synchronisation terminée !"));
                emitter.complete();
            } catch (Exception e) {
                try {
                    emitter.send(SseEmitter.event().data("Erreur : " + e.getMessage()));
                } catch (IOException ignored) {}
                emitter.completeWithError(e);
            }
        }).start();
        return emitter;
    }
}