package org.spaceinvaders.server.dao;

import org.spaceinvaders.shared.dto.Evaluation;

import java.util.TreeMap;

public interface EvaluationDao {
    TreeMap<String, Evaluation> getAll(String cip, int semesterID);
}
