package com.gugulethugillz.auctionsystem.schedulers.processors.api;


import com.gugulethugillz.auctionsystem.winner.model.Winner;

import java.util.List;

public interface WinnerProcessor {
    List<Winner> determineWinners();
}
