package org.spaceinvaders.client.application.griddemo;

import org.spaceinvaders.shared.dto.Competence;
import org.spaceinvaders.shared.dto.CompetenceEvalResult;

import java.util.List;

/**
 * Created with IntelliJ IDEA Project: projetS6 on 5/26/2015
 *
 * @author antoine
 */
public interface GridData<T, E> {

    public List<Competence> getAllCompetences();

    public List<CompetenceEvalResult> getAllRow();

}
