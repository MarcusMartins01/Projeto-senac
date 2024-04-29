package com.projeto.senac.service;



import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto.senac.model.Aluno;
import com.projeto.senac.repository.AlunoRepository;

@Service
public class ServiceAluno {
	
	@Autowired
	private AlunoRepository repository;
	
	public String gerarMatricula(int id) {
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int ano = cal.get(Calendar.YEAR);
		String txt = "SENAC";
		return txt + ano + (id + 1);
	}
	
	public String cadastrarAluno(Aluno aluno) {
		Aluno alunoExistente =  repository.findByCpf(aluno.getCpf());
		if (alunoExistente == null) {
			Aluno aux = repository.findLastInsertedAluno();
			if (aux == null) {
				aluno.setMatricula(this.gerarMatricula(0));
			}
			else {
				aluno.setMatricula(this.gerarMatricula(Integer.parseInt(aux.getId().toString())));
			}
			repository.save(aluno);
		}
		else {
			return "Já existe um aluno cadastrado com o mesmo Cpf!";
		}
		
		
		
		return null;
	}
	
	public String alterarAluno(Aluno aluno, Long id) {
		Aluno alunoExistente =  repository.findByCpf(aluno.getCpf());
		if ((alunoExistente != null && alunoExistente.getId() == id) || alunoExistente == null) {
			repository.save(aluno);
		}
		else {
			return "Já existe um aluno cadastrado com o mesmo Cpf!";
		}
		return null;
	}

}
