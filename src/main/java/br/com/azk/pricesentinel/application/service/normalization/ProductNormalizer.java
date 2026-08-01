package br.com.azk.pricesentinel.application.service.normalization;


import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductNormalizer {

    public String normalize(String name) {

        if (name == null || name.isBlank()) {
            return "";
        }

        String normalized = name;

        for (String term : REMOVED_TERMS) {
            normalized = normalized.replace(term, "");
        }

        return normalized
                .replaceAll("\\s+", " ")
                .trim();
    }


    private static final List<String> REMOVED_TERMS = List.of(
            "Processador",
            "Processor",
            "AMD",
            "™",
            "Desktop",
            "Box",
            "Tray"
    );
}
