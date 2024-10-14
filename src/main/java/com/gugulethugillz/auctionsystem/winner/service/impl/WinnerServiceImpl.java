package com.gugulethugillz.auctionsystem.winner.service.impl;


import com.gugulethugillz.auctionsystem.winner.model.Winner;
import com.gugulethugillz.auctionsystem.winner.repository.WinnerRepository;
import com.gugulethugillz.auctionsystem.winner.service.WinnerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class WinnerServiceImpl implements WinnerService {

    private final WinnerRepository winnerRepository;

    @Override
    public WinnerRepository getRepository() {
        return winnerRepository;
    }

    @Override
    public Optional<Winner> findById(Long id) {
        return winnerRepository.findById(id);
    }

    @Override
    public List<Winner> findByUsername(String username) {
        return winnerRepository.getByUsername(username);
    }

    @Override
    public List<Winner> findAllWinners() {
        return winnerRepository.findAll();
    }
}
