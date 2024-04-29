package com.projeto.senac.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.projeto.senac.model.Professor;
import com.projeto.senac.repository.ProfessorRepository;
import com.projeto.senac.service.ServiceProfessor;

@Controller
public class ProfessorController {
	
	@Autowired
	private ServiceProfessor serviceProfessor;
	@Autowired
	private ProfessorRepository professorRepository;

	@GetMapping("/inserirProfessor")
	public ModelAndView insertProfessor() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("professor", new Professor());
		mv.setViewName("Professor/inserirProfessor");
		return mv;
	}
	
	@PostMapping("/inserirProfessor")
	public ModelAndView insertProfessor(Professor professor) {
		ModelAndView mv = new ModelAndView();
		String out = serviceProfessor.cadastrarProfessor(professor);
		if (out != null) {
			mv.addObject("msg", out);
			mv.addObject("professor", new Professor());
			mv.setViewName("Professor/inserirProfessor");
		}
		else {
			mv.addObject("professores", professorRepository.findAllOrderedById());
			mv.setViewName("Professor/listarProfessores");
		}
		return mv;
	}
	
	@GetMapping("/listarProfessores")
	public ModelAndView listarProfessores() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("Professor/listarProfessores");
		mv.addObject("professores", professorRepository.findAllOrderedById());
		return mv;
	}
	
	@GetMapping("/listarAlterarProfessores")
	public ModelAndView listarAlterarProfessores() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("Professor/listarAlterar");
		mv.addObject("professores", professorRepository.findAllOrderedById());
		return mv;
	}
	
	@GetMapping("/alterarProfessor/{id}")
	public ModelAndView alterarProfessor(@PathVariable Long id) {
		ModelAndView mv = new ModelAndView();
		Professor professor = professorRepository.findById(id).get();
		mv.addObject("professor", professor);
		mv.setViewName("Professor/alterar");
		return mv;
	}
	
	@PostMapping("/alterarProfessor")
	public ModelAndView alterarProfessor(Professor professor) {
		ModelAndView mv = new ModelAndView();
		String out = serviceProfessor.alterarProfessor(professor, professor.getId());
		if (out != null) {
			mv.addObject("msg", out);
			mv.addObject("professor", professor);
			mv.setViewName("Professor/alterar");
		}
		else {
			mv.addObject("professores", professorRepository.findAllOrderedById());
			mv.setViewName("Professor/listarAlterar");
		}
		return mv;
	}
	
	@GetMapping("/listarExcluirProfessores")
	public ModelAndView listarExcluirProfessores() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("Professor/listarExcluir");
		mv.addObject("professores", professorRepository.findAllOrderedById());
		return mv;
	}
	
	@GetMapping("/excluirProfessor/{id}")
	public String excluirProfessor(@PathVariable("id") Long id) {
		professorRepository.deleteById(id);
		return "redirect:/listarExcluirProfessores";
	}
}
