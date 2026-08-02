package br.com.azk.pricesentinel.infrastructure.http;

import org.jsoup.nodes.Document;

public interface HtmlClient {

    Document get(String url);

}
