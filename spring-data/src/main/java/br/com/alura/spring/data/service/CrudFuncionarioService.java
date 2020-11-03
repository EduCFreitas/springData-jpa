package br.com.alura.spring.data.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import br.com.alura.spring.data.orm.Cargo;
import br.com.alura.spring.data.orm.Funcionario;
import br.com.alura.spring.data.orm.UnidadeTrabalho;
import br.com.alura.spring.data.repository.CargoRepository;
import br.com.alura.spring.data.repository.FuncionarioRepository;
import br.com.alura.spring.data.repository.UnidadeTrabalhoRepository;

@Service
public class CrudFuncionarioService {

	private Boolean system = true;
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private final CargoRepository cargoRepository;
	private final FuncionarioRepository funcionarioRepository;
	private final UnidadeTrabalhoRepository unidadeTrabalhoRepository;
	
	public CrudFuncionarioService(FuncionarioRepository funcionarioRepository, CargoRepository cargoRepository,
			UnidadeTrabalhoRepository unidadeTrabalhoRepository) {
		this.funcionarioRepository = funcionarioRepository;
		this.cargoRepository = cargoRepository;
		this.unidadeTrabalhoRepository = unidadeTrabalhoRepository;
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
		System.out.println("Digite o id do cargo");
		Integer cargoId = scanner.nextInt();
		
		List<UnidadeTrabalho> unidades = unidade(scanner);
		
		Funcionario funcionario = new Funcionario();
		funcionario.setNome(nome);
		funcionario.setCpf(cpf);
		funcionario.setSalario(salario);
		funcionario.setDataContratacao(LocalDate.parse(dataContratacao, formatter));
		Optional<Cargo> cargo = cargoRepository.findById(cargoId);
		funcionario.setCargo(cargo.get());
		funcionario.setUnidadeTrabalhos(unidades);
		funcionarioRepository.save(funcionario);
		System.out.println("Salvo");
	}
	
	private List<UnidadeTrabalho> unidade(Scanner scanner){
		Boolean isTrue = true;
		List<UnidadeTrabalho> unidades = new ArrayList<UnidadeTrabalho>();
		while(isTrue) {
			System.out.println("Digite a id da unidade de trabalho (Para sair digite 0)");
			Integer unidadeId = scanner.nextInt();
			
			if(unidadeId != 0) {
				Optional<UnidadeTrabalho> unidade = unidadeTrabalhoRepository.findById(unidadeId);
				unidades.add(unidade.get());
			}else {
				isTrue = false;
			}
		}
		return unidades;
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
		System.out.println("Id do novo cargo");
		Integer cargoId = scanner.nextInt();
		
		List<UnidadeTrabalho> unidades = unidade(scanner);
		
		Funcionario funcionario = new Funcionario();
		funcionario.setId(id);
		funcionario.setNome(nome);
		funcionario.setCpf(cpf);
		funcionario.setSalario(salario);
		funcionario.setDataContratacao(LocalDate.parse(dataContratacao, formatter));
		Optional<Cargo> cargo = cargoRepository.findById(cargoId);
		funcionario.setCargo(cargo.get());
		funcionario.setUnidadeTrabalhos(unidades);
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
