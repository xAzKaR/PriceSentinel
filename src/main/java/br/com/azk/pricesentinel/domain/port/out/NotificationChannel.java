package br.com.azk.pricesentinel.domain.port.out;

import br.com.azk.pricesentinel.domain.model.PriceResult;

public interface NotificationChannel {

    void send(PriceResult result);
}
