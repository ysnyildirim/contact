package com.yil.contact.config;

import com.yil.contact.model.PhoneType;
import com.yil.contact.service.PhoneTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class SetupDataLoader implements ApplicationListener<ContextStartedEvent> {

    @Autowired
    private PhoneTypeService phoneTypeService;


    @Override
    public void onApplicationEvent(ContextStartedEvent event) {
        System.out.println("Start Up Events");
        System.out.println(new Date(event.getTimestamp()));
        System.out.println("----------------------");
        initPhoneTypes();
    }

    private void initPhoneTypes() {
        addPhoneType("CEP TELEFONU");
        addPhoneType("İŞ TELEFONU");
        addPhoneType("EV TELEFONU");
        addPhoneType("VELİSİNİN/YAKINININ TELEFONU");

    }

    private void addPhoneType(String name) {
        List<PhoneType> typeList = phoneTypeService.findByName(name);
        if (typeList.size() > 0)
            return;
        PhoneType type = new PhoneType();
        type.setName(name);
        phoneTypeService.save(type);
    }

}
