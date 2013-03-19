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
package org.openmrs.module.deriveddata.api.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.Patient;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.deriveddata.api.db.TbDataDAO;
import org.openmrs.module.deriveddata.api.model.TbData;
import org.openmrs.module.deriveddata.api.service.TbDataService;

import java.util.List;

/**
 * It is a default implementation of {@link org.openmrs.module.deriveddata.api.service.DerivedDataService}.
 */
public class TbDataServiceImpl extends BaseOpenmrsService implements TbDataService {

    protected final Log log = LogFactory.getLog(this.getClass());

    private TbDataDAO dao;

    /**
     * @param dao the dao to set
     */
    public void setDao(final TbDataDAO dao) {
        this.dao = dao;
    }

    /**
     * @return the dao
     */
    public TbDataDAO getDao() {
        return dao;
    }

    @Override
    public TbData saveTbData(final TbData data) {
        return getDao().saveTbData(data);
    }

    @Override
    public TbData getTbData(final Encounter encounter, final Concept concept) {
        return getDao().getTbData(encounter, concept);
    }

    @Override
    public List<TbData> findTbDataByEncounter(final Encounter encounter) {
        return getDao().findTbDataByEncounter(encounter);
    }

    @Override
    public List<TbData> findTbDataByPatient(final Patient patient) {
        return getDao().findTbDataByPatient(patient);
    }

    @Override
    public void deleteTbData(final TbData data) {
        getDao().deleteTbData(data);
    }
}