package br.com.alura.spring.data.service;

import java.util.Scanner;

import org.springframework.stereotype.Service;

import br.com.alura.spring.data.orm.Funcionario;
import br.com.alura.spring.data.repository.FuncionarioRepository;

@Service
public class CrudFuncionarioService {

	private Boolean system = true;
	private final FuncionarioRepository funcionarioRepository;
	
	public CrudFuncionarioService(FuncionarioRepository funcionarioRepository) {
		this.funcionarioRepository = funcionarioRepository;
	}
	
	public void inicial(Scanner scanner) {
		while(system) {
			System.out.println("Qual ação de funcionário deseja executar?");
			System.out.println("0 - Sair");
			System.out.println("1 - Salvar");
			System.out.println("2 - Atualizar");
			System.out.println("3 - Visualizar");
			System.out.println("4 - Deletar");
			
			int action = scanner.nextInt();
			
			switch (action) {
			case 1:
				salvar(scanner);
				break;
			case 2:
				atualizar(scanner);
				break;
			case 3:
				visualizar();
				break;
			case 4:
				deletar(scanner);
				break;
			default:
				system = false;
				break;
			}
		}
	}
	
	private void salvar(Scanner scanner) {
		System.out.println("Nome do funcionário:");
		String nome = scanner.next();
		System.out.println("CPF do funcionário:");
		String cpf = scanner.next();
		System.out.println("Salário do funcionário:");
		Double salario = scanner.nextDouble();
		System.out.println("Data de contratação do funcionário:");
		String dataContratacao = scanner.next();
		
		Funcionario funcionario = new Funcionario();
		funcionario.setNome(nome);
		funcionario.setCpf(cpf);
		funcionario.setSalario(salario);
		funcionario.setDataContratacao(dataContratacao);
		funcionarioRepository.save(funcionario);
		System.out.println("Salvo");
	}
	
	private void atualizar(Scanner scanner) {
		System.out.println("Informe o id do funcionário a ser atualizado:");
		int id = scanner.nextInt();
		System.out.println("Novo nome do funcionário:");
		String nome = scanner.next();
		System.out.println("Novo CPF do funcionário:");
		String cpf = scanner.next();
		System.out.println("Novo salário do funcionário:");
		Double salario = scanner.nextDouble();
		System.out.println("Nova data de contratação do funcionário:");
		String dataContratacao = scanner.next();
		
		Funcionario funcionario = new Funcionario();
		funcionario.setId(id);
		funcionario.setNome(nome);
		funcionario.setCpf(cpf);
		funcionario.setSalario(salario);
		funcionario.setDataContratacao(dataContratacao);
		funcionarioRepository.save(funcionario);
		System.out.println("Atualizado");
	}
	
	private void visualizar() {
		Iterable<Funcionario> funcionarios = funcionarioRepository.findAll();
		funcionarios.forEach(funcionario -> System.out.println(funcionario));
	}
	
	private void deletar(Scanner scanner) {
		System.out.println("Informe o id do funcionário a ser deletado:");
		int id = scanner.nextInt();
		
		funcionarioRepository.deleteById(id);
		System.out.println("Deletado");
	}
}
