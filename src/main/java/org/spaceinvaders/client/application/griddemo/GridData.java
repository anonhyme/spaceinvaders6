package org.spaceinvaders.client.application.griddemo;

import org.spaceinvaders.shared.dto.Competence;
import org.spaceinvaders.shared.dto.CompetenceEvalResult;

import java.util.List;

/**
 * Created with IntelliJ IDEA Project: projetS6 on 5/26/2015
 *
 * @author antoine
 */
public abstract class GridData<T, E> {

    abstract List<Competence> getAllCompetences();

    abstract List<CompetenceEvalResult> getAllRow();

}
