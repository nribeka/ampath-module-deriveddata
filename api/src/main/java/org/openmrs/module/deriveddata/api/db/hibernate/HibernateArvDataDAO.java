/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.deriveddata.api.db.hibernate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Expression;
import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.Patient;
import org.openmrs.module.deriveddata.api.db.ArvDataDAO;
import org.openmrs.module.deriveddata.api.model.ArvData;

import java.util.List;

/**
 */
public class HibernateArvDataDAO implements ArvDataDAO {
    protected final Log log = LogFactory.getLog(this.getClass());

    private SessionFactory sessionFactory;

    /**
     * @param sessionFactory the sessionFactory to set
     */
    public void setSessionFactory(final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * @return the sessionFactory
     */
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Override
    public ArvData saveArvData(final ArvData data) {
        getSessionFactory().getCurrentSession().saveOrUpdate(data);
        return data;
    }

    @Override
    public ArvData getArvData(final Encounter encounter, final Concept question) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(ArvData.class);
        criteria.add(Expression.eq("encounter", encounter));
        criteria.add(Expression.eq("question", question));
        return (ArvData) criteria.uniqueResult();
    }

    @Override
    public List<ArvData> findArvDataByEncounter(final Encounter encounter) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(ArvData.class);
        criteria.add(Expression.eq("encounter", encounter));
        return criteria.list();
    }

    @Override
    public List<ArvData> findArvDataByPatient(final Patient patient) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(ArvData.class);
        criteria.add(Expression.eq("patient", patient));
        return criteria.list();
    }

    @Override
    public void deleteArvData(final ArvData data) {
        getSessionFactory().getCurrentSession().delete(data);
    }
}
