package com.projeto.senac.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.projeto.senac.enums.Status;
import com.projeto.senac.model.Aluno;
import com.projeto.senac.model.Professor;
import com.projeto.senac.model.Turma;
import com.projeto.senac.repository.AlunoRepository;
import com.projeto.senac.repository.ProfessorRepository;
import com.projeto.senac.repository.TurmaRepository;
import com.projeto.senac.service.ServiceTurma;

@Controller
public class TurmaController {
	
	@Autowired
	ProfessorRepository professorRepository;
	@Autowired
	TurmaRepository turmaRepository;
	@Autowired
	ServiceTurma serviceTurma;
	@Autowired
	AlunoRepository alunoRepository;
	
	@GetMapping("/carregarTurma")
	public ModelAndView carregarTurma() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("professores", professorRepository.findAllOrderedById());
		mv.addObject("turma", new Turma());
		mv.setViewName("Turma/turmaInsert");
		return mv;
	}
	
	@PostMapping("/inserirTurma")
	public ModelAndView carregarTurma(Turma turma) {
		ModelAndView mv = new ModelAndView();
		String out = serviceTurma.cadastrarTurma(turma);
		if(out != null) {
			mv.addObject("professores", professorRepository.findAllOrderedById());
			mv.addObject("msg", out);
			mv.addObject("turma", new Turma());
			mv.setViewName("Turma/turmaInsert");
		}
		else {
			List<Aluno> alunos = alunoRepository.findByTurnoAndCursoAndStatus(turma.getTurno(), turma.getCurso(), Status.ATIVO);
			Professor professor = professorRepository.findById(turma.getProfessor().getId()).get();
			turma.setProfessor(professor);
			mv.addObject("alunos", alunos);
			turma.setAlunos(alunos);
			mv.addObject("turma", turma);
			mv.setViewName("Turma/inserirAlunosTurma");
		}
		return mv;
	}
	
	@PostMapping("/inserirAlunosTurma")
	public ModelAndView inserirAlunosTurma(Turma turma) {
		ModelAndView mv = new ModelAndView();
		turmaRepository.save(turma);
		mv.setViewName("redirect:/home");
		return mv;
	}
	
	@GetMapping("/listarTurmas")
	public ModelAndView listarTurmas() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("turmas", turmaRepository.findAllOrderedById());
		mv.setViewName("Turma/listarTurmas");
		return mv;
	} 

}
