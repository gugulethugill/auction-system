package com.gugulethugillz.auctionsystem.winner.service;

import com.gugulethugillz.auctionsystem.winner.model.Winner;
import com.gugulethugillz.auctionsystem.winner.repository.WinnerRepository;

import java.util.List;
import java.util.Optional;

public interface WinnerService {
    WinnerRepository getRepository();
    Optional<Winner> findById(Long id);
    List<Winner> findByUsername(String username);
    List<Winner> findAllWinners();
}
