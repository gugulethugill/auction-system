package com.gugulethugillz.auctionsystem.integrations.camel;

import com.gugulethugillz.auctionsystem.person.repository.HumanBeingRepository;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HumanRecordResponseGenerator implements Processor {
    @Autowired
    private HumanBeingRepository humanBeingRepository;

    @Override
    public void process(Exchange exchange) throws Exception {
        List<HumanBeing> humanBeings = humanBeingRepository.findAll();
        exchange.getIn().setBody(humanBeings);
    }
}
