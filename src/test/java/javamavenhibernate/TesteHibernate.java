package javamavenhibernate;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import dao.DaoGeneric;
import model.TelefoneUser;
import model.UsuarioPessoa;

public class TesteHibernate {

	@Test
	public void testeHibernateUtil() {
		DaoGeneric daoGeneric = new DaoGeneric<UsuarioPessoa>();

		UsuarioPessoa pessoa = new UsuarioPessoa();

		pessoa.setNome("Teste");
		pessoa.setSobrenome("Te");
		pessoa.setEmail("Tes@hotmail.com");
		pessoa.setSenha("432");
		pessoa.setLogin("t");

		daoGeneric.salvar(pessoa);
	}

	@Test
	public void testConsultar() {

		DaoGeneric daoGeneric = new DaoGeneric<UsuarioPessoa>();
		UsuarioPessoa pessoa = new UsuarioPessoa();
		pessoa.setId(1L);
		pessoa = (UsuarioPessoa) daoGeneric.consulta(pessoa);
		System.out.println(pessoa.toString());
	}

	@Test
	public void testUpdate() {
		DaoGeneric daoGeneric = new DaoGeneric<UsuarioPessoa>();

		UsuarioPessoa pessoa = new UsuarioPessoa();
		pessoa.setId(1L);

		UsuarioPessoa updatePessoa = (UsuarioPessoa) daoGeneric.consulta(pessoa);

		updatePessoa.setNome("samuel");
		updatePessoa.setEmail("sricardosgomes@hotmail.com");
		updatePessoa.setLogin("samuel");
		updatePessoa.setSobrenome("Ricardo");

		daoGeneric.UpdateMerge(updatePessoa);

		System.out.println(updatePessoa.toString());
	}

	@Test
	public void testDelete() {
		DaoGeneric daoGeneric = new DaoGeneric<UsuarioPessoa>();

		UsuarioPessoa pessoa = new UsuarioPessoa();
		pessoa.setId(2L);

		UsuarioPessoa deletePessoa = (UsuarioPessoa) daoGeneric.consulta(pessoa);
		daoGeneric.deletarId(deletePessoa);

	}

	@Test
	public void TestConsultaLista() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();

		List<UsuarioPessoa> pessoa = daoGeneric.consultaTodos(UsuarioPessoa.class);

		for (UsuarioPessoa usuarioPessoa : pessoa) {
			System.out.println(usuarioPessoa.toString());
			System.out.println("=======================================================================");
		}
	}

	@Test
	public void TestQueryList() {
		DaoGeneric daoGeneric = new DaoGeneric<UsuarioPessoa>();
		List<UsuarioPessoa> list = daoGeneric.getEntityManager().createQuery(" FROM UsuarioPessoa where id = 1 ")
				.getResultList();

		for (UsuarioPessoa usuarioPessoa : list) {
			System.out.println(usuarioPessoa.toString());
		}
	}

	/* Para limitar o resultado */
	@Test
	public void TestQueryListMaxResult() {
		DaoGeneric daoGeneric = new DaoGeneric<UsuarioPessoa>();
		List<UsuarioPessoa> list = daoGeneric.getEntityManager().createQuery(" FROM UsuarioPessoa order by nome ")
				.setMaxResults(1).getResultList();

		for (UsuarioPessoa usuarioPessoa : list) {
			System.out.println(usuarioPessoa.toString());
		}
	}

	/* Condicionais Parametro */
	@Test
	public void TestQueryLisParamt() {
		DaoGeneric daoGeneric = new DaoGeneric<UsuarioPessoa>();
		List<UsuarioPessoa> list = daoGeneric.getEntityManager()
				.createQuery(" FROM UsuarioPessoa where sobrenome = :sobrenome ").setParameter("sobrenome", "Ricardo")
				.getResultList();

		for (UsuarioPessoa usuarioPessoa : list) {
			System.out.println(usuarioPessoa.toString());
		}
	}

	/* Via NamedQuery */

	@Test
	public void TestNamedQuery() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();
		List<UsuarioPessoa> list = daoGeneric.getEntityManager().createNamedQuery("UsuarioPessoa.consultaTodos")
				.getResultList();

		for (UsuarioPessoa usuarioPessoa : list) {
			System.out.println(usuarioPessoa.toString());
		}
	}

	@Test
	public void TestNamedQueryBuscaNome() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();

		List<UsuarioPessoa> pessoa = (List<UsuarioPessoa>) daoGeneric.getEntityManager()
				.createNamedQuery("UsuarioPessoa.buscaNome").setParameter("nome", "samuel").getResultList();

		for (UsuarioPessoa usuarioPessoa : pessoa) {
			System.out.println(usuarioPessoa.toString());
		}

	}

	@Test
	public void TestNamedInsertTel() {
		DaoGeneric daoGeneric = new DaoGeneric();

		UsuarioPessoa pessoa = new UsuarioPessoa();
		pessoa.setId(1L);

		daoGeneric.consulta(pessoa);

		TelefoneUser telefoneUser = new TelefoneUser();
		telefoneUser.setTipo("casa");
		telefoneUser.setNumero("554");
		telefoneUser.setUsuarioPessoa(pessoa);

		daoGeneric.salvar(telefoneUser);

	}

	@Test
	public void TestNamedConsultaTel() {

		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();

		UsuarioPessoa pessoa = new UsuarioPessoa();
		pessoa.setId(1L);

		UsuarioPessoa telefone = daoGeneric.consulta(pessoa);

		if (telefone != null && telefone.getTelefoneUsers() != null) {

			for (TelefoneUser fone : telefone.getTelefoneUsers()) {
				System.out.println(fone.getNumero());
				System.out.println(fone.getUsuarioPessoa().getNome());
				System.out.println("====================================");
			}
		} else {
			System.out.println("n tem");
		}
	}

}
