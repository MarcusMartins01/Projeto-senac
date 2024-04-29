package com.projeto.senac.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto.senac.model.Turma;
import com.projeto.senac.repository.TurmaRepository;

@Service
public class ServiceTurma {
	
	@Autowired
	TurmaRepository repository;
	public String cadastrarTurma(Turma turma) {
		Turma objTurma = repository.findByCodTurma(turma.getCodTurma());
		if (objTurma != null) {
			return "Já existe uma turma cadastrada com esse código";
		}
		else {
			return null;
		}
	}

}
