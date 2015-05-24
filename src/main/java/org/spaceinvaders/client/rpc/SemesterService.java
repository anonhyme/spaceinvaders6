package org.spaceinvaders.client.rpc;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.inject.Inject;

import org.spaceinvaders.shared.model.EvaluationGrid;
import org.spaceinvaders.shared.model.SemesterInfo;

import java.util.List;

import javax.persistence.EntityManager;

/**
 * Created with IntelliJ IDEA Project: projetS6 on 5/16/2015
 *
 * @author antoine
 */
@RemoteServiceRelativePath("dataService")
public interface SemesterService extends RemoteService {

    SemesterInfo fetchSemesterInfo();
}