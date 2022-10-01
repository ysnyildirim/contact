package com.yil.contact.config;

import com.yil.contact.model.PhoneType;
import com.yil.contact.repository.PhoneTypeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class SetupDataLoader implements ApplicationListener<ContextStartedEvent> {

    @Autowired
    private PhoneTypeDao phoneTypeDao;

    @Override
    public void onApplicationEvent(ContextStartedEvent event) {
        System.out.println("Start Up Events");
        System.out.println(new Date(event.getTimestamp()));
        System.out.println("----------------------");
        initPhoneTypes();
    }

    private void initPhoneTypes() {
        phoneTypeDao.save(PhoneType.builder().id(1l).name("CEP TELEFONU").build());
        phoneTypeDao.save(PhoneType.builder().id(2l).name("İŞ TELEFONU").build());
        phoneTypeDao.save(PhoneType.builder().id(3l).name("EV TELEFONU").build());
        phoneTypeDao.save(PhoneType.builder().id(4l).name("VELİSİNİN/YAKINININ TELEFONU").build());

    }


}
