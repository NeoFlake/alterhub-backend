package com.alterhub.alterhubbackend.service.impl;

import com.alterhub.alterhubbackend.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerServiceImpl {

    @Autowired
    private PlayerRepository playerRepository;

}
