package com.assets_france.api.shared.helper.hibernate;

import com.assets_france.api.shared.domain.helper.HibernateHelper;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Component;

@Component
public class HibernateHelperImpl implements HibernateHelper {
    @Override
    public void initialize(Object proxy) {
        Hibernate.initialize(proxy);
    }
}
