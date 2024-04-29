package com.projeto.senac.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto.senac.model.Professor;
import com.projeto.senac.repository.ProfessorRepository;

@Service
public class ServiceProfessor {

	@Autowired
	private ProfessorRepository professorRepository;
	
	public String cadastrarProfessor(Professor professor) {
		
		Professor profExiste = professorRepository.findByCpf(professor.getCpf());
		if (profExiste != null) {
			return "Já existe um professor cadastrado com o mesmo cpf informado!"; 
		}
		else {
			professorRepository.save(professor);
		}
		return null;
	}
	
	public String alterarProfessor(Professor professor, long id) {	
		Professor professorExistente = professorRepository.findByCpf(professor.getCpf());
		if ((professorExistente != null && professorExistente.getId() == id) || professorExistente == null) {
			professorRepository.save(professor);
		}
		else {
			return "Já existe um professor cadastrado com o mesmo CPF!";
		}
		return null;
	}
}
