package ru.gb.hubr.service.mail;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
/**
 * @author Vitaly Krivobokov
 * @Date 13.11.22
 */
@Getter
@Setter
@Builder
public class EmailContext {

    private String from;
    private String to;
    private String subject;
    private String email;
    private String attachment;
    private String fromDisplayName;
    private String emailLanguage;
    private String displayName;
    private String pathTemplate;


    @Builder.Default
    private Map<String, Object> context = new HashMap<>();


    public void addContext(String key, Object value) {
        context.put(key, value);
    }


}


