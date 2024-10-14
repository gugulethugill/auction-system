package com.gugulethugillz.auctionsystem.service.ifaces;

import com.gugulethugillz.auctionsystem.model.Winner;
import com.gugulethugillz.auctionsystem.repository.WinnerRepository;

import java.util.List;
import java.util.Optional;

public interface WinnerService {
    WinnerRepository getRepository();
    Optional<Winner> findById(Long id);
    List<Winner> findByUsername(String username);
    List<Winner> findAllWinners();
}
