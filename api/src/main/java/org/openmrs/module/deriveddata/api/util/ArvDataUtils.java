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
package org.openmrs.module.deriveddata.api.util;

import org.openmrs.Concept;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 */
public class ArvDataUtils {

    private static final List<Concept> questions = Arrays.asList(new Concept(966), new Concept(1086), new Concept(1087),
            new Concept(1088), new Concept(1250), new Concept(1895), new Concept(2154), new Concept(2157));

    private static final Map<Concept, List<String>> codedIdFieldNameMap = new HashMap<Concept, List<String>>();

    private static void map(final Integer conceptId, final String... fieldName) {
        List<String> fieldNames = Arrays.asList(fieldName);
        // TODO: concept equals and hashCode only taking id in the calculation!
        codedIdFieldNameMap.put(new Concept(conceptId), fieldNames);
    }

    static {
        map(625, "onStavudine");
        map(628, "onLamivudine");
        map(630, "onLamivudine", "onZidovudine");
        map(631, "onNevirapine");
        map(633, "onEfavirenz");
        map(635, "onNelfinavir");
        map(749, "onIndinavir");
        map(791, "onEmtricitabine");
        map(792, "onLamivudine", "onNevirapine", "onStavudine");
        map(794, "onLopinavir", "onRitonavir");
        map(795, "onRitonavir");
        map(796, "onDidanosine");
        map(797, "onZidovudine");
        map(802, "onTenofovir");
        map(814, "onAbacavir");
        map(817, "onAbacavir", "onLamivudine", "onZidovudine");
        map(1400, "onLamivudine", "onTenofovir");
        map(5424, "onOther");
        map(5811, "onUnknown");
        map(6156, "onRaltegravir");
        map(6157, "onDarunavir");
        map(6158, "onEtravirine");
        map(6159, "onAtazanavir");
        map(6160, "onAtazanavir", "onRitonavir");
        map(6180, "onEmtricitabine", "onTenofovir");
        map(6467, "onLamivudine", "onNevirapine", "onZidovudine");
        map(6679, "onAbacavir", "onLamivudine");
        map(6964, "onEfavirenz", "onLamivudine", "onTenofovir");
        map(6965, "onLamivudine", "onStavudine");
    }

    public static List<String> getMappings(final Concept valueCoded) {
        List<String> mappings = codedIdFieldNameMap.get(valueCoded);
        if (mappings == null)
            mappings = Collections.emptyList();
        return mappings;
    }

    public static List<Concept> getQuestions() {
        return questions;
    }
}
