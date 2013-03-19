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
import java.util.List;

/**
 */
public class TbDataUtils {

    public static final Concept ANSWER_START_DRUGS = new Concept(1256);

    public static final Concept ANSWER_STOP_DRUGS = new Concept(1260);

    private static final List<Concept> questions = Arrays.asList(new Concept(1268));

    private static final List<Concept> answers = Arrays.asList(ANSWER_START_DRUGS, ANSWER_STOP_DRUGS);

    public static List<Concept> getAnswers() {
        return answers;
    }

    public static List<Concept> getQuestions() {
        return questions;
    }
}
